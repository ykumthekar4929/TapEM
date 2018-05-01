package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestEventTask extends AsyncTask<Void, Void, String> {

    private EventDTO eventDTO;
    private CallBack callBack;

    public RequestEventTask(EventDTO eventDTO, CallBack callBack) {
        this.eventDTO = eventDTO;
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = AppConstrants.SERVER_BASE_URL + "events/events_data/";
        String object = new Gson().toJson(eventDTO);

        Log.d("URL: ", url);
        Log.d("Object: ", object);

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, object);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        String result = null;

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Event Request", "onPostExecute: "+s);
        callBack.onCallBack(s, null);
    }
}
