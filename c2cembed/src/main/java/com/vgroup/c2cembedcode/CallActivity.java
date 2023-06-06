package com.vgroup.c2cembedcode;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.Voice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import kotlin.Unit;

public class CallActivity extends AppCompatActivity {
    public static final String TAG = "DemoActivity";
    public static final int MIC_PERMISSION_REQUEST_CODE = 1;
    public static final int PERMISSIONS_REQUEST_CODE = 100;
    public HashMap<String, String> params = new HashMap<>();
    public Call activeCall;
    public Call.Listener callListener = callListener();
    public AudioSwitch audioSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioSwitch = new AudioSwitch(getApplicationContext());
        Log.d("a","a");
        startAudioSwitch();
    }

    public void disconnect() {
        if (activeCall != null) {
            activeCall.disconnect();
            activeCall = null;
        }
    }

    public void hold(Context context, FloatingActionButton holdActionFab) {
        if (activeCall != null) {
            boolean hold = !activeCall.isOnHold();
            activeCall.hold(hold);
            applyFabState(holdActionFab, hold, context);
        }
    }

    public void mute(Context context, FloatingActionButton muteActionFab) {
        if (activeCall != null) {
            boolean mute = !activeCall.isMuted();
            activeCall.mute(mute);
            applyFabState(muteActionFab, mute, context);
        }
    }

    private void applyFabState(FloatingActionButton button, boolean enabled, Context context) {
        // Set fab as pressed when call is on hold
        ColorStateList colorStateList = enabled ?
                ColorStateList.valueOf(ContextCompat.getColor(context,
                        R.color.teal_200)) :
                ColorStateList.valueOf(ContextCompat.getColor(context,
                        R.color.colorAccent));
        button.setBackgroundTintList(colorStateList);
    }

    public void startCall(String mobileNumber,String token, Context context) {
        if (mobileNumber.isEmpty()){
            return;
        }
        params.put("To", mobileNumber);
        ConnectOptions connectOptions = new ConnectOptions.Builder(token)
                .params(params)
                .build();
        activeCall = Voice.connect(context.getApplicationContext(), connectOptions, callListener);
        Log.d("A","A");

    }

    private void startAudioSwitch() {
        /*
         * Start the audio device selector after the menu is created and update the icon when the
         * selected audio device changes.
         */
        audioSwitch.start((audioDevices, audioDevice) -> {
            Log.d(TAG, "Updating AudioDeviceIcon");
//            updateAudioDeviceIcon(audioDevice);
            return Unit.INSTANCE;
        });
    }


    private Call.Listener callListener() {
        return new Call.Listener() {
            /*
             * This callback is emitted once before the Call.Listener.onConnected() callback when
             * the callee is being alerted of a Call. The behavior of this callback is determined by
             * the answerOnBridge flag provided in the Dial verb of your TwiML application
             * associated with this client. If the answerOnBridge flag is false, which is the
             * default, the Call.Listener.onConnected() callback will be emitted immediately after
             * Call.Listener.onRinging(). If the answerOnBridge flag is true, this will cause the
             * call to emit the onConnected callback only after the call is answered.
             * See answeronbridge for more details on how to use it with the Dial TwiML verb. If the
             * twiML response contains a Say verb, then the call will emit the
             * Call.Listener.onConnected callback immediately after Call.Listener.onRinging() is
             * raised, irrespective of the value of answerOnBridge being set to true or false
             */
            @Override
            public void onRinging(@NonNull Call call) {
                Log.d(TAG, "Ringing");
                /*
                 * When [answerOnBridge](https://www.twilio.com/docs/voice/twiml/dial#answeronbridge)
                 * is enabled in the <Dial> TwiML verb, the caller will not hear the ringback while
                 * the call is ringing and awaiting to be accepted on the callee's side. The application
                 * can use the `SoundPoolManager` to play custom audio files between the
                 * `Call.Listener.onRinging()` and the `Call.Listener.onConnected()` callbacks.
                 */
//                if (BuildConfig.playCustomRingback) {
//                SoundPoolManager.getInstance(getApplicationContext()).playRinging();
//                }
            }

            @Override
            public void onConnectFailure(@NonNull Call call, @NonNull CallException error) {
                if (audioSwitch!=null){
                    audioSwitch.deactivate();
//                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(getApplicationContext()).stopRinging();
//                }
                    Log.d(TAG, "Connect failure");
                    String message = String.format(
                            Locale.US,
                            "Call Error: %d, %s",
                            error.getErrorCode(),
                            error.getMessage());
                    Log.e(TAG, message);

                }
            }

            @Override
            public void onConnected(@NonNull Call call) {
                if (audioSwitch != null){
                    audioSwitch.activate();
//                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(getApplicationContext()).stopRinging();
//                }
                    Log.d(TAG, "Connected");
                    activeCall = call;

                }
            }

            @Override
            public void onReconnecting(@NonNull Call call, @NonNull CallException callException) {
                Log.d(TAG, "onReconnecting");
            }

            @Override
            public void onReconnected(@NonNull Call call) {
                Log.d(TAG, "onReconnected");
            }

            @Override
            public void onDisconnected(@NonNull Call call, CallException error) {
                if(audioSwitch != null){
                    audioSwitch.deactivate();
//                if (BuildConfig.playCustomRingback) {
                    SoundPoolManager.getInstance(getApplicationContext()).stopRinging();
//                }
                    Log.d(TAG, "Disconnected");
                    if (error != null) {
                        String message = String.format(
                                Locale.US,
                                "Call Error: %d, %s",
                                error.getErrorCode(),
                                error.getMessage());
                        Log.e(TAG, message);
//                    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            /*
             * currentWarnings: existing quality warnings that have not been cleared yet
             * previousWarnings: last set of warnings prior to receiving this callback
             *
             * Example:
             *   - currentWarnings: { A, B }
             *   - previousWarnings: { B, C }
             *
             * Newly raised warnings = currentWarnings - intersection = { A }
             * Newly cleared warnings = previousWarnings - intersection = { C }
             */
            public void onCallQualityWarningsChanged(@NonNull Call call,
                                                     @NonNull Set<Call.CallQualityWarning> currentWarnings,
                                                     @NonNull Set<Call.CallQualityWarning> previousWarnings) {

                if (previousWarnings.size() > 1) {
                    Set<Call.CallQualityWarning> intersection = new HashSet<>(currentWarnings);
                    currentWarnings.removeAll(previousWarnings);
                    intersection.retainAll(previousWarnings);
                    previousWarnings.removeAll(intersection);
                }

                String message = String.format(
                        Locale.US,
                        "Newly raised warnings: " + currentWarnings + " Clear warnings " + previousWarnings);
                Log.e(TAG, message);
//                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
            }
        };
    }


}
