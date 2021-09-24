package io.github.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val mutableActivityState = MutableLiveData<MainActivityState>()
    val activityState: LiveData<MainActivityState> get() = mutableActivityState

    fun setActivityState(state: MainActivityState) {
        viewModelScope.launch(context = Dispatchers.Main) {
            mutableActivityState.value = state
        }
    }
}
