package dev.kirillzhelt.maymaymay.newday.smiledetection

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


private val LOG_TAG = SmileImageAnalyzer::class.java.simpleName

class SmileImageAnalyzer(private val onSmileDetected: (Int) -> Unit): ImageAnalysis.Analyzer {

    private var lastAnalyzingTime: Long = System.currentTimeMillis()

    override fun analyze(imageProxy: ImageProxy?, rotationDegrees: Int) {
        val now = System.currentTimeMillis()

        if (now - lastAnalyzingTime >= TimeUnit.SECONDS.toMillis(1) / 2) {
            lastAnalyzingTime = now

            val mediaImage = imageProxy?.image

            mediaImage?.let {
                val image = FirebaseVisionImage.fromMediaImage(
                    mediaImage,
                    FirebaseVisionImageMetadata.ROTATION_270
                )

                val options = FirebaseVisionFaceDetectorOptions.Builder()
                    .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                    .build()

                val detector = FirebaseVision.getInstance().getVisionFaceDetector(options)

                detector.detectInImage(image).addOnSuccessListener { faces ->
                    faces.maxBy { face -> face.smilingProbability }?.let {
                        onSmileDetected((it.smilingProbability * 10).roundToInt())
                    }

                    Log.i(LOG_TAG, faces.toString())
                }.addOnFailureListener { e ->
                    Log.e(LOG_TAG, e.toString())
                }

            }
        }
    }
}
