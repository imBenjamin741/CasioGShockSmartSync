/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-14, 1:48 p.m.
 */

package org.avmedia.gShockPhoneSync.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import org.avmedia.gShockPhoneSync.databinding.FragmentSettingsBinding
import org.avmedia.gshockapi.ProgressEvents

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    init {
        settingsFragmentScope = lifecycleScope
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _binding?.settingsList?.init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded && view != null) {
            _binding?.settingsList?.init()
        } else {
            ProgressEvents.onNext("ApiError")
        }
    }

    @Override
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private lateinit var settingsFragmentScope: LifecycleCoroutineScope

        fun getFragmentScope(): LifecycleCoroutineScope {
            if (!this::settingsFragmentScope.isInitialized) {
                ProgressEvents.onNext("ApiError")
            }
            return settingsFragmentScope
        }
    }
}