package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UserLoginTask extends AsyncTask<Void, Void, UserDTO>{

    private String username, password;
    private String url = AppConstrants.SERVER_BASE_URL + "users/user_login/";
    private CallBack callBack;
    UserDTO userDTO;

    public UserLoginTask(String username, String password, CallBack callBack) {
        this.password = password;
        this.username = username;
        this.callBack = callBack;
    }

    @Override
    protected UserDTO doInBackground(Void... params) {

        String result = null;
        JSONObject json = new JSONObject();
        try {
            json.put("mav_id", Integer.parseInt(username));
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());

        Log.d("URL: ", url);
        Log.d("Object: ", json.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d("Result : ",  result);


        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        UserDTO user = gson.fromJson(result, UserDTO.class);

        return user;
    }

    @Override
    protected void onPostExecute(UserDTO userDTO) {
        super.onPostExecute(userDTO);
        Log.d("User login", "onPostExecute: " + userDTO.getFirstName());
        callBack.onCallBack(userDTO, null);

    }
}
