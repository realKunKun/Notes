package com.example.notes.logic

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.logic.model.GlobalValue
import com.example.notes.logic.model.UserModel
import okhttp3.*
import org.json.JSONObject
import java.lang.Exception
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import kotlin.concurrent.thread

class LogInRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun autoLogIn(activity: Activity){
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val requestBody= FormBody.Builder().build()
                    val client = OkHttpClient()
                    val request = Request.Builder().url("http://121.37.86.25:8000/")
                        .header("cookieforuser",cookieforuser.toString())
                        .header("userdata",userdata.toString())
                        .post(requestBody)
                        .build()
                    val response = client.newCall(request).execute()
                    val responseData = response.body()?.string()
                    if (responseData != null) {
                        Log.d("服务器回应",responseData.toString())
                        val json= JSONObject(responseData)
                        if(json.get("state")=="FAIL"){
                            //写下错误的处理
                        }
                        else{
                            GlobalValue.accessState=true
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }
            }
        }catch (e: Exception){
            Log.d("登录问题",e.toString())
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun register(password:String, firstname:String,activity:Activity): String {
        var data=""
        val publicKeyStr=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
                    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
                    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
                    "kTe6w/y6ii8uMQZrGwIDAQAB"
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)))
        val transformation = "RSA/ECB/PKCS1P" +
                "adding"
        val cipherPassword = Cipher.getInstance(transformation)
        cipherPassword.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptPassword = cipherPassword.doFinal(password.toByteArray())
        val passwordRsa = Base64.getEncoder().encode(encryptPassword).decodeToString()
        Log.d("====================", Base64.getEncoder().encode(encryptPassword).decodeToString())
        val cipherFirstname = Cipher.getInstance(transformation)
        cipherFirstname.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptFirstname = cipherFirstname.doFinal(firstname.toByteArray())
        val firstnameRsa = Base64.getEncoder().encode(encryptFirstname).decodeToString()
        Log.d(
            "====================",
            Base64.getEncoder().encode(encryptFirstname).decodeToString()
        )



        try {
            thread {
                try {
                    val client = OkHttpClient()
                    var requestBody: RequestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("password", passwordRsa)
                        .addFormDataPart("firstname", firstnameRsa)
                        .build()
                    val request = Request.Builder().url("http://121.37.86.25:8000/register").post(requestBody).build();
                    val response = client.newCall(request).execute()
                    val responseData = response.body()?.string()
                    if (responseData != null) {
                        Log.d("服务器回应",responseData.toString())
                        if (responseData != null) {
                            Log.d("服务器回应",responseData.toString())
                            val json= JSONObject(responseData)
                            if(json.get("state")=="FAIL"){
                                //写下错误的处理
                            }
                            else{
                                val prefs = activity.getPreferences(Context.MODE_PRIVATE)
                                var cookieforuserToSave=json.get("cookieforuser").toString()
                                var userdataToSave=json.get("userdata").toString()
                                val editor = activity.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                                editor.putString("cookieforuser", cookieforuserToSave)
                                editor.putString("userdata", userdataToSave)
                                editor.apply()
                                GlobalValue.accessState=true
                            }
                        }

                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e: Exception){
            Log.d("登录问题",e.toString())
        }
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun logIn(password:String, firstname:String,activity:Activity): String {
        var data=""
        val publicKeyStr=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
                    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
                    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
                    "kTe6w/y6ii8uMQZrGwIDAQAB"
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)))
        val transformation = "RSA/ECB/PKCS1P" +
                "adding"
        val cipherPassword = Cipher.getInstance(transformation)
        cipherPassword.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptPassword = cipherPassword.doFinal(password.toByteArray())
        val passwordRsa = Base64.getEncoder().encode(encryptPassword).decodeToString()
        Log.d("====================", Base64.getEncoder().encode(encryptPassword).decodeToString())
        val cipherFirstname = Cipher.getInstance(transformation)
        cipherFirstname.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptFirstname = cipherFirstname.doFinal(firstname.toByteArray())
        val firstnameRsa = Base64.getEncoder().encode(encryptFirstname).decodeToString()
        Log.d(
            "====================",
            Base64.getEncoder().encode(encryptFirstname).decodeToString()
        )



    try {
            thread {
                try {
                    val client = OkHttpClient()
                    var requestBody: RequestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("password", passwordRsa)
                        .addFormDataPart("firstname", firstnameRsa)
                        .build()
                    val request = Request.Builder().url("http://121.37.86.25:8000/").post(requestBody).build();
                    val response = client.newCall(request).execute()
                    val responseData = response.body()?.string()
                    if (responseData != null) {
                        Log.d("服务器回应",responseData.toString())
                        if (responseData != null) {
                            Log.d("服务器回应",responseData.toString())
                            val json= JSONObject(responseData)
                            if(json.get("state")=="FAIL"){
                                //写下错误的处理
                            }
                            else{
                                val prefs = activity.getPreferences(Context.MODE_PRIVATE)
                                var cookieforuserToSave=json.get("cookieforuser").toString()
                                var userdataToSave=json.get("userdata").toString()
                                val editor = activity.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                                editor.putString("cookieforuser", cookieforuserToSave)
                                editor.putString("userdata", userdataToSave)
                                editor.apply()
                                GlobalValue.accessState=true
                            }
                        }

                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e: Exception){
            Log.d("登录问题",e.toString())
        }
        return data
    }

}