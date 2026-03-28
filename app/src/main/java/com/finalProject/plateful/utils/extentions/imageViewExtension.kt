package com.finalProject.plateful.utils.extentions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView

val ImageView.bitmap: Bitmap?
    get() = (this.drawable as? BitmapDrawable)?.bitmap