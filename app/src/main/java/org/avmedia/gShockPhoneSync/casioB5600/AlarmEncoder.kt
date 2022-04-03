/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-04-03, 10:57 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-04-03, 10:57 a.m.
 */

package org.avmedia.gShockPhoneSync.casioB5600

import com.google.gson.Gson
import org.avmedia.gShockPhoneSync.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

object AlarmEncoder {
    fun toJson(command: String): JSONObject {
        val jsonResponse = JSONObject()
        val intArray = Utils.toIntArray(command)
        val alarms = JSONArray()

        when (intArray[0]) {
            CasioConstants.CHARACTERISTICS.CASIO_SETTING_FOR_ALM.code -> {
                intArray.removeAt(0)
                alarms.put(createJsonAlarm(intArray))
                jsonResponse.put("ALARMS", alarms)
            }
            CasioConstants.CHARACTERISTICS.CASIO_SETTING_FOR_ALM2.code -> {
                intArray.removeAt(0)
                val multipleAlarms = intArray.chunked(4)
                multipleAlarms.forEach {
                    alarms.put(createJsonAlarm(it as ArrayList<Int>))
                }
                jsonResponse.put("ALARMS", alarms)
            }
            in listOf(
                CasioConstants.CHARACTERISTICS.CASIO_DST_SETTING.code,
                CasioConstants.CHARACTERISTICS.CASIO_WORLD_CITIES.code,
                CasioConstants.CHARACTERISTICS.CASIO_DST_WATCH_STATE.code,
                CasioConstants.CHARACTERISTICS.CASIO_WATCH_NAME.code,
                CasioConstants.CHARACTERISTICS.CASIO_WATCH_CONDITION.code,
            ) -> {
                jsonResponse.put("WATCH_INFO_DATA", command)
            }
            else -> {
                Timber.d("Unhandled Command [$command]")
            }
        }

        return jsonResponse
    }

    private fun createJsonAlarm(intArray: ArrayList<Int>): JSONObject {
        var alarm = Alarms.Alarm(
            intArray[2],
            intArray[3],
            intArray[0] == 0x40
        )
        val gson = Gson()
        return JSONObject(gson.toJson(alarm))
    }
}
