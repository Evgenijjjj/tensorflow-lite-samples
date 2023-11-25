package com.evgeny.aiLandmarkRecognition

import android.content.Context
import androidx.camera.core.ImageAnalysis
import com.evgeny.aiLandmarkRecognition.data.TfLiteLandmarkClassifier
import com.evgeny.aiLandmarkRecognition.domain.LandmarkClassifier
import com.evgeny.aiLandmarkRecognition.presentation.LandmarkImageAnalyzer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AiLandmarkRecognitionModule {

    @Binds
    @Singleton
    fun bindLandmarkClassifier(impl: TfLiteLandmarkClassifier): LandmarkClassifier

    @Binds
    fun bindImageAnalyzer(impl: LandmarkImageAnalyzer): ImageAnalysis.Analyzer

    companion object {
        @Provides
        @Singleton
        @TfLiteLandmarksClassifier
        fun provideImageClassifier(
            @ApplicationContext context: Context,
        ): ImageClassifier {
            val baseOptions = BaseOptions.builder()
                .setNumThreads(2)
                .build()
            val options = ImageClassifierOptions.builder()
                .setBaseOptions(baseOptions)
                .setMaxResults(3)
                .setScoreThreshold(0.5f)
                .build()
            return ImageClassifier.createFromFileAndOptions(
                context,
                "landmarks.tflite",
                options,
            )
        }

        @Provides
        @Singleton
        fun provideImageProcessor(): ImageProcessor = ImageProcessor.Builder().build()

        @Provides
        @Singleton
        @DefaultCoroutineScope
        fun provideDefaultCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TfLiteLandmarksClassifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultCoroutineScope
