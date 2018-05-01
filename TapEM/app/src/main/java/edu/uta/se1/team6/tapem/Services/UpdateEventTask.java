package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

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

public class UpdateEventTask extends AsyncTask<Void, Void, String> {

    int event;
    String status;
    CallBack callBack;

    public UpdateEventTask(int event, String status, CallBack callBack) {
        this.event = event;
        this.status = status;
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String url = AppConstrants.SERVER_BASE_URL + "events/events_data/" + event + "/";
        JSONObject object = new JSONObject();
        try {
            object.put("status", status);
            object.put("event_id", event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("URL: ", url);
        Log.d("Object: ", String.valueOf(object));

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, object.toString());
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        String result = null;
        UserDTO user = null;
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
