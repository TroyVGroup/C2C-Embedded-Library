package com.vgroup.c2cbuisness

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vgroup.c2cembedcode.C2CVoiceActivity
import com.vgroup.c2cembedcode.CallActivity
import com.vgroup.c2cembedcode.C2CConstants
import com.vgroup.c2cembedcode.pojo.Modes

class IconActivity : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var modes: Modes = Modes()
    val ALL_PERMISSIONS = 101
    val channelId = "64538a3e23426f1086e09e9b"
    val origin = "https://testvg20.weebly.com"
    val website = "https://testvg20.weebly.com/naresh-new-page.html"
    var c2cVoiceActivity: C2CVoiceActivity = C2CVoiceActivity(this@IconActivity,origin,website)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon_activity)

        call_icon = findViewById(R.id.call_icon)
        msg_icon = findViewById(R.id.msg_icon)
        email_icon = findViewById(R.id.email_icon)

        c2cVoiceActivity.getModes(channelId, origin,website, modes, call_icon, msg_icon, email_icon)

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
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)

    }
}