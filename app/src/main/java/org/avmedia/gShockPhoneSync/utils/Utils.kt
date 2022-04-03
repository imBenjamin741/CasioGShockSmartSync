/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-28, 10:38 a.m.
 */

package org.avmedia.gShockPhoneSync.utils

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale
import kotlin.math.max


object Utils {
    public fun String.hexToBytes() =
        this.chunked(2).map { it.uppercase(Locale.US).toInt(16).toByte() }.toByteArray()

    public fun byteArrayOfInts(vararg ints: Int) =
        ByteArray(ints.size) { pos -> ints[pos].toByte() }

    public fun byteArrayOfIntArray(intArray: IntArray) =
        ByteArray(intArray.size) { pos -> intArray[pos].toByte() }

    public fun toByteArray(string: String):ByteArray {
        val charset = Charsets.UTF_8
        return string.toByteArray(charset)
    }

    public fun toByteArray(string: String, maxLen: Int):ByteArray {
        val charset = Charsets.UTF_8
        var retArr = string.toByteArray(charset)
        if (retArr.size > maxLen) {
            return retArr.take(maxLen).toByteArray()
        }
        if (retArr.size < maxLen) {
            return retArr + ByteArray(maxLen - retArr.size)
        }

        return retArr
    }

    fun toHexStr (asciiStr: String) : String {
        var byteArr = toByteArray(asciiStr)
        var hexStr = ""
        byteArr.forEach {
            // hexStr += it.toString(16)
            hexStr += "%02x".format(it)
        }
        return hexStr
    }

    public fun byteArray(vararg bytes: Byte) = ByteArray(bytes.size) { pos -> bytes[pos] }

    public fun toast(context: Context, message: String) {
        val toast: Toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun toIntArray(hexStr: String): ArrayList<Int> {
        val intArr = ArrayList<Int>()
        val strArray = hexStr.split(' ')
        strArray.forEach {
            var s = it
            if (s.startsWith("0x")) {
                s = s.removePrefix("0x")
            }
            intArr.add(Integer.parseInt(s, 16))
        }

        return intArr
    }

    fun toAsciiString(hexStr: String, commandLengthToSkip: Int): String {
        var asciiStr = ""
        val strArrayWithCommand = hexStr.split(' ')

        // skip command
        val strArray = strArrayWithCommand.subList(commandLengthToSkip, strArrayWithCommand.size)
        strArray.forEach {
            var s = it
            if (s.startsWith("0x")) {
                s = s.removePrefix("0x")
            }
            asciiStr += Integer.parseInt(s, 16).toChar()
        }

        return asciiStr
    }

    fun toCompactString(hexStr: String): String {
        var compactString = ""
        val strArray = hexStr.split(' ')
        strArray.forEach {
            var s = it
            if (s.startsWith("0x")) {
                s = s.removePrefix("0x")
            }
            compactString += s
        }

        return compactString
    }

    // JSON safe functions, prevent throwing exceptions
    public fun JSONObject.getStringSafe(name: String): String? {
        if (!has(name)) {
            return null
        }
        return getString(name)
    }

    public fun JSONObject.getBooleanSafe(name: String): Boolean? {
        if (!has(name)) {
            return null
        }
        return getBoolean(name)
    }

    public fun JSONObject.getJSONObjectSafe(name: String): JSONObject? {
        if (!has(name)) {
            return null
        }
        return getJSONObject(name)
    }

    public fun JSONObject.getJSONArraySafe(name: String): JSONArray? {
        if (!has(name)) {
            return null
        }
        return getJSONArray(name)
    }

    public fun JSONObject.getIntSafe(name: String): Int? {
        if (!has(name)) {
            return null
        }
        return getInt(name)
    }
}