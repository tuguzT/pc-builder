package io.github.tuguzt.pcbuilder.buildconfig.android.dependencies

object KotlinX {
    const val group = "org.jetbrains.kotlinx"

    object Coroutines {
        const val version = "1.6.2"
        const val dependency = "$group:kotlinx-coroutines-android:$version"
        const val playServices = "$group:kotlinx-coroutines-play-services:$version"
    }

    const val serializationJson = "$group:kotlinx-serialization-json:1.3.2"

    object Test {
        const val coroutines = "$group:kotlinx-coroutines-test:${Coroutines.version}"
        const val excludedModule = "kotlinx-coroutines-debug"
    }
}
