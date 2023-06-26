/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-20, 9:42 p.m.
 */
package org.avmedia.gShockPhoneSync.ui.alarms

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import org.avmedia.gShockPhoneSync.MainActivity.Companion.api
import org.avmedia.gShockPhoneSync.ui.events.EventAdapter
import org.avmedia.gShockPhoneSync.ui.events.EventList
import org.avmedia.gShockPhoneSync.ui.events.EventsModel

class AlarmList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    object AdapterValue {
        var adapter: AlarmAdapter? = null
    }

    init {
        // Save adapter for re-use
        adapter = AdapterValue.adapter ?:  AlarmAdapter(AlarmsModel.alarms).also { AdapterValue.adapter = it }
        // adapter = AlarmAdapter(AlarmsModel.alarms)

        layoutManager = LinearLayoutManager(context)

        runBlocking {
            var alarms = api().getAlarms()

            // update the model
            AlarmsModel.alarms.clear()
            AlarmsModel.alarms.addAll(alarms)

            runBlocking {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}

