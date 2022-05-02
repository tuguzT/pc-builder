package io.github.tuguzt.pcbuilder.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

/**
 * Entry point of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PCBuilderTheme { MainScreen() }
        }
    }
}
