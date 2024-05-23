package com.vgroup.c2c_embedded_library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.Voice;
import com.vgroup.c2c_embedded_library.pojo.C2CAddress;
import com.vgroup.c2c_embedded_library.pojo.Country;
import com.vgroup.c2c_embedded_library.pojo.InitiateC2C;
import com.vgroup.c2c_embedded_library.pojo.Modes;
import com.vgroup.c2c_embedded_library.pojo.SuccessC2C;
import com.vgroup.c2c_embedded_library.pojo.TokenPojo;
import com.vgroup.c2c_embedded_library.pojo.ValidateOTP;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import kotlin.Unit;

public class C2CVoiceActivity extends AppCompatActivity {
    private Activity activity;
    private String origin;
    private C2CEmbedActivity c2CEmbedActivity;
//    public static final String TAG = "C2C";
//    public HashMap<String, String> params = new HashMap<>();
//    public Call activeCall;
//    public Call.Listener callListener = callListener();
//    public AudioSwitch audioSwitch;
//    private C2CAddress c2CAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        audioSwitch = new AudioSwitch(getApplicationContext());
//        startAudioSwitch();
    }
    public C2CVoiceActivity(Activity activity) {
        this.activity = activity;
        try {
            origin = activity.getClass().getPackage().getName();
        }catch (Exception e){
            e.printStackTrace();
        }
         c2CEmbedActivity = new C2CEmbedActivity(activity,origin);
    }

    public void getModes(@NotNull String channelId, @NotNull Modes modes, @org.jetbrains.annotations.Nullable ImageView callIcon, @org.jetbrains.annotations.Nullable ImageView msgIcon, @org.jetbrains.annotations.Nullable ImageView emailIcon) {
        if (c2CEmbedActivity != null){
            c2CEmbedActivity.getModes(channelId, modes, callIcon, msgIcon, emailIcon);
        }
    }

    public void getCallDetails(@NotNull String channelId, @NotNull Modes modes, @NotNull String call) {
        if (c2CEmbedActivity != null){
            c2CEmbedActivity.getCallDetails(channelId, modes, call);
        }
    }

    public void showError(@NotNull String title, @NotNull String message) {
        if (c2CEmbedActivity != null){
            c2CEmbedActivity.showError(title, message);
        }
    }


}
