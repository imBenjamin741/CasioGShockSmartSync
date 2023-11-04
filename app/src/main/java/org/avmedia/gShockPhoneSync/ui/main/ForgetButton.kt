/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-22, 1:55 p.m.
 */

package org.avmedia.gShockPhoneSync.ui.main

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.avmedia.gShockPhoneSync.customComponents.Button
import org.avmedia.gShockPhoneSync.utils.LocalDataStorage
import org.avmedia.gshockapi.EventAction
import org.avmedia.gshockapi.ProgressEvents
import org.avmedia.gshockapi.WatchInfo
import org.avmedia.gshockapi.ble.Connection
import org.avmedia.gshockapi.ble.DeviceCharacteristics
import org.avmedia.gshockapi.io.CachedIO
import timber.log.Timber

class ForgetButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    init {
        setOnTouchListener(OnTouchListener())
        listenForConnection()
    }

    private fun listenForConnection() {

        val eventActions = arrayOf(
            EventAction("ConnectionStarted") { _ ->
                isEnabled = false
            },
            EventAction("WatchInitializationCompleted") { _ ->
                isEnabled = true
            },
            EventAction("ConnectionFailed") { _ ->
                isEnabled = true
            },
            EventAction("Disconnect") { _ ->
                isEnabled = true
            },
        )

        ProgressEvents.subscriber.runEventActions(this.javaClass.canonicalName, eventActions)
    }

    inner class OnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    WatchInfo.reset()

                    LocalDataStorage.delete("LastDeviceAddress", context)
                    LocalDataStorage.delete("LastDeviceName", context)

                    Connection.breakWait()
                    ProgressEvents.onNext("DeviceName", "")
                    ProgressEvents.onNext("WaitForConnection")
                }
            }
            v?.performClick()
            return false
        }
    }
}
