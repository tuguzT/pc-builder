package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.JetpackCompose
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.composeCoreImplementation() {
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.activity)

    // Android Studio integrations with Jetpack Compose
    implementation(JetpackCompose.AndroidStudio.preview)
    debugImplementation(JetpackCompose.AndroidStudio.tooling)
}

fun DependencyHandler.composeThirdPartyImplementation() {
    implementation(JetpackCompose.ThirdParty.coil)
    implementation(JetpackCompose.ThirdParty.scalableDp)
}

fun DependencyHandler.accompanistImplementation() {
    implementation(JetpackCompose.Accompanist.placeholder)
    implementation(JetpackCompose.Accompanist.swipeToRefresh)
}
