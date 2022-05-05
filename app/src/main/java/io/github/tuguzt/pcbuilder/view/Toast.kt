@file:Suppress("unused")

package io.github.tuguzt.pcbuilder.view

import android.content.Context
import android.widget.Toast

/**
 * Represents [Toast] possible show durations.
 */
enum class ToastDuration(val value: Int) {
    Short(Toast.LENGTH_SHORT),
    Long(Toast.LENGTH_LONG),
}

/**
 * Shows simple [Toast] when called.
 */
fun showToast(context: Context, text: String, duration: ToastDuration) =
    Toast.makeText(context, text, duration.value).show()
