package io.github.tuguzt.pcbuilder.view.builds

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

/**
 * Application screen which represents *Builds* main application destination.
 */
@Composable
fun BuildsScreen(onTitleChanged: (String) -> Unit) {
    val appName = stringResource(R.string.app_name)
    SideEffect { onTitleChanged(appName) }

    Text(stringResource(R.string.builds))
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun BuildsScreenPreview() {
    PCBuilderTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BuildsScreen(onTitleChanged = {})
        }
    }
}
