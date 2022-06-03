package io.github.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.pcbuilder.presentation.view.root.RootScreen
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme

/**
 * Entry point of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PCBuilderTheme { RootScreen() }
        }
    }
}
