package io.github.tuguzt.pcbuilder.di

import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Application module of the Koin DI.
 */
val appModule = module {
    single { json() }
    viewModel { AccountViewModel() }
}

private fun json() = Json { ignoreUnknownKeys = true }
