package io.github.tuguzt.pcbuilder.presentation.view

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

/**
 * If this fragment would like to participate in populating the options menu
 * by receiving a call to [Fragment.onCreateOptionsMenu] and related methods.
 */
inline var Fragment.hasOptionsMenu
    @SuppressLint("RestrictedApi")
    get() = hasOptionsMenu()
    set(value) = setHasOptionsMenu(value)
