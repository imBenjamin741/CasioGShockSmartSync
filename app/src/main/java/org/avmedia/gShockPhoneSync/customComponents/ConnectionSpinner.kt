/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-29, 11:13 a.m.
 */

/*
 * Developed for the OpenBot project (https://openbot.org) by:
 *
 * Ivo Zivkov
 * izivkov@gmail.com
 *
 * Date: 2020-12-27, 10:56 p.m.
 */

package org.avmedia.gShockPhoneSync.customComponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import org.avmedia.gShockPhoneSync.utils.ProgressEvents
import timber.log.Timber


class ConnectionSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr) {

    init {
        visibility = View.INVISIBLE
        createAppEventsSubscription()
    }

    private fun createAppEventsSubscription() {
        ProgressEvents.subscriber.start(
            this.javaClass.simpleName,

            {
                when (it) {
                    ProgressEvents.Events.ConnectionStarted -> {
                        visibility = View.VISIBLE
                    }
                    ProgressEvents.Events.PhoneDataCollected -> {
                        visibility = View.INVISIBLE
                    }
                }
            },
            { throwable -> Timber.d("Got error on subscribe: $throwable") })
    }

}
