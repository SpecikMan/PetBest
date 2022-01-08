package com.specikman.petbest.common

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class QRGenerator {
    companion object {
        fun generateQRBitmap(content: String): Bitmap =
            BarcodeEncoder().encodeBitmap(content, BarcodeFormat.QR_CODE, 2000, 2000)
    }
}