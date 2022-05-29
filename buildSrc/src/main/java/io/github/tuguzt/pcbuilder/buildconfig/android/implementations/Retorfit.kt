package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.Retrofit
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.retrofitImplementation() {
    implementation(Retrofit.dependency)
    implementation(Retrofit.ThirdParty.kotlinXSerializationConverter)
    implementation(Retrofit.ThirdParty.networkResponseAdapter)
}
