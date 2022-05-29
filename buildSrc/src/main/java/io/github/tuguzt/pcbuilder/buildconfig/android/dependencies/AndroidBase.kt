package io.github.tuguzt.pcbuilder.buildconfig.android.dependencies

import io.github.tuguzt.pcbuilder.buildconfig.android.implementations.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object AndroidBase {
    private const val core = "androidx.core:core-ktx:1.7.0"
    private const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
    private const val material = "com.google.android.material:material:1.6.0"
    private const val security = "androidx.security:security-crypto:1.0.0"

    fun DependencyHandler.androidBaseImplementation() {
        implementation(core)
        implementation(lifecycle)
        implementation(material)
        implementation(security)
    }
}
