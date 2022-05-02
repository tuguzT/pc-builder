package io.github.tuguzt.pcbuilder.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    PCBuilderTheme {
        Greeting("Android")
    }
}
