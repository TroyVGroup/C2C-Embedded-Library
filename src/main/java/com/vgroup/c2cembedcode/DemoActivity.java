package com.vgroup.c2cembedcode;


import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twilio.audioswitch.AudioDevice;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.Voice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import kotlin.Unit;


public class DemoActivity extends AppCompatActivity {
    private static final String TAG = "DemoActivity";
    private static final int MIC_PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private String accessToken = "eyJjdHkiOiJ0d2lsaW8tZnBhO3Y9MSIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJpc3MiOiJTSzhlYWU3YjhiNTllNGNmYjk1NGIxNDMzMDk2MGQxNmRjIiwiZXhwIjoxNjY3NDg1Mjg1LCJncmFudHMiOnsidm9pY2UiOnsiaW5jb21pbmciOnsiYWxsb3ciOmZhbHNlfSwib3V0Z29pbmciOnsiYXBwbGljYXRpb25fc2lkIjoiQVA4MmI4YWJmM2IyYjIyNTA5ZjllNWE4MDVmODVmMDJmYSJ9fSwiaWRlbnRpdHkiOiJ1c2VyIn0sImp0aSI6IlNLOGVhZTdiOGI1OWU0Y2ZiOTU0YjE0MzMwOTYwZDE2ZGMtMTY2NzQ4MTcyOCIsInN1YiI6IkFDMTRlYWQ2ZmYyNDk4YjMzYzdhZGQ0MmYyODMzMzZkMDQifQ.L0poxLZgoHhPUSM74-uvm3PSOOOGLTSHvgJx7pW6AZI";
    HashMap<String, String> params = new HashMap<>();
    private Call activeCall;
    Call.Listener callListener = callListener();
    private AudioSwitch audioSwitch;
    private FloatingActionButton callActionFab;
    private FloatingActionButton menuAudioDeviceFab;
    private FloatingActionButton hangupActionFab;
    private FloatingActionButton holdActionFab;
    private FloatingActionButton muteActionFab;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        // These flags ensure that the activity can be launched when the screen is locked.
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        callActionFab = findViewById(R.id.call_action_fab);
        hangupActionFab = findViewById(R.id.hangup_action_fab);
        holdActionFab = findViewById(R.id.hold_action_fab);
        muteActionFab = findViewById(R.id.mute_action_fab);
        menuAudioDeviceFab = findViewById(R.id.menu_audio_device_fab);
        chronometer = findViewById(R.id.chronometer);
        audioSwitch = new AudioSwitch(getApplicationContext());

        startAudioSwitch();
        resetUI();

        callActionFab.setOnClickListener(v -> startCall("+919893373444"));

        muteActionFab.setOnClickListener(view ->
                mute()
        );

        hangupActionFab.setOnClickListener(view -> {
            SoundPoolManager.getInstance(DemoActivity.this).playDisconnect();
            resetUI();
            disconnect();
        });

        holdActionFab.setOnClickListener(view ->
                hold()
        );

