package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess

/**
 * View model of 'Account' page.
 */
class AccountViewModel(application: Application) : AndroidViewModel(application) {
    val user
        get() = RepositoryAccess.currentUsername?.let {
            RepositoryAccess.localUserRepository.findById(it)
        }
}
