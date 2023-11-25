package com.evgeny.aiLandmarkRecognition.data

import android.graphics.Bitmap
import android.view.Surface
import com.evgeny.aiLandmarkRecognition.TfLiteLandmarksClassifier
import com.evgeny.aiLandmarkRecognition.domain.LandmarkClassifier
import com.evgeny.aiLandmarkRecognition.domain.model.Classification
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import javax.inject.Inject

internal class TfLiteLandmarkClassifier @Inject constructor(
    @TfLiteLandmarksClassifier private val classifier: ImageClassifier,
    private val imageProcessor: ImageProcessor,
) : LandmarkClassifier {

    override fun classify(bitmap: Bitmap, rotation: Int): List<Classification> {
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))
        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(rotation))
            .build()
        val results = classifier.classify(tensorImage, imageProcessingOptions)

        return results.asSequence()
            .map {
                it.categories.map {
                    Classification(
                        name = it.displayName,
                        score = it.score,
                    )
                }
            }
            .flatten()
            .distinctBy { it.name }
            .toList()
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }
}
