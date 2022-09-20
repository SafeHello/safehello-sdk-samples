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

public class SafeHelloModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext _context;
   SafeHelloModule(ReactApplicationContext context) {
       super(context);
       _context=context;

   }

   // add to CalendarModule.java
    @Override
    public String getName() {
    return "SafeHelloModule";
    }

    @ReactMethod
    public void createSafeHelloEvent(String name, String location) {
        Log.d("CalendarModule", "Create event called with name: " + name         + " and location: " + location);

        Router.INSTANCE.showEventScreen(_context,"demo","subtitle","234asfse");
    }    
}