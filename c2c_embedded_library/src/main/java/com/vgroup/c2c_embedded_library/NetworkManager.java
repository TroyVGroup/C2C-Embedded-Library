package com.vgroup.c2c_embedded_library;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vgroup.c2c_embedded_library.pojo.C2CAddress;
import com.vgroup.c2c_embedded_library.pojo.CallPojo;
import com.vgroup.c2c_embedded_library.pojo.Modes;
import com.vgroup.c2c_embedded_library.pojo.SuccessC2C;
import com.vgroup.c2c_embedded_library.pojo.TokenPojo;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NetworkManager {

    public void getModes(final NetworkEventListener listener, String channelId, String c2cPackage, ImageView call_icon, ImageView msg_icon, ImageView email_icon) {
        String url = C2CConstants.CHANNEL_MODES + channelId;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("request-package", c2cPackage);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, Method.GET.toString(), Method.GET, headers,Modes.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                Log.e("Response", "obj");
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

    public void getDeviceIP(final NetworkEventListener listener) {
        String url = C2CConstants.GEOCODE;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "prj_live_sk_569b6f639edde6120a26f703511c61aaecd3f7ef");
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");
        HTTPRequestC2C requestHttp = new HTTPRequestC2C(false,url, Method.GET.toString(), Method.GET, headers, C2CAddress.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                listener.OnSuccess(obj);
            }
            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();
    }

    public void sendEmail(final NetworkEventListener listener, String data, String c2cPackage,String latLong) {

        String url = C2CConstants.SEND_EMAIL;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("c2c-latlong", latLong);
        Log.d("latLong",latLong);
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

    public void sendSMS(final NetworkEventListener listener, String data, String c2cPackage, String latLong) {

        String url = C2CConstants.SEND_SMS;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("c2c-latlong", latLong);
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

    public void verifyEmailOTP(final NetworkEventListener listener, String data, String c2cPackage) {

        String url = C2CConstants.VERIFY_EMAIL_OTP;
        HashMap<String, String> headers = new HashMap<>();

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

    public void verifyMobileOTP(final NetworkEventListener listener, String data, String c2cPackage) {

        String url = C2CConstants.VERIFY_SMS_OTP;
        HashMap<String, String> headers = new HashMap<>();
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

    public void getOTPForEmail(final NetworkEventListener listener, String channelId, String emailID, String c2cPackage) {

        String url = C2CConstants.EMAIL_OTP;
        HashMap<String, String> headers = new HashMap<>();
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

    public void initiateCall(final NetworkEventListener listener, String data, Context context, String c2cPackage, String latLong) {

        String url = C2CConstants.INITIATE_CALL;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("request-package", c2cPackage);
        headers.put("c2c-latlong", latLong);
        headers.put("Content-Type","application/json");
        headers.put("Accept", "application/json");

        HTTPRequestC2C requestHttp = new HTTPRequestC2C(url, data, Method.POST, headers,CallPojo.class, new HTTPCallback() {
            @Override
            public void processFinish(Object obj) {
                String callAuth = ((CallPojo) obj).callauth.id;
                getToken(listener, callAuth, c2cPackage, latLong);
            }

            @Override
            public void processFailed(int responseCode, String output) {
                Log.e("Response Failed", Integer.toString(responseCode) + " - " + output);
            }
        });
        requestHttp.execute();

    }

    public void getToken(final NetworkEventListener listener, String authID,  String c2cPackage, String latLong) {

        String url = C2CConstants.GENERATE_TOKEN;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("request-package", c2cPackage);
        headers.put("c2c-latlong", latLong);
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

    public void getOTPForSMS(final NetworkEventListener listener, String channelId, String code, String number, String c2cPackage) {

        String url = C2CConstants.SMS_OTP;
        HashMap<String, String> headers = new HashMap<>();
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

}
