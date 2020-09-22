package com.example.systemoverlay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SYSTEM_OVERLAY_REQUEST_CODE: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission();
    }

    fun checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
            startActivityForResult(intent, SYSTEM_OVERLAY_REQUEST_CODE)
        } else {
            init()
        }
    }

    fun init() {
        btnStartService.setOnClickListener {
            Intent(this, WindowOverlay::class.java).also {
                startService(it)
            }
            finish()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ( requestCode == SYSTEM_OVERLAY_REQUEST_CODE) {
            if ( resultCode == Activity.RESULT_OK ){
                init()
            } else {
                Toast.makeText(this,
                    "Permission for System Overlay is not granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}