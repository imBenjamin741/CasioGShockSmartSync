/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-20, 9:42 p.m.
 */
package org.avmedia.gShockPhoneSync.ui.alarms

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.avmedia.gShockPhoneSync.MainActivity.Companion.api
import org.avmedia.gShockPhoneSync.ui.alarms.AlarmsFragment.Companion.getFragmentScope

@SuppressLint("NotifyDataSetChanged")
class AlarmList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    object Cache {
        var adapter: AlarmAdapter? = null
    }

    init {
        adapter = Cache.adapter ?: AlarmAdapter(AlarmsModel.alarms).also { Cache.adapter = it }
        layoutManager = LinearLayoutManager(context)

        runBlocking {
            val alarms = api().getAlarms()

            // update the model
            AlarmsModel.alarms.clear()
            AlarmsModel.alarms.addAll(alarms)

            getFragmentScope().launch(Dispatchers.IO) {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}

