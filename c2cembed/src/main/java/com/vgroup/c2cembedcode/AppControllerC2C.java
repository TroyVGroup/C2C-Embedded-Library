package com.vgroup.c2cembedcode;

import android.app.Application;
import android.text.TextUtils;

public class AppControllerC2C {}
//extends Application {
//
//    public static final String TAG = AppControllerC2C.class
//            .getSimpleName();
//
//    private static RequestQueue mRequestQueue;
//
//
//    private static AppControllerC2C mInstance;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mInstance = this;
//
//    }
//
//    public static synchronized AppControllerC2C getInstance() {
//        return mInstance;
//    }
//
//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(this);
//        }
//
//        return mRequestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        // set the default tag if tag is empty
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//    }
//
//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }
//}