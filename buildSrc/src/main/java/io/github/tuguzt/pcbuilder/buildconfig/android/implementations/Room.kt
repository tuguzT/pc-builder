package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.Room
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.roomImplementation() {
    implementation(Room.dependency)
    kapt(Room.compiler)
}
