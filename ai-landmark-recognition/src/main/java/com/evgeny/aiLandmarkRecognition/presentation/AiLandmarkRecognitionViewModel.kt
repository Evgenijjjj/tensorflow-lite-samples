package com.evgeny.aiLandmarkRecognition.presentation

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class AiLandmarkRecognitionViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val landmarkImageAnalyzer: LandmarkImageAnalyzer,
) : ViewModel() {

    val cameraController = LifecycleCameraController(context).apply {
        setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
        setImageAnalysisAnalyzer(Dispatchers.Default.asExecutor(), landmarkImageAnalyzer)
    }

    val imageAnalyzerResults = landmarkImageAnalyzer.results.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.Lazily
    )
}
