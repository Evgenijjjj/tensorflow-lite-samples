package com.evgeny.aiLandmarkRecognition.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.evgeny.aiLandmarkRecognition.DefaultCoroutineScope
import com.evgeny.aiLandmarkRecognition.domain.LandmarkClassifier
import com.evgeny.aiLandmarkRecognition.domain.model.Classification
import com.evgeny.archBase.graphics.centerCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

internal class LandmarkImageAnalyzer @Inject constructor(
    private val landmarkClassifier: LandmarkClassifier,
    @DefaultCoroutineScope private val coroutineScope: CoroutineScope,
) : ImageAnalysis.Analyzer {

    private val _results = MutableSharedFlow<List<Classification>>()
    val results = _results.asSharedFlow()

    private val frameSkipCounter = AtomicLong(0)

    override fun analyze(image: ImageProxy) {
        coroutineScope.launch {
            if (frameSkipCounter.getAndIncrement() % 20 == 0L) {
                val results = landmarkClassifier.classify(
                    bitmap = image.toBitmap().centerCrop(321, 321),
                    rotation = image.imageInfo.rotationDegrees,
                )
                _results.emit(results)
            }
            image.close()
        }
    }
}
