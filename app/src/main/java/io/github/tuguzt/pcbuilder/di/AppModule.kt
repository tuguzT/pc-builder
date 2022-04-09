package io.github.tuguzt.pcbuilder.di

import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountListViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AuthViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Application module of the Koin DI.
 */
val appModule = module {
    viewModel { AccountViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { ComponentsSharedViewModel(get()) }
    viewModel { (id: String) -> ComponentViewModel(id, get()) }
    viewModel { AccountListViewModel(get()) }
}
