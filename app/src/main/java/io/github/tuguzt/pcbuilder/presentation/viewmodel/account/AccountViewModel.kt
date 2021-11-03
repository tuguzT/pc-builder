package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.model.user.UserSealed

/**
 * View model of 'Account' page.
 */
class AccountViewModel : ViewModel() {
    var currentUser: UserSealed? = null
}
