package io.github.tuguzt.pcbuilder.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.github.tuguzt.pcbuilder.BuildConfig
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountListViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Application module of the Koin DI.
 */
val appModule = module {
    viewModel { AccountViewModel(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { ComponentsSharedViewModel(get()) }
    viewModel { (id: String) -> ComponentViewModel(id, get()) }
    viewModel { AccountListViewModel(get()) }

    single {
        val googleSignInOptions: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                .requestEmail()
                .build()
        GoogleSignIn.getClient(androidContext(), googleSignInOptions)
    }
}
