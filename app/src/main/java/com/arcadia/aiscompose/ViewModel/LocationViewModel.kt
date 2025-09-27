package com.arcadia.aiscompose.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationViewModel(app: Application) : AndroidViewModel(app) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)

    @SuppressLint("MissingPermission")
    fun getLocationAndSend(token: String ) {
        Log.d("LocationDebug", "Location getLocationAndSend: ${token} data")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("LocationDebug", "Location received: ${location.latitude}, ${location.longitude}")
                    sendToServer(token, location.latitude, location.longitude)
                } else {
                    Log.e("LocationDebug", "Location is NULL")
                }
            }
            .addOnFailureListener { e ->
                Log.e("LocationDebug", "Failed to get location", e)
            }
    }

    private fun sendToServer(token:String,lat: Double, lon: Double) {
        Log.d("LocationDebug", "Location: ${token} data")
        viewModelScope.launch (Dispatchers.IO)  {
            val client = OkHttpClient()

//            val url = "https://tosft.my.id/locations?lat=$lat&lon=$lon"
//
//            val request = Request.Builder().url(url).build()

            // JSON body
            val json = """
            {
              "lat": $lat,
              "lon": $lon
            }
        """.trimIndent()

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = json.toRequestBody(mediaType)

            // Build POST request
            val request = Request.Builder()
                .url("https://tosft.my.id/locations") // pakai endpoint POST
                .post(body)
                .addHeader("Authorization", "Bearer $token") // kirim token
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        println("Server error: ${response.code}")
                    } else {
                        println("Server response: ${response.body?.string()}")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
