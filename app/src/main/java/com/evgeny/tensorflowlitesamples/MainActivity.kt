package com.evgeny.tensorflowlitesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.evgeny.aiLandmarkRecognition.presentation.AiLandmarkRecognitionPage
import com.evgeny.tensorflowlitesamples.feature.home.MainPage
import com.evgeny.tensorflowlitesamples.ui.theme.TensorFlowLiteSamplesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TensorFlowLiteSamplesTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainPage(navController = navController)
                        }
                        composable("aiLandmarkRecognition") {
                            AiLandmarkRecognitionPage(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
