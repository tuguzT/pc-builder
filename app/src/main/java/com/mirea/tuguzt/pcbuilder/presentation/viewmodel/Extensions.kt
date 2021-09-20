package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Launches a new coroutine from the tied [scope][ViewModel.viewModelScope] with [Dispatchers.IO].
 */
fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(context = Dispatchers.IO, block = block)
