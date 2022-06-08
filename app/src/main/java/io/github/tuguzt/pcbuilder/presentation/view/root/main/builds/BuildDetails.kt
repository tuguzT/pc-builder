package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.utils.SimpleProperty

@Composable
fun BuildDetails(
    build: BuildData,
    scrollState: ScrollState = rememberScrollState(),
) {
    val none = stringResource(R.string.none)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        BuildImage(build)
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            SimpleProperty(
                name = stringResource(R.string.name),
                value = build.name,
            )
            Divider()
            SimpleProperty(
                name = stringResource(R.string.case_label),
                value = build.case?.name ?: none,
            )
            Divider()
            SimpleProperty(
                name = stringResource(R.string.motherboard),
                value = build.motherboard?.name ?: none,
            )
        }
    }
}

@Composable
private fun BuildImage(build: BuildData) {
    val buildImageUri: String? = null

    val imageShape = MaterialTheme.shapes.medium.copy(
        topStart = ZeroCornerSize,
        topEnd = ZeroCornerSize,
    )
    var imageState: AsyncImagePainter.State by remember {
        mutableStateOf(AsyncImagePainter.State.Empty)
    }
    if (imageState !is AsyncImagePainter.State.Error && buildImageUri != null) {
        AsyncImage(
            model = buildImageUri,
            contentDescription = stringResource(R.string.component_picture),
            modifier = Modifier
                .heightIn(min = 240.dp)
                .fillMaxWidth()
                .clip(imageShape)
                .placeholder(
                    visible = imageState is AsyncImagePainter.State.Loading,
                    highlight = PlaceholderHighlight.fade(),
                ),
            onState = { imageState = it },
        )
    } else {
        Surface(tonalElevation = 2.dp, shape = imageShape) {
            Icon(
                modifier = Modifier
                    .heightIn(min = 240.dp)
                    .fillMaxWidth(),
                imageVector = Icons.Rounded.Computer,
                contentDescription = stringResource(R.string.image_not_loaded),
            )
        }
    }
}
