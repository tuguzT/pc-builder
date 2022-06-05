package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel

/**
 * Application screen which represents *Builds* main application destination.
 */
@Composable
fun BuildsScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val appName = stringResource(R.string.app_name)
    SideEffect {
        mainViewModel.updateTitle(appName)
        mainViewModel.updateFilled(isFilled = false)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.coming_soon),
            style = MaterialTheme.typography.headlineMedium,
        )
    }
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
            BuildsScreen()
        }
    }
}
