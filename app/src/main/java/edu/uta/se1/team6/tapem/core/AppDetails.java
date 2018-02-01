package edu.uta.se1.team6.tapem.core;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.BuildConfig;
import android.support.v4.app.FragmentActivity;

/**
 * Created by yashodhan on 2/1/18.
 */


public class AppDetails {

    private static AppDetails mInstance = null;
    protected Context mContext;
    protected FragmentActivity mActivity;

//    protected Bus bus;

    /**
     * @return
     */
    public static AppDetails instance() {
        if (mInstance == null) {
            mInstance = new AppDetails();
        }

        return mInstance;
    }

    private AppDetails() {
//        bus = new Bus();
    }

    /**
     * @return
     */
    public static Context getContext() {
        Context context = instance().mContext;
        if (context == null) {
            // what to do!?
        }
        return context;
    }

    /**
     * @return
     */
    public static Resources resources() {
        if (AppDetails.instance().mContext == null)
            return null;

        return AppDetails.instance().mContext.getResources();
    }

    /**
     * @param context
     */
    public static void setContext(Context context) {
        instance().mContext = context;
    }

    /**
     * @param activity
     * @param setContext
     */
    public static void setActivity(FragmentActivity activity, boolean setContext) {
        instance().mActivity = activity;
        if (setContext) {
            if (activity == null) {
                setContext(null);
            } else {
                setContext(activity.getApplicationContext());
            }
        }
    }

    public static FragmentActivity getActivity() {
        return instance().mActivity;
    }

//    public Bus getBus() {
//        return bus;
//    }

    public static String getBucketName(){
        if(BuildConfig.DEBUG){
            return AppConstrants.DEVELOPMENT;
        }else{
            return AppConstrants.PRODUCTION;
        }
    }

}
