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
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class CloudinaryStorageModel private constructor() {
    private val executor = Executors.newSingleThreadExecutor()

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

    companion object {
        val shared = CloudinaryStorageModel()
    }

    private fun uploadImage(
        image: Bitmap,
        name: String,
        url: String,
        completion: StringCompletion
    ) {
        val context = MyApplication.appContext ?: return

        val file = bitmapToFile(image, context)

        MediaManager.get().upload(file.path)
            .option(name, url)
            .callback(object : UploadCallback {
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

    fun uploadRecipeImage(image: Bitmap, recipeId: String, completion: StringCompletion) {
        return this.uploadImage(image, "images", "recipes/${recipeId}/recipe_image", completion)
    }

    fun uploadProfileImage(image: Bitmap, userId: String, completion: StringCompletion) {
        return this.uploadImage(image, "public_id", "users/${userId}/profile_image", completion)
    }

    fun deleteRecipeImage(imageUrl: String, completion: (Boolean) -> Unit) {
        val publicId = extractPublicId(imageUrl)

        if (publicId == null) {
            Log.e("TAG", "Failed to extract Public ID from URL")
            completion(false)
            return
        }

        try {
            val response =
                MediaManager.get().cloudinary.uploader().destroy(publicId, emptyMap<Any, Any>())

            val result = response["result"] as? String
            if (result == "ok") {
                Log.v("TAG", "Cloudinary delete success: $publicId")
                completion(true)
            } else {
                Log.e("TAG", "Cloudinary delete failed: $result")
                completion(false)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Cloudinary delete error: ${e.message}")
            completion(false)
        }
    }

    private fun extractPublicId(url: String): String? {
        val parts = url.split("/")
        val uploadIndex = parts.indexOf("upload")
        if (uploadIndex == -1) return null

        val afterUpload = parts.subList(uploadIndex + 1, parts.size)

        val startIndex = if (afterUpload[0].startsWith("v") && afterUpload[0].substring(1)
                .all { it.isDigit() }
        ) {
            1
        } else {
            0
        }

        val publicIdWithExtension =
            afterUpload.subList(startIndex, afterUpload.size).joinToString("/")

        return publicIdWithExtension.substringBeforeLast(".")
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