package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser

/**
 * View model of 'Account' page.
 */
class AccountViewModel(application: Application) : AndroidViewModel(application) {
    val account get() = GoogleSignIn
        .getLastSignedInAccount(getApplication<Application>().applicationContext)!!
        .toUser()
}
