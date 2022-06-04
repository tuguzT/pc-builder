package io.github.tuguzt.pcbuilder.buildconfig.android.dependencies

import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object AndroidBase {
    const val core = "androidx.core:core-ktx:1.7.0"
    private const val splashScreen = "androidx.core:core-splashscreen:1.0.0-beta02"
    private const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
    private const val material = "com.google.android.material:material:1.6.0"
    const val security = "androidx.security:security-crypto:1.0.0"

    fun DependencyHandler.androidBaseImplementation() {
        implementation(core)
        implementation(lifecycle)
        implementation(material)
        implementation(security)
        implementation(splashScreen)
    }
}
