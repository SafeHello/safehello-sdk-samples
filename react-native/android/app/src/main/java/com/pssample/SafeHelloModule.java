package com.pssample;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;
import com.safehello.sdk.Router;
import com.safehello.sdk.SafeHelloSdk;
import android.app.Activity;

public class SafeHelloModule extends ReactContextBaseJavaModule {
   SafeHelloModule(ReactApplicationContext context) {
       super(context);
   }

   // add to CalendarModule.java
    @Override
    public String getName() {
    return "SafeHelloModule";
    }

    @ReactMethod
    public void createSafeHelloEvent(String name, String location) {
        Log.d("CalendarModule", "Create event called with name: " + name         + " and location: " + location);
        Activity  context = getCurrentActivity();
        Router.INSTANCE.showEventScreen(context,"demo","subtitle","234asfse");
    }    
}