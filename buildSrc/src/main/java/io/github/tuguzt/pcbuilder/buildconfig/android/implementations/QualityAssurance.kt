package io.github.tuguzt.pcbuilder.buildconfig.android.implementations

import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.QualityAssurance
import io.github.tuguzt.pcbuilder.buildconfig.android.dependencies.JetpackCompose
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.unitTestingImplementation() {
    testImplementation(QualityAssurance.UnitTest.jUnit4)
    androidTestImplementation(QualityAssurance.UnitTest.jUnitAndroidX)
}

fun DependencyHandler.loggingImplementation() {
    implementation(QualityAssurance.Log.slf4j)
    implementation(QualityAssurance.Log.loggingKotlin)
}

fun DependencyHandler.instrumentTestingImplementation() {
    debugImplementation(JetpackCompose.InstrumentTest.layout)
    androidTestImplementation(JetpackCompose.InstrumentTest.jUnit4)
}
