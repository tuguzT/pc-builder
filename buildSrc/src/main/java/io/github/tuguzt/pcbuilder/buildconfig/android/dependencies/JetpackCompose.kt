package io.github.tuguzt.pcbuilder.buildconfig.android.dependencies

object JetpackCompose {
    const val version = "1.2.0-beta02"

    const val ui = "androidx.compose.ui:ui:$version"
    const val activity = "androidx.activity:activity-compose:1.4.0"
    const val navigation = "androidx.navigation:navigation-compose:2.4.2"

    object Material {
        object Icons {
            const val core = "androidx.compose.material:material-icons-core:$version"
            const val extended = "androidx.compose.material:material-icons-extended:$version"
        }

        const val dependency = "androidx.compose.material:material:$version"

        object You {
            private const val version = "1.0.0-alpha12"

            const val dependency = "androidx.compose.material3:material3:$version"
            const val windowSizeClass =
                "androidx.compose.material3:material3-window-size-class:$version"
        }
    }

    object Accompanist {
        private const val version = "0.24.9-beta"

        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
        const val swipeToRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object AndroidStudio {
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
    }

    object ThirdParty {
        const val coil = "io.coil-kt:coil-compose:2.1.0"
        const val scalableDp = "com.intuit.sdp:sdp-android:1.0.6"
        const val navigationHilt = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object InstrumentTest {
        const val jUnit4 = "androidx.compose.ui:ui-test-junit4:$version"
        const val layout = "androidx.compose.ui:ui-test-manifest:$version"
    }
}
