package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.KotlinX
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kotlinXImplementation() {
    implementation(KotlinX.Coroutines.dependency)
    implementation(KotlinX.Coroutines.playServices)
    implementation(KotlinX.serializationJson)
}
