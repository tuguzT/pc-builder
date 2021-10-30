package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment

/**
 * Makes a [Toast] with short duration.
 *
 * @see Toast.LENGTH_SHORT
 */
@CheckResult
internal inline fun toastShort(context: Context, text: () -> CharSequence): Toast =
    Toast.makeText(context, text(), Toast.LENGTH_SHORT)

/**
 * Makes a [Toast] with short duration.
 *
 * @see Toast.LENGTH_SHORT
 */
@CheckResult
internal inline fun Fragment.toastShort(text: () -> CharSequence) =
    toastShort(context = requireContext(), text)
