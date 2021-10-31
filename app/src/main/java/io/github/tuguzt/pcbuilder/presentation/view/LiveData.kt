package io.github.tuguzt.pcbuilder.presentation.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Observes data behind [LiveData] exactly once.
 *
 * @see LiveData
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner? = null, observer: Observer<T>) {
    @Suppress("NAME_SHADOWING")
    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    }
    if (lifecycleOwner != null) {
        observe(lifecycleOwner, observer)
        return
    }
    observeForever(observer)
}
