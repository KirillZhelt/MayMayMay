package dev.kirillzhelt.maymaymay.newday.smiledetection


import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.content.ContextCompat

import dev.kirillzhelt.maymaymay.R
import kotlinx.coroutines.newSingleThreadContext
import java.util.concurrent.Executors

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

/**
 * A simple [Fragment] subclass.
 */
class SmileDetectionFragment : Fragment() {

    private lateinit var viewFinder: TextureView

    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_smile_detection, container, false)

        viewFinder = inflatedView.findViewById(R.id.fragment_smile_detection_preview_txtr_v)

        resultTextView = inflatedView.findViewById(R.id.fragment_smile_detection_result_tv)

        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        return inflatedView
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    private fun startCamera() {
        val aspectRatio = Rational(640, 640)
        val resolution = Size(640, 640)

        val previewConfig = PreviewConfig.Builder()
            .setTargetAspectRatio(aspectRatio)
            .setTargetResolution(resolution)
            .setLensFacing(CameraX.LensFacing.FRONT)
            .build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
        }

        val analyzerConfig = ImageAnalysisConfig.Builder()
            .setTargetAspectRatio(aspectRatio)
            .setTargetResolution(resolution)
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .setLensFacing(CameraX.LensFacing.FRONT)
            .build()

        val analyzer = ImageAnalysis(analyzerConfig).apply {
            analyzer = SmileImageAnalyzer()
        }

        CameraX.bindToLifecycle(this, preview, analyzer)
    }

    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all { ContextCompat.checkSelfPermission(requireContext(), it) ==
                PackageManager.PERMISSION_GRANTED }
    }
}
