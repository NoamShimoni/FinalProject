package com.finalProject.plateful.utils.extentions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

val ImageView.bitmap: Bitmap?
    get() = (this.drawable as? BitmapDrawable)?.bitmap

fun customizeAvatarImageView(imageView: ImageView) {
    imageView.imageTintList = null
    imageView.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
    imageView.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
}

fun ImageView.loadAvatar(url: Uri) {
    customizeAvatarImageView(this)

    Picasso.get().load(url).into(this)
}

fun ImageView.setAvatarImageBitmap(bitmap: Bitmap) {
    customizeAvatarImageView(this)
    this.setImageBitmap(bitmap)
}