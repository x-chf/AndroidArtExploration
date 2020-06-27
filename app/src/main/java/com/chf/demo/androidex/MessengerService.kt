package com.chf.demo.androidex

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import kotlin.system.exitProcess

class MessengerService : Service() {
    companion object {
        const val TAG = "MessengerService"
        const val MSG_FROM_CLIENT = 1
        const val MSG_FROM_SERVER = 2
    }
    private inner class MessengerHandler: Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FROM_CLIENT -> {
                    //Log.i(TAG, "receive msg from client " +msg.obj.toString())
                    val client = msg.replyTo
                    val sendToClientMsg = Message.obtain(null, MSG_FROM_SERVER)
                    val bundle = Bundle()
                    bundle.putString("Hello", "Hello")
                    sendToClientMsg.data = bundle
                    client.send(sendToClientMsg)
                }
                else ->
                    super.handleMessage(msg)
            }
            //exitProcess(0)
        }
    }

    private val mMessenger = Messenger(MessengerHandler())


    override fun onBind(intent: Intent): IBinder {
        return mMessenger.binder
    }
}
