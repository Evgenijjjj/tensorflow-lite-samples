package com.evgeny.tensorflowlitesamples.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evgeny.tensorflowlitesamples.R
import com.evgeny.tensorflowlitesamples.ui.theme.TensorFlowLiteSamplesTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun MainPage(
    navController: NavController?,
) {
    val cameraPermission = rememberPermissionState(permission = "android.permission.CAMERA")

    if (cameraPermission.status.isGranted) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text(text = "TensorFlowLite Samples")
            })
            Item(title = "AI Landmark Recognition") {
                navController?.navigate("aiLandmarkRecognition")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Enable camera permission",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    if (cameraPermission.status.isGranted.not()) {
        LaunchedEffect(Unit) {
            cameraPermission.launchPermissionRequest()
        }
    }
}

@Composable
private fun Item(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(text = title, modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_forward_24),
            contentDescription = null,
            modifier = Modifier
                .weight(0.1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
@Preview
internal fun MainPagePreview() {
    TensorFlowLiteSamplesTheme {
        MainPage(navController = null)
    }
}
