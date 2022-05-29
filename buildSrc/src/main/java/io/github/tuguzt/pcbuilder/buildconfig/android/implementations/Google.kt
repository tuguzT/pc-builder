package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.Google
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.googleImplementation() {
    implementation(Google.PlayServices.auth)
}
