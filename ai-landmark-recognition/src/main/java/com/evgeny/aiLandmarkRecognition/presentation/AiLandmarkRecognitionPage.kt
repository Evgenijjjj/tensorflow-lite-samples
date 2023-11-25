package com.evgeny.aiLandmarkRecognition.presentation

import androidx.activity.compose.BackHandler
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.evgeny.aiLandmarkRecognition.domain.model.Classification
import com.evgeny.archBase.compose.DisposableEffectWithLifeCycle

@Composable
fun AiLandmarkRecognitionPage(
    navController: NavController?,
) {
    val viewModel = hiltViewModel<AiLandmarkRecognitionViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current
    val results by viewModel.imageAnalyzerResults.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    controller = viewModel.cameraController
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            results.forEach {
                ClassificationItem(it)
            }
        }
    }

    DisposableEffectWithLifeCycle(
        onCreate = {
            viewModel.cameraController.bindToLifecycle(lifecycleOwner)
        },
        onDestroy = {
            viewModel.cameraController.unbind()
        }
    )

    BackHandler {
        navController?.popBackStack()
    }
}

@Composable
private fun ClassificationItem(item: Classification) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = item.name, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = String.format("%.2f", item.score))
    }
}
