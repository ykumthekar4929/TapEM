package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateUserTask extends AsyncTask<Void, Void, UserDTO> {

    private UserDTO userDTO;
    private CallBack callBack;

    public UpdateUserTask(UserDTO userDTO, CallBack callBack) {
        this.userDTO = userDTO;
        this.callBack = callBack;
    }

    @Override
    protected UserDTO doInBackground(Void... voids) {
        String url = AppConstrants.SERVER_BASE_URL + "users/register/" + userDTO.getId() + "/";
        String object = new Gson().toJson(userDTO);
        Log.d("URL: ", url);
        Log.d("Object: ", object);

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, object);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        String result = null;
        UserDTO user = null;
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Gson gson = new GsonBuilder().create();
            user = gson.fromJson(result, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    protected void onPostExecute(UserDTO user) {
        super.onPostExecute(user);
        callBack.onCallBack(user, null);
    }

}
