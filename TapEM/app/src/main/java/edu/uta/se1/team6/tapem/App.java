package edu.uta.se1.team6.tapem;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by yashodhan on 3/23/18.
 */

public class App extends MultiDexApplication {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        if(!BuildConfig.DEBUG){
            //Anything debig specific
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
