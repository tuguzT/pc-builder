package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

enum class ToastDuration(val duration: Int) {
    Short(Toast.LENGTH_SHORT),
    Long(Toast.LENGTH_LONG),
}

inline fun showToast(
    context: Context,
    text: CharSequence,
    duration: ToastDuration = ToastDuration.Short,
    configuration: Toast.() -> Unit = {},
) {
    val toast = Toast.makeText(context, text, duration.duration)
    toast.configuration()
    toast.show()
}

inline fun showToast(
    context: Context,
    @StringRes resId: Int,
    duration: ToastDuration = ToastDuration.Short,
    configuration: Toast.() -> Unit = {},
) {
    val toast = Toast.makeText(context, resId, duration.duration)
    toast.configuration()
    toast.show()
}
