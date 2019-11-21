package dev.kirillzhelt.maymaymay.newday.smiledetection

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions


private val LOG_TAG = SmileImageAnalyzer::class.java.simpleName

class SmileImageAnalyzer: ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy?, rotationDegrees: Int) {
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
                Log.i(LOG_TAG, faces.toString())
            }.addOnFailureListener { e ->
                Log.e(LOG_TAG, e.toString())
            }

        }

    }
}
