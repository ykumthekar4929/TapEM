package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetActiveUsersTask extends AsyncTask<Void, Void, UserDTO[]> {

    CallBack callBack;
    UserDTO[] userDTOList;

    public GetActiveUsersTask(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected UserDTO[] doInBackground(Void... voids) {
        String url = AppConstrants.SERVER_BASE_URL + "users/get_users/?status=ACTIVE";


        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Log.d("Get Users URL", url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        String result;

        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                result = response.body().string();
                userDTOList = new Gson().fromJson(result, UserDTO[].class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




        return userDTOList;
    }

    @Override
    protected void onPostExecute(UserDTO[] userDTOS) {
        super.onPostExecute(userDTOS);
        Log.d("Active Users", "onPostExecute: " + userDTOS.length);
        callBack.onCallBack(userDTOS, null);
    }
}
