package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

enum class SnackbarDuration(val duration: Int) {
    Indefinite(Snackbar.LENGTH_INDEFINITE),
    Short(Snackbar.LENGTH_SHORT),
    Long(Snackbar.LENGTH_LONG),
}

inline fun showSnackbar(
    context: Context,
    view: View,
    text: CharSequence,
    duration: SnackbarDuration = SnackbarDuration.Short,
    configuration: Snackbar.() -> Unit = {},
) {
    val snackbar = Snackbar.make(context, view, text, duration.duration)
    snackbar.configuration()
    snackbar.show()
}

inline fun showSnackbar(
    view: View,
    text: CharSequence,
    duration: SnackbarDuration = SnackbarDuration.Short,
    configuration: Snackbar.() -> Unit = {},
) {
    val snackbar = Snackbar.make(view, text, duration.duration)
    snackbar.configuration()
    snackbar.show()
}

inline fun showSnackbar(
    view: View,
    @StringRes resId: Int,
    duration: SnackbarDuration = SnackbarDuration.Short,
    configuration: Snackbar.() -> Unit = {},
) {
    val snackbar = Snackbar.make(view, resId, duration.duration)
    snackbar.configuration()
    snackbar.show()
}
