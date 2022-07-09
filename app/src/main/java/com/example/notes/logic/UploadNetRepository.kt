package com.example.notes.logic

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.core.app.ActivityCompat
import okhttp3.*

import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.concurrent.thread

class UploadNetRepository {
    @SuppressLint("SdCardPath")
    fun upLoad(actvity: Activity){
        thread {
            ActivityCompat.requestPermissions(
                actvity, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE
                ), 1
            )
            var okHttpClient = OkHttpClient()
            var file = File("/sdcard/Android/data/com.example.notes/cache")
            val MEDIA_TYPE_PNG = MediaType.parse("image/png")
            var requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("file", UUID.randomUUID().toString()+".png",
                    RequestBody.create(MEDIA_TYPE_PNG, file))
                .build()
            val request =
                Request.Builder().url("http://121.37.86.25:8000/file").post(requestBody).build();
            val response = okHttpClient.newCall(request).execute()
            val responseData = response.body()?.string()
            if (responseData != null) {
                Log.d("服务器回应",responseData.toString())
            }
        }
    }

}