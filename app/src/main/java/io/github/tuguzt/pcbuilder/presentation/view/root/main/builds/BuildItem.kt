package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildItem(
    build: BuildData,
    shape: Shape = MaterialTheme.shapes.medium,
    imageShape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
    onClose: () -> Unit,
) {
    val buildImageUri: String? = null // todo pick image for build

    Box {
        ElevatedCard(shape = shape, onClick = onClick) {
            Row(modifier = Modifier.padding(16.dp)) {
                var imageState: AsyncImagePainter.State by remember {
                    mutableStateOf(AsyncImagePainter.State.Empty)
                }
                if (imageState !is AsyncImagePainter.State.Error) {
                    AsyncImage(
                        model = buildImageUri,
                        contentDescription = buildImageUri?.let { stringResource(R.string.build_picture) },
                        modifier = Modifier
                            .size(128.dp)
                            .clip(imageShape)
                            .placeholder(
                                visible = imageState is AsyncImagePainter.State.Loading,
                                highlight = PlaceholderHighlight.fade(),
                            ),
                        contentScale = ContentScale.Crop,
                        onState = { imageState = it },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Computer,
                        contentDescription = stringResource(R.string.image_not_loaded),
                        modifier = Modifier
                            .size(128.dp)
                            .clip(imageShape),
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    DisableSelection {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            text = build.name,
                            maxLines = 2,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
        ) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun BuildItemPreview() {
    PCBuilderTheme {
        val build = BuildData(
            name = "Hello World",
            case = null,
            cooler = null,
            centralProcessingUnit = null,
            graphicsProcessingUnit = listOf(),
            memory = listOf(),
            monitor = listOf(),
            motherboard = null,
            powerSupplyUnit = null,
            storage = listOf(),
        )
        Surface {
            BuildItem(
                build = build,
                onClick = {},
                onClose = {},
            )
        }
    }
}
