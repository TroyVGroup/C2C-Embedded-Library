package com.vgroup.c2c_embedded_library

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vgroup.c2c_embedded_library.pojo.Modes

class MainActivity : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var modes: Modes = Modes()
    val ALL_PERMISSIONS = 101
    val channelId = "65c1e0d8632f4ced4d676ba3"
//    val channelId = "657ad6d2632f4ca91909cdbc"
//    val origin = "https://testvg20.weebly.com"
//    val website = "https://testvg20.weebly.com/naresh-new-page.html"
//    val packageName = "com.example.android"
    var c2cVoiceActivity: C2CVoiceActivity = C2CVoiceActivity(this@MainActivity,"com.example.android","com.example.android")
    lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
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
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)

    }
}