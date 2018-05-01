package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetCatererStaffTask extends AsyncTask<Void, Void, UserDTO[]> {

    CallBack callBack;
    UserDTO[] userDTOList;

    public GetCatererStaffTask(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected UserDTO[] doInBackground(Void... voids) {

        String url = AppConstrants.SERVER_BASE_URL + "users/get_users/?status=ACTIVE&role=CATERER%20STAFF";


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
        callBack.onCallBack(userDTOS, null);

    }
}
