package io.github.tuguzt.pcbuilder.presentation.view

import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Makes a [Snackbar] with short duration.
 *
 * @see Snackbar.LENGTH_SHORT
 */
@CheckResult
internal inline fun Fragment.snackbarShort(text: () -> CharSequence): Snackbar =
    Snackbar.make(requireView(), text(), Snackbar.LENGTH_SHORT)
