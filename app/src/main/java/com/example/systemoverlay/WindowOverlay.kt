package com.example.systemoverlay


import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button


class WindowOverlay : Service() {


    private lateinit var windowManager : WindowManager
    private var overlayView : View? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun onClick(view: View) {


        when(view.id) {
            R.id.btnOpenMainActivity -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                stopSelf()
            }
            R.id.btnCloseWindow -> stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay, null)

        val params : WindowManager.LayoutParams
        val layoutParamsType: Int = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutParamsType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(overlayView, params)



        val btnCloseWindow =
            overlayView!!.findViewById<Button>(R.id.btnCloseWindow)
        val btnOpenMainActivity =
            overlayView!!.findViewById<Button>(R.id.btnOpenMainActivity)

        btnCloseWindow.setOnClickListener {
            stopSelf()
        }

        btnOpenMainActivity.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayView != null) {
            windowManager.removeView(overlayView)
        }
    }
}