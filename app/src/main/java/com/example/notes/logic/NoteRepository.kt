package com.example.notes.logic

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.TagModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import kotlin.concurrent.thread

class NoteRepository {
    private var noteData : MutableList<NoteModel>
    private var classData :MutableList<TagModel>
    init {
        noteData= mutableListOf()
        classData= mutableListOf()
    }
    fun AddNote(name:String,date:String,num:Int,state:Int,image:String,tag_name:String,tag_id:Int=0,info:String,activity: Activity):String
    {  var data= ""
        thread {
            try {
                val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                val cookieforuser = prefs.getString("cookieforuser", "___")
                val userdata = prefs.getString("userdata", "___")
                val client = OkHttpClient()
                val requestBody= FormBody.Builder()
                    .add("name",name)
                    .add("date",date)
                    .add("num",num.toString())
                    .add("state",state.toString())
                    .add("image",image)
                    .add("tag",tag_name)
                    .add("tag_id",tag_id.toString())
                    .add("info",info)
                    .build()
                val request =
                    Request.Builder().url("http://121.37.86.25:8000/note")
                        .header("userdata", userdata.toString())
                        .header("cookieforuser",cookieforuser.toString())
                        .post(requestBody)
                        .build()

                val response = client.newCall(request).execute()
                val responseData = response.body()?.string()

                if (responseData!= null) {
                    val jsonObject = JSONObject(responseData)
                    val json= JSONObject(responseData)
                    Log.d("服务器回应",responseData.toString())
                    if(jsonObject.get("state")=="FAIL"){
                        //写下错误的处理
                    }
                    else{
                        //正确的处理
                        var state=json.get("state").toString()
                        data=state
                    }
                }
            }
            catch (e: Exception) {
                Log.d("服务器错误",e.toString())
            }
        }
        return data
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteNote(Id:String,activity: Activity): String {
        var data= ""
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    var requestBody: RequestBody = FormBody.Builder()
                        .add("id", Id)
                        .build()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/note?id=$Id")
                            .header("userdata", userdata.toString())
                            .header("cookieforuser",cookieforuser.toString())
                            .delete()
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
                            var id=json.get("id").toString()
                            var state=json.get("state").toString()
                            data=state
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e:Exception){
            Log.d("登录问题",e.toString())
        }
        return data
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun editNote(name:String, Id:String, activity: Activity): String {
        //名字
        var data= ""
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    var requestBody: RequestBody = FormBody.Builder()
                        .add("id", Id)
                        .add("data", name)
                        .build()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/note")
                            .header("userdata", userdata.toString())
                            .header("cookieforuser",cookieforuser.toString())
                            .put(requestBody)
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
                            var state=json.get("state").toString()
                            data=state
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e:Exception){
            Log.d("登录问题",e.toString())
        }
        return data
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getNote(id:String,activity:Activity):NoteModel {
        //假数据
        var data:NoteModel=NoteModel("1","1","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","fruit",1,"xxxx")
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()

                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/note?id=$id")
                            .header("userdata", userdata.toString())
                            .header("cookieforuser",cookieforuser.toString())
                            .get()
                            .build()

                    val response = client.newCall(request).execute()
                    val responseData = response.body()?.string()
                    if (responseData != null) {
                        Log.d("服务器回应",responseData.toString())
                        if(responseData.toString()=="FAIL"){
                            //写下错误的处理
                        }
                        else{
                            val response=parseJSONWithGSONInNode(responseData)
                            data=response
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e:Exception){
            Log.d("登录问题",e.toString())
        }
        return data
    }
    private fun parseJSONWithGSONInNode(jsonData: String): NoteModel {
        val gson = Gson()
        Log.d("MainActivity", "here")
        val typeOf = object : TypeToken<NoteModel>() {}.type
        return gson.fromJson(jsonData, typeOf)
    }

}
