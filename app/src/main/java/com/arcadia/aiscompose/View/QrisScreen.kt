package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import android.graphics.Bitmap
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.BarcodeFormat
import android.graphics.Color

@Composable
fun QrisScreen() {
    val qrisContent = remember {
        generateQrisStaticContent(
            merchantId = "936009143652545411",
            amount = 100000.0
        )
    }
    val qrBitmap = remember(qrisContent) {
        generateQrBitmap(qrisContent)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("QRIS Static (100,000 IDR)", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            bitmap = qrBitmap.asImageBitmap(),
            contentDescription = "QRIS QR Code",
            modifier = Modifier.size(250.dp)
        )
        //Spacer(modifier = Modifier.height(16.dp))
        //Text(qrisContent.take(100) + "...") // optional debug
    }
}

fun generateQrisStaticContent(
    merchantId: String,
    merchantName: String = "IWAN TJIOE, Edukasi",
    merchantCity: String = "JAKARTA UTARA",
    amount: Double = 0.0
): String {
    fun formatField(id: String, value: String): String {
        return id + value.length.toString().padStart(2, '0') + value
    }

    val payloadFormatIndicator = formatField("00", "01")
    val pointOfInitiationMethod = formatField("01", "11") // 11 = static
    val merchantAccountInfo = formatField("26",
        formatField("00", "COM.GO-JEK.WWW") +
                formatField("01", merchantId)+
                formatField("02","G652545411")+
                formatField("02","UMI")
    )
    //val additionalDataField = formatField("51", "0014ID.CO.QRIS.WWW0215ID10254112154820303UMI")
    val merchantCategoryCode = formatField("52", "8299")
    val transactionCurrency = formatField("53", "360")
    val transactionAmount = if (amount > 0) formatField("54", amount.toString()) else ""
    val countryCode = formatField("58", "ID")
    val merchantNameField = formatField("59", merchantName.take(25))
    val merchantCityField = formatField("60", merchantCity.take(15))
    val merchantPostal = formatField("61", "14110")
    val merchantAdditional  = formatField("62", "5028A2202507220803218PpkIy3Pp8ID0703A01")

    val rawData = payloadFormatIndicator +
            pointOfInitiationMethod +
            merchantAccountInfo +
      //      additionalDataField +
            merchantCategoryCode +
            transactionCurrency +
            transactionAmount +
            countryCode +
            merchantNameField +
            merchantCityField +
            merchantPostal+
            merchantAdditional+
            "6304" // Checksum placeholder

    val crc = calculateCRC16(rawData)
    return rawData + crc
}

fun calculateCRC16(input: String): String {
    var crc = 0xFFFF
    input.toByteArray().forEach { b ->
        crc = crc xor (b.toInt() shl 8)
        for (i in 0 until 8) {
            crc = if ((crc and 0x8000) != 0) (crc shl 1) xor 0x1021 else crc shl 1
        }
    }
    crc = crc and 0xFFFF
    return String.format("%04X", crc)
}

fun generateQrBitmap(content: String, size: Int = 512): Bitmap {
    val hints = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
    val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }
    return bitmap
}