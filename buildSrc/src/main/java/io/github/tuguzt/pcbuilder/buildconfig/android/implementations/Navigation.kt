package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.JetpackCompose
import org.gradle.api.artifacts.dsl.DependencyHandler

// Provide navigation by pairing Jetpack Compose and Hilt
fun DependencyHandler.composeNavigationImplementation() {
    implementation(JetpackCompose.navigation)
    implementation(JetpackCompose.ThirdParty.navigationHilt)
}
