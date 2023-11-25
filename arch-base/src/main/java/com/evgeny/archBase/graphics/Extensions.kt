package com.evgeny.archBase.graphics

import android.graphics.Bitmap

fun Bitmap.centerCrop(desiredWidth: Int, desiredHeight: Int): Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2

    return Bitmap.createBitmap(this, xStart, yStart, desiredHeight, desiredHeight)
}
