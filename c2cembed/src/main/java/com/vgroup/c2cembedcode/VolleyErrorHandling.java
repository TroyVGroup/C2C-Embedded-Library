package com.vgroup.c2cembedcode;


import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VolleyErrorHandling{}
//{
//    static Dialog errorDialog;
//
//    public static void errorHandling(VolleyError volleyError, final Activity activity, int option) {
//        if (activity == null) {
//            return;
//        }
//
//
//        try {
//            if (volleyError instanceof ServerError) {
//                NetworkResponse networkResponse = volleyError.networkResponse;
//                if (networkResponse.data != null) {
//                    String data = new String(networkResponse.data);
//                    JSONObject jsonObject = new JSONObject(data);
//                    if (jsonObject.has("message")) {
////                            errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, jsonObject.get("message").toString());
////                            errorDialog.show();
//                    }
//                } else {
////                    errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Server Error");
////                    errorDialog.show();
//                }
//
//            } else {
//                errorHandling(volleyError, activity);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void errorHandling(VolleyError volleyError, final Activity activity) {
//
//        if (activity == null) {
//            return;
//        }
//
//
//        try {
//            if (volleyError instanceof ServerError) {
//                NetworkResponse networkResponse = volleyError.networkResponse;
//                if (networkResponse.data != null) {
//                    String data = new String(networkResponse.data);
//                    JSONObject jsonObject = new JSONObject(data);
//                    if (jsonObject.has("message")) {
//
////                        errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, jsonObject.get("message").toString());
////                        errorDialog.show();
//                    }
//                } else {
////                    errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Server Error");
////                    errorDialog.show();
//                }
//
//            } else if (volleyError instanceof NoConnectionError) {
////                errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "No Connection Error");
////                errorDialog.show();
//            } else if (volleyError instanceof TimeoutError) {
////                errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Time Out ");
////                errorDialog.show();
//            } else if (volleyError instanceof AuthFailureError) {
//
//                NetworkResponse networkResponse = volleyError.networkResponse;
//
//                if (networkResponse.data != null) {
//                    String data = new String(networkResponse.data);
//                    JSONObject jsonObject = new JSONObject(data);
//                    if (jsonObject.has("message")) {
//
////                        errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, jsonObject.get("message").toString());
////                        errorDialog.show();
//                    }
//                } else {
////                    errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Authentication Failure!");
////                    errorDialog.show();
//                }
//
//
//            } else if (volleyError instanceof NetworkError) {
////                errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Network Error!");
////                errorDialog.show();
//            } else if (volleyError instanceof ParseError) {
////                errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, "Parse Error!");
////                errorDialog.show();
//            } else {
//                NetworkResponse networkResponse = volleyError.networkResponse;
//                if (networkResponse != null && networkResponse.data != null) {
//
//                    String data = new String(networkResponse.data);
//                    JSONObject jsonObject = new JSONObject(data);
//
//                    if (jsonObject.has("errorMessages")) {
//                        JSONArray array = jsonObject.getJSONArray("errorMessages");
//                        StringBuilder sb = new StringBuilder();
//                        for (int i = 0; i < array.length(); i++) {
//                            sb.append(array.get(i));
//                            sb.append("\n");
//                        }
//                        sb.setLength(sb.length() - 1);
////                        errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, sb.toString());
////                        errorDialog.show();
//                    }
//                }
//            }
//        } catch (Exception e) {
////            errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.error), activity, activity.getString(R.string.something_went_wrong_text));
////            errorDialog.show();
//            Log.e("VolleyErrorHandling", e.getMessage());
//        }
//    }
//
//}
