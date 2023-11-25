package com.evgeny.aiLandmarkRecognition.domain

import android.graphics.Bitmap
import com.evgeny.aiLandmarkRecognition.domain.model.Classification

internal interface LandmarkClassifier {

    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}
