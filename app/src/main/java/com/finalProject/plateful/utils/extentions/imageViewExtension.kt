package com.finalProject.plateful.utils.extentions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

val ImageView.bitmap: Bitmap?
    get() = (this.drawable as? BitmapDrawable)?.bitmap

fun ImageView.loadAvatar(url: Uri) {
    this.imageTintList = null
    this.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
    this.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
    this.scaleType = ImageView.ScaleType.CENTER_CROP

    Picasso.get().load(url).into(this)
}