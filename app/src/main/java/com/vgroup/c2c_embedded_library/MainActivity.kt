package com.vgroup.c2c_embedded_library

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vgroup.c2c_embedded_library.pojo.Modes

class MainActivity : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var modes: Modes = Modes()
    val ALL_PERMISSIONS = 101
    val channelId = "65dcfc07632f4c62504a3368"
    var c2cVoiceActivity: C2CVoiceActivity = C2CVoiceActivity(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        call_icon = findViewById(R.id.c2c_Call)
        msg_icon = findViewById(R.id.c2c_Msg)
        email_icon = findViewById(R.id.c2c_Email)
        c2cVoiceActivity.getModes(channelId, modes, call_icon, msg_icon, email_icon)

        call_icon!!.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            )
            if (permission == PackageManager.PERMISSION_GRANTED) {
                c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.CALL)
            } else {
                c2cVoiceActivity.showError("Message", "Allow permission from setting to make call")
            }
        }

        msg_icon!!.setOnClickListener {
            c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.SMS)
        }
        email_icon!!.setOnClickListener {
            c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.EMAIL)
        }

        val permissions = arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)

    }
}