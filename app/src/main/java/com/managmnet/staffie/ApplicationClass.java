package com.managmnet.staffie;
import com.onesignal.OneSignal;

import android.app.Application;

public class ApplicationClass extends Application {
    private static final String ONESIGNAL_APP_ID = "d59e0b22-34c3-4bf1-87d8-8d6ca5f161f7";
    @Override
    public void onCreate(){
        super.onCreate();


        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
