package com.finalProject.plateful.data.models

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.cloudinary.android.policy.UploadPolicy
import com.finalProject.plateful.base.MyApplication
import com.finalProject.plateful.base.StringCompletion
import java.io.File
import kotlin.collections.get

class CloudinaryStorageModel {

    init {
        val config = mapOf(
            "cloud_name" to "dltg3tc47",
            "api_key" to "574698951814332",
            "api_secret" to "hr453YhzcWkfUOIkPsh6jgr9Ey4"
        )

        MyApplication.appContext?.let {
            MediaManager.init(it, config)
            MediaManager.get().globalUploadPolicy = GlobalUploadPolicy.Builder()
                .maxConcurrentRequests(3)
                .networkPolicy(UploadPolicy.NetworkType.UNMETERED)
                .build()
        }
    }

    fun uploadRecipeImage(image: Bitmap, recipeId: String, completion: StringCompletion) {
        val context = MyApplication.appContext ?: return

        val file = bitmapToFile(image, context)

        MediaManager.get().upload(file.path)
            .option("images", "recipes/${recipeId}/recipe_image")
            .callback ( object: UploadCallback {
                override fun onStart(requestId: String) {
                    // Upload started
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                    // Upload progress
                }

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val imageUrl = resultData["secure_url"] as? String
                    Log.v("TAG", "Cloudinary upload success: $imageUrl")
                    completion(imageUrl)
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    Log.v("TAG", "Cloudinary upload error: ${error.description}")
                    completion(null)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    // Upload rescheduled
                }
            }).dispatch()
    }
    private fun bitmapToFile(image: Bitmap, context: Context): File {
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

        file.outputStream().use {
            image.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }

        return file
    }
}