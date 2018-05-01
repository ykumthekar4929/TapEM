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
import okhttp3.Response;

public class GetEventData extends AsyncTask<Void, Void, EventDTO> {

    String event_id;
    CallBack callBack;
    EventDTO eventDTO;
    public GetEventData(String event_id, CallBack callBack) {
        this.event_id = event_id;
        this.callBack = callBack;
    }

    @Override
    protected EventDTO doInBackground(Void... voids) {
        Log.d("+>>", "doInBackground: "+Integer.parseInt(event_id));
        String url = AppConstrants.SERVER_BASE_URL + "events/get_event_data/?event_id=" + Integer.parseInt(event_id);
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
                Log.d("doInBackground: ", result);
                eventDTO = new Gson().fromJson(result, EventDTO.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventDTO;
    }

    @Override
    protected void onPostExecute(EventDTO eventDTO) {
        super.onPostExecute(eventDTO);
        callBack.onCallBack(eventDTO, null);

    }
}
