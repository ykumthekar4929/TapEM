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

public class GetRequestedUsersTask extends AsyncTask<Void, Void, UserDTO[]> {

    CallBack callBack;
    //    List  = new ArrayList<>();
    UserDTO[] userDTOList;
    public GetRequestedUsersTask(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected UserDTO[] doInBackground(Void... voids) {
        String url = AppConstrants.SERVER_BASE_URL + "users/get_users/?status=REQUESTED";


        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        Log.d("Get Users URL", url);

        OkHttpClient client = new OkHttpClient();
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
//                userDTOList.addAll(new Gson().fromJson(result, userDTOList.getClass()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userDTOList;
    }

    @Override
    protected void onPostExecute(UserDTO[] userDTOS) {
        super.onPostExecute(userDTOS);
        Log.d("Requested Users", "onPostExecute: " + userDTOS.length);
        callBack.onCallBack(userDTOS, null);
    }
}
