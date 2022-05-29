package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.JetpackCompose
import org.gradle.api.artifacts.dsl.DependencyHandler

// Provide app theming with Material 3
fun DependencyHandler.materialThemeImplementation() {
    implementation(JetpackCompose.Material.You.dependency)
    implementation(JetpackCompose.Material.You.windowSizeClass)
}

// Provide Material icons for use without downloading files
fun DependencyHandler.materialIconImplementation() {
    implementation(JetpackCompose.Material.Icons.core)
    implementation(JetpackCompose.Material.Icons.extended)
}

// Provide features to build complete Material app
fun DependencyHandler.materialDesignImplementation() {
    materialIconImplementation()
    materialThemeImplementation()
}
