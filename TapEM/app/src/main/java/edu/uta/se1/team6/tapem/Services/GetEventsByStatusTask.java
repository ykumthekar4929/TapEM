package edu.uta.se1.team6.tapem.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.uta.se1.team6.tapem.Core.AppConstrants;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetEventsByStatusTask extends AsyncTask<Void, Void, List<EventDTO>> {

    CallBack callBack;
    String status;
    EventDTO[] eventDTOS;

    public GetEventsByStatusTask(String status, CallBack callBack) {
        this.callBack = callBack;
        this.status = status;
    }

    @Override
    protected List<EventDTO> doInBackground(Void... voids) {
        String url = AppConstrants.SERVER_BASE_URL + "events/events_data/?status=" + status;
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
                eventDTOS = new Gson().fromJson(result, EventDTO[].class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(eventDTOS);
    }

    @Override
    protected void onPostExecute(List<EventDTO> eventDTOS) {
        super.onPostExecute(eventDTOS);
        callBack.onCallBack(eventDTOS, null);

    }
}
