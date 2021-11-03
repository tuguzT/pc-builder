package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Context
import android.content.SharedPreferences

/**
 * Get [SharedPreferences] of the application for user settings.
 */
val Context.userSharedPreferences: SharedPreferences
    get() = getSharedPreferences("USER_SETTINGS", Context.MODE_PRIVATE)
