package com.chf.demo.androidex

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var mService: Messenger? = null
    private val mReplyMessenger = Messenger(object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MessengerService.MSG_FROM_SERVER -> {
                    val bundle = msg.data

                    Log.i(TAG, "receive from server ${ bundle.getString("Hello")}")

                }
            }
        }
    })
    private val mConnection = object: ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.i(TAG, "onServiceConnected")
            mService = Messenger(p1)
            val msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT)
            //msg.obj = "hello"
            msg.replyTo = mReplyMessenger
            try {
                //mReplyMessenger.send(msg)
                mService?.send(msg)
                Log.i(TAG, "send msg to server")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }

    private fun initView() {
        jump_book_manager_activity.setOnClickListener {
            val intent = Intent(this.baseContext, BookManagerActivity::class.java)
            startActivity(intent)
        }
    }
}
