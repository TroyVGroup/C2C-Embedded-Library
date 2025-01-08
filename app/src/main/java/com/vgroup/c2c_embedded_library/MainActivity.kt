package com.vgroup.c2c_embedded_library

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vgroup.c2c_embedded_library.pojo.Modes

class MainActivity : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var ALL_PERMISSIONS = 101
    var channelId = "673c82c1c1f63788c8d2c602"//Dev82
    var modes: Modes = Modes()
    var c2cVoiceActivity: C2CVoiceActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        call_icon = findViewById(R.id.c2c_Call)
        msg_icon = findViewById(R.id.c2c_Msg)
        email_icon = findViewById(R.id.c2c_Email)
        c2cVoiceActivity  = C2CVoiceActivity(this@MainActivity) // Your Activity Name
        c2cVoiceActivity?.getModes(channelId, modes, call_icon, msg_icon, email_icon)
        call_icon?.setOnClickListener(View.OnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                c2cVoiceActivity?.getCallDetails(channelId, modes, C2CConstants.CALL)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity,
                        Manifest.permission.RECORD_AUDIO
                    )
                ) {
                    c2cVoiceActivity?.showError(
                        "Message",
                        "Allow permission from setting to make call"
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        ALL_PERMISSIONS
                    )
                }
            }
        })
        msg_icon?.setOnClickListener(View.OnClickListener {
            c2cVoiceActivity?.getCallDetails(
                channelId,
                modes,
                C2CConstants.SMS
            )
        })
        email_icon?.setOnClickListener(View.OnClickListener {
            c2cVoiceActivity?.getCallDetails(
                channelId,
                modes,
                C2CConstants.EMAIL
            )
        })
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(this@MainActivity, permissions, ALL_PERMISSIONS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult","hey")
        c2cVoiceActivity?.handleActivityResult(requestCode,resultCode,data);
    }

}