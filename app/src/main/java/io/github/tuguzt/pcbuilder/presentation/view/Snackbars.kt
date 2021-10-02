package io.github.tuguzt.pcbuilder.presentation.view

import android.view.View
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Makes a [Snackbar] with short duration.
 *
 * @see Snackbar.LENGTH_SHORT
 */
@CheckResult
internal inline fun snackbarShort(view: View, text: () -> CharSequence): Snackbar =
    Snackbar.make(view, text(), Snackbar.LENGTH_SHORT)

/**
 * Makes a [Snackbar] with short duration.
 *
 * @see Snackbar.LENGTH_SHORT
 */
@CheckResult
internal inline fun Fragment.snackbarShort(
    view: View = requireView(),
    text: () -> CharSequence,
): Snackbar = io.github.tuguzt.pcbuilder.presentation.view.snackbarShort(view, text)