        menuAudioDeviceFab.setOnClickListener(view ->
                showAudioDevices()
        );

    }

    /*
     * Show the current available audio devices.
     */
    private void showAudioDevices() {
        AudioDevice selectedDevice = audioSwitch.getSelectedAudioDevice();
        List<AudioDevice> availableAudioDevices = audioSwitch.getAvailableAudioDevices();

        if (selectedDevice != null) {
            int selectedDeviceIndex = availableAudioDevices.indexOf(selectedDevice);

            ArrayList<String> audioDeviceNames = new ArrayList<>();
            for (AudioDevice a : availableAudioDevices) {
                audioDeviceNames.add(a.getName());
            }

            new AlertDialog.Builder(this)
                    .setTitle(R.string.select_device)
                    .setSingleChoiceItems(
                            audioDeviceNames.toArray(new CharSequence[0]),
                            selectedDeviceIndex,
                            (dialog, index) -> {
                                dialog.dismiss();
                                AudioDevice selectedAudioDevice = availableAudioDevices.get(index);
                                updateAudioDeviceIcon(selectedAudioDevice);
                                audioSwitch.selectDevice(selectedAudioDevice);
                            }).create().show();
        }
    }


    private void startCall(String mobileNumber) {
        params.put("To", mobileNumber);
        ConnectOptions connectOptions = new ConnectOptions.Builder(accessToken)
                .params(params)
                .build();
        activeCall = Voice.connect(DemoActivity.this, connectOptions, callListener);
        setCallUI();
    }
    /*
     * The UI state when there is an active call
     */
    private void setCallUI() {
        callActionFab.hide();
        hangupActionFab.show();
        holdActionFab.show();
        muteActionFab.show();
        menuAudioDeviceFab.show();
        chronometer.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }
    private void startAudioSwitch() {
        /*
         * Start the audio device selector after the menu is created and update the icon when the
         * selected audio device changes.
         */
        audioSwitch.start((audioDevices, audioDevice) -> {
            Log.d(TAG, "Updating AudioDeviceIcon");
            updateAudioDeviceIcon(audioDevice);
            return Unit.INSTANCE;
        });
    }

    /*
     * Update the menu icon based on the currently selected audio device.
     */
    private void updateAudioDeviceIcon(AudioDevice selectedAudioDevice) {
        int audioDeviceMenuIcon = R.drawable.ic_phonelink_ring_white_24dp;

        if (selectedAudioDevice instanceof AudioDevice.BluetoothHeadset) {
            audioDeviceMenuIcon = R.drawable.ic_bluetooth_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.WiredHeadset) {
            audioDeviceMenuIcon = R.drawable.ic_headset_mic_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.Earpiece) {
            audioDeviceMenuIcon = R.drawable.ic_phonelink_ring_white_24dp;
        } else if (selectedAudioDevice instanceof AudioDevice.Speakerphone) {
            audioDeviceMenuIcon = R.drawable.ic_volume_up_white_24dp;
        }

        if (menuAudioDeviceFab != null) {
            menuAudioDeviceFab.setImageResource(audioDeviceMenuIcon);
        }
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
                SoundPoolManager.getInstance(DemoActivity.this).playRinging();
//                }
            }

            @Override
            public void onConnectFailure(@NonNull Call call, @NonNull CallException error) {
                audioSwitch.deactivate();
//                if (BuildConfig.playCustomRingback) {
                SoundPoolManager.getInstance(DemoActivity.this).stopRinging();
//                }
                Log.d(TAG, "Connect failure");
                String message = String.format(
                        Locale.US,
                        "Call Error: %d, %s",
                        error.getErrorCode(),
                        error.getMessage());
                Log.e(TAG, message);
//                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                resetUI();
            }

            @Override
            public void onConnected(@NonNull Call call) {
                audioSwitch.activate();
//                if (BuildConfig.playCustomRingback) {
                SoundPoolManager.getInstance(DemoActivity.this).stopRinging();
//                }
                Log.d(TAG, "Connected");
                activeCall = call;
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
                audioSwitch.deactivate();
//                if (BuildConfig.playCustomRingback) {
                SoundPoolManager.getInstance(DemoActivity.this).stopRinging();
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
                resetUI();
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
    private void resetUI() {
        callActionFab.show();
        muteActionFab.setImageDrawable(ContextCompat.getDrawable(DemoActivity.this, R.drawable.ic_mic_white_24dp));
        holdActionFab.hide();
        holdActionFab.setBackgroundTintList(ColorStateList
                .valueOf(ContextCompat.getColor(this, R.color.colorAccent)));
        muteActionFab.hide();
        menuAudioDeviceFab.hide();
        hangupActionFab.hide();
        chronometer.setVisibility(View.INVISIBLE);
        chronometer.stop();
    }

    /*
     * Disconnect from Call
     */
    private void disconnect() {
        if (activeCall != null) {
            activeCall.disconnect();
            activeCall = null;
        }
    }

    private void hold() {
        if (activeCall != null) {
            boolean hold = !activeCall.isOnHold();
            activeCall.hold(hold);
            applyFabState(holdActionFab, hold);
        }
    }

    private void mute() {
        if (activeCall != null) {
            boolean mute = !activeCall.isMuted();
            activeCall.mute(mute);
            applyFabState(muteActionFab, mute);
        }
    }

    private void applyFabState(FloatingActionButton button, boolean enabled) {
        // Set fab as pressed when call is on hold
        ColorStateList colorStateList = enabled ?
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.colorPrimaryDark)) :
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.colorAccent));
        button.setBackgroundTintList(colorStateList);
    }

}