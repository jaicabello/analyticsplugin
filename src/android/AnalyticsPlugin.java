package com.hms.cordova.plugin;

import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;

import java.util.Iterator;

public class AnalyticsPlugin extends CordovaPlugin {

    private HiAnalyticsInstance instance;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        HiAnalyticsTools.enableLog();
        instance = HiAnalytics.getInstance(cordova.getContext());
    }


    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        switch (action) {
            case "setUserProfile":
                try {
                    JSONObject userCategory = args.getJSONObject(0);
                    instance.setUserProfile(userCategory.getString("UserProfileName"), userCategory.getString("UserProfile"));
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "User Profile Set Success");
                    callbackContext.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
                    callbackContext.sendPluginResult(pluginResult);
                }
                break;

            case "logEvent":
                try {
                    JSONObject eventName = args.getJSONObject(0);
                    JSONObject object = args.getJSONObject(0);
                    logEvent(eventName.getString("EventName"), object, callbackContext);
                } catch (JSONException e) {
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
                    callbackContext.sendPluginResult(pluginResult);
                }
                break;
        }

        return true;
    }

    private void logEvent(String eventName, JSONObject object, CallbackContext callbackContext) {
        if (object != null) {
            Bundle bundle = new Bundle();
            for (Iterator<String> it = object.keys(); it.hasNext(); ) {
                String key = it.next();
                bundle.putString(key, object.optString(key));
            }
            instance.onEvent(eventName, bundle);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "Upload Success");
            callbackContext.sendPluginResult(pluginResult);
        } else {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "Expected one non-empty string argument.");
            callbackContext.sendPluginResult(pluginResult);
        }
    }


}