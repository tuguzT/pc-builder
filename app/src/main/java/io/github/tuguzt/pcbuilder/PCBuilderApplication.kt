package io.github.tuguzt.pcbuilder

import android.app.Application
import io.github.tuguzt.pcbuilder.di.appModule
import io.github.tuguzt.pcbuilder.di.networkModule
import io.github.tuguzt.pcbuilder.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Class of the whole application state.
 */
@Suppress("UNUSED")
class PCBuilderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // TODO Koin can't work properly on Kotlin 1.6.0
            androidLogger(Level.ERROR)
            androidContext(androidContext = this@PCBuilderApplication)
            modules(appModule, repositoryModule, networkModule)
        }
    }
}
