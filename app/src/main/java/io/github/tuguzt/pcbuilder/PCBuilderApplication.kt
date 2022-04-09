package io.github.tuguzt.pcbuilder

import android.app.Application
import io.github.tuguzt.pcbuilder.di.appModule
import io.github.tuguzt.pcbuilder.di.networkModule
import io.github.tuguzt.pcbuilder.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Class of the whole application state.
 */
@Suppress("UNUSED")
class PCBuilderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule, repositoryModule, networkModule)
        }
    }
}
