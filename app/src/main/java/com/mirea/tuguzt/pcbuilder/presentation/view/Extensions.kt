package com.mirea.tuguzt.pcbuilder.presentation.view

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Makes a [Snackbar] and shows it for the short period of time.
 *
 * @see Snackbar.LENGTH_SHORT
 */
internal inline fun Fragment.snackbarShort(text: () -> CharSequence): Unit =
    Snackbar.make(requireView(), text(), Snackbar.LENGTH_SHORT).show()
