package edu.uta.se1.team6.tapem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String userCheck = Utils.readSharedPrefs(SplashActivity.this, getString(R.string.user_data_key));
        Log.d("Splash", "userCheck: " + userCheck);
        if (userCheck.length() > 0) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            try {
                JSONObject object = new JSONObject(userCheck);
//                UserDTO user = Utils.initiateDummy(object.getString("mavID"));
//                intent.putExtra(getString(R.string.user_data_key), user);
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
