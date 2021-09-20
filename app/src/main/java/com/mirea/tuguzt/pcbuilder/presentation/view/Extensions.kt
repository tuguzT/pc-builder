package com.mirea.tuguzt.pcbuilder.presentation.view

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

internal inline fun Fragment.snackBarShort(text: () -> CharSequence): Unit =
    Snackbar.make(requireView(), text(), Snackbar.LENGTH_SHORT).show()
