package com.vgroup.c2cbuisness

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vgroup.c2cembedcode.C2CConstants
import com.vgroup.c2cembedcode.C2CVoiceActivity
import com.vgroup.c2cembedcode.pojo.Modes

class IconActivity : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var modes: Modes = Modes()
    val ALL_PERMISSIONS = 101
    val channelId = "657ad6d2632f4ca91909cdbc"
//    val origin = "https://testvg20.weebly.com"
//    val website = "https://testvg20.weebly.com/naresh-new-page.html"
//    val packageName = "com.example.android"
    var c2cVoiceActivity: C2CVoiceActivity = C2CVoiceActivity(this@IconActivity,"com.example.android","com.example.android")
    lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon_activity)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        call_icon = findViewById(R.id.c2cCall)
        msg_icon = findViewById(R.id.c2cMsg)
        email_icon = findViewById(R.id.c2cEmail)

        c2cVoiceActivity.getModes(channelId, "com.example.android","com.example.android", modes, call_icon, msg_icon, email_icon)

        call_icon!!.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            )
            if (permission == PackageManager.PERMISSION_GRANTED) {
                c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.CALL, locationManager)
            } else {
                c2cVoiceActivity.showError("Message", "Allow permission from setting to make call")
            }
        }

        msg_icon!!.setOnClickListener {
            c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.SMS,locationManager)
        }
        email_icon!!.setOnClickListener {
            c2cVoiceActivity.getCallDetails(channelId, modes, C2CConstants.EMAIL,locationManager)
        }

        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)

    }
}