package com.vgroup.c2cembedcode;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vgroup.c2cembedcode.pojo.CallPojo;
import com.vgroup.c2cembedcode.pojo.Modes;
import com.vgroup.c2cembedcode.pojo.SuccessC2C;
import com.vgroup.c2cembedcode.pojo.TokenPojo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NetworkManager {

    public void getModes(final NetworkEventListener listener, String channelId, String origin, String c2cPackage, ImageView call_icon, ImageView msg_icon, ImageView email_icon) {
        String url = C2CConstants.CHANNEL_MODES + channelId;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", c2cUrl);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, Method.GET.toString(), Method.GET, headers,Modes.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                Log.e("Response", "clazz");
                Modes response = (Modes) obj;

                if (response.status == 200) {
                    call_icon.setVisibility(response.channel.callstats.enable ? View.VISIBLE : View.GONE);
                    msg_icon.setVisibility(response.channel.smsstats.enable ? View.VISIBLE : View.GONE);
                    email_icon.setVisibility(response.channel.emailstats.enable ? View.VISIBLE : View.GONE);
                } else {
                    call_icon.setVisibility(View.GONE);
                    msg_icon.setVisibility(View.GONE);
                    email_icon.setVisibility(View.GONE);
                }
                listener.OnSuccess(obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();
    }

    public void sendEmail(final NetworkEventListener listener, String data, String origin, String c2cPackage) {

        String url = C2CConstants.SEND_EMAIL;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("C2c-Request", website);
//        headers.put("C2c-Latlong", "23.2669986,77.4357627");
//
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void sendSMS(final NetworkEventListener listener, String data, String origin, String c2cPackage, String latLong) {

        String url = C2CConstants.SEND_SMS;
        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//        headers.put("c2c-latlong", "23.222,23.55555");
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();
    }

    public void verifyEmailOTP(final NetworkEventListener listener, String data,String origin, String c2cPackage) {

        String url = C2CConstants.VERIFY_EMAIL_OTP;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void verifyMobileOTP(final NetworkEventListener listener, String data, String origin, String c2cPackage) {

        String url = C2CConstants.VERIFY_SMS_OTP;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void getOTPForEmail(final NetworkEventListener listener, String channelId, String emailID, String origin, String c2cPackage) {

        String url = C2CConstants.EMAIL_OTP;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        Map<String, String> data = new HashMap<>();
        data.put("channelid", channelId);
        data.put("email", emailID);

        JSONObject obj = new JSONObject(data);

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, obj.toString(), Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void initiateCall(final NetworkEventListener listener, String data, Context context, String origin, String c2cPackage) {

        String url = C2CConstants.INITIATE_CALL;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,CallPojo.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                String callAuth = ((CallPojo) obj).callauth.id;
                getToken(listener, callAuth, context, origin, c2cPackage);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void getToken(final NetworkEventListener listener, String authID, Context context, String origin, String c2cPackage) {

        String url = C2CConstants.GENERATE_TOKEN;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//        headers.put("c2c-latlong", "23.2669986,77.4357627");
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");


        HashMap<String, String> data = new HashMap<>();
        data.put("authId", authID);
        JSONObject obj = new JSONObject(data);
        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, obj.toString(), Method.POST, headers,TokenPojo.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((TokenPojo) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();


    }

    public void getOTPForSMS(final NetworkEventListener listener, String channelId, String code, String number, String origin, String c2cPackage) {

        String url = C2CConstants.SMS_OTP;
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        Map<String, String> data = new HashMap<>();
        data.put("channelid", channelId);
        data.put("countrycode", code);
        data.put("number", number);
        JSONObject obj = new JSONObject(data);
        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, obj.toString(), Method.POST, headers,SuccessC2C.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess((SuccessC2C) obj);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();
    }


//    public void getModes1(final NetworkEventListener listener, String channelId, String origin, String c2cUrl, ImageView call_icon, ImageView msg_icon, ImageView email_icon) {
//
//        String url = Constants.CHANNEL_MODES + channelId;
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", c2cUrl);
//
//        UnAuthorizedServiceOperation modes = new UnAuthorizedServiceOperation<Modes>(url, Modes.class, headers) {
//            @Override
//            public void onServerResult(Modes response) {
//                if (response.status == 200) {
//                    call_icon.setVisibility(response.channel.callstats.enable ? View.VISIBLE : View.GONE);
//                    msg_icon.setVisibility(response.channel.smsstats.enable ? View.VISIBLE : View.GONE);
//                    email_icon.setVisibility(response.channel.emailstats.enable ? View.VISIBLE : View.GONE);
//                } else {
//                    call_icon.setVisibility(View.GONE);
//                    msg_icon.setVisibility(View.GONE);
//                    email_icon.setVisibility(View.GONE);
//                }
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        modes.addToRequestQueue("notificationList");
//
//    }
//
//    public void getCallDetails(final NetworkEventListener listener, String channelId, Context context, CallActivity callActivity) {
//        String url = Constants.CHANNEL_MODES + channelId;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", "https://testvg20.weebly.com");
//        headers.put("Referer", "https://testvg20.weebly.com");
//        UnAuthorizedServiceOperation modes = new UnAuthorizedServiceOperation<Modes>(url, Modes.class, headers) {
//            @Override
//            public void onServerResult(Modes response) {
//                if (response.channel.preferences.isVerificationRequired()) {
//
//                } else {
//                }
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        modes.addToRequestQueue("notificationList");
//    }
//
//    public void sendEmail1(final NetworkEventListener listener, String data, String origin, String website) {
//
//        String url = Constants.SEND_EMAIL;
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//        headers.put("c2c-latlong", "23.2669986,77.4357627");
//
//        UnAuthorizedServiceOperation sendEmail = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, data) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        sendEmail.addToRequestQueue("sendEmail");
//
//    }
//
//    public void sendSMS1(final NetworkEventListener listener, String data, String origin, String website, String latLong) {
//
//        String url = Constants.SEND_SMS;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//        headers.put("c2c-latlong", "23.222,23.55555");
//
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, data) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("sendSMS");
//
//    }
//
//    public void verifyEmailOTP1(final NetworkEventListener listener, String data) {
//
//        String url = Constants.VERIFY_EMAIL_OTP;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", "https://testvg20.weebly.com");
//        headers.put("Referer", "https://testvg20.weebly.com");
//
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, data) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//    public void verifyMobileOTP1(final NetworkEventListener listener, String data) {
//
//        String url = Constants.VERIFY_SMS_OTP;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", "https://testvg20.weebly.com");
//        headers.put("Referer", "https://testvg20.weebly.com");
//
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, data) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//    public void getOTPForEmail1(final NetworkEventListener listener, String channelId, String emailID, String origin, String website) {
//
//        String url = Constants.EMAIL_OTP;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("channelid", channelId);
//        data.put("email", emailID);
//
//        JSONObject obj = new JSONObject(data);
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, obj.toString()) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//    public void getOTPForSMS1(final NetworkEventListener listener, String channelId, String code, String number, String origin, String website) {
//
//        String url = Constants.SMS_OTP;
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("channelid", channelId);
//        data.put("countrycode", code);
//        data.put("number", number);
//        JSONObject obj = new JSONObject(data);
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<SuccessC2C>(url, SuccessC2C.class, headers, obj.toString()) {
//            @Override
//            public void onServerResult(SuccessC2C response) {
//                listener.OnSuccess(response);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//
//    public void initiateCall1(final NetworkEventListener listener, String data, Context context, CallActivity callActivity, String origin, String website) {
//
//        String url = Constants.INITIATE_CALL;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<CallPojo>(url, CallPojo.class, headers, data) {
//            @Override
//            public void onServerResult(CallPojo response) {
//                String callAuth = response.callauth.id;
//                getToken(listener, callAuth, context, callActivity, origin, website);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//    public void getToken1(final NetworkEventListener listener, String authID, Context context, CallActivity callActivity, String origin, String website) {
//
//        String url = Constants.GENERATE_TOKEN;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Origin", origin);
//        headers.put("Referer", origin);
//        headers.put("c2c-request", website);
//        headers.put("c2c-latlong", "23.2669986,77.4357627");
//
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("authId", authID);
//        JSONObject obj = new JSONObject(data);
//        UnAuthorizedServiceOperation call = new UnAuthorizedServiceOperation<TokenPojo>(url, TokenPojo.class, headers, obj.toString()) {
//            @Override
//            public void onServerResult(TokenPojo response) {
//                listener.OnSuccess(response);
////                startCall(response, context, callActivity);
//            }
//
//            @Override
//            public void onErrorResult(VolleyError exception) {
//                listener.OnError(exception);
//            }
//        };
//
//        call.addToRequestQueue("initiateCall");
//
//    }
//
//
//    private void startCall(TokenPojo response, Context context, CallActivity callActivity) {
////        callActivity.startCall(tokenPojo.verified.call, tokenPojo.token.value,this@Dashboard)
////        callActivity.startCall(response.verified.call, response.token.value, context);
//
////        Dialog dialog = new Dialog(context);
////        dialog.setContentView(R.layout.c);
////
////
////        val dialog = Dialog(this@Dashboard)
////        dialog.setContentView(R.layout.call_menu)
////        val chronometer = dialog.findViewById<Chronometer>(R.id.chronometer)
////                val holdActionFab = dialog.findViewById<FloatingActionButton>(R.id.hold_action_fab)
////                val muteActionFab = dialog.findViewById<FloatingActionButton>(R.id.mute_action_fab)
////                val hangUpActionFab = dialog.findViewById<FloatingActionButton>(R.id.hangup_action_fab)
////                holdActionFab.setOnClickListener {
////            callActivity.hold(this@Dashboard,holdActionFab)
////        }
////        muteActionFab.setOnClickListener {
////            callActivity.mute(this@Dashboard,muteActionFab)
////        }
////        hangUpActionFab.setOnClickListener {
////            callActivity.disconnect()
////            dialog.dismiss()
////        }
////        chronometer.base = SystemClock.elapsedRealtime()
////        chronometer.start()
////        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
////        dialog.setCancelable(false)
////        dialog.show()
//
//    }
}
