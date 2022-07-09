package com.example.notes.logic

import android.app.Activity
import android.content.Context
import com.example.notes.logic.model.NoteModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.notes.logic.ViewModel.TagsViewModel
import com.example.notes.logic.database.Database
import com.example.notes.logic.model.OnlyTagModel
import com.example.notes.logic.model.TagModel
import com.example.notes.logic.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
class NetRepository() {
    private var noteData : MutableList<NoteModel>
    private var classData :MutableList<TagModel>
    private var onlyTagData:MutableList<OnlyTagModel>
    private lateinit var listData:List<TagModel>
    init {
        onlyTagData= mutableListOf()
        noteData= mutableListOf()
        classData= mutableListOf()
    }
    fun getLiveData(): MutableList<NoteModel> {
        return noteData
    }
    fun getClassLiveData():MutableList<TagModel>{
        return classData
    }
    fun getList():List<TagModel>{
        return listData
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getHttpData(activity: Activity){
        getTagList(activity)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTagList(activity:Activity){
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/tag/note")
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
                            val response=parseJSONWithGSON(responseData)
                            listData=response
                            for (x in response){
                               classData.add(x)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e:Exception){
            Log.d("登录问题",e.toString())
        }

    }
    private fun parseJSONWithGSON(jsonData: String):List<TagModel> {
        val gson = Gson()
        Log.d("MainActivity", "here")
        val typeOf = object : TypeToken<List<TagModel>>() {}.type
        val appList = gson.fromJson<List<TagModel>>(jsonData, typeOf)
        for (app in appList) {
            Log.d("MainActivity", "id is ${app.id}")
            Log.d("MainActivity", "name is ${app.tag}")
        }
        return appList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTag(tagId:String,activity: Activity): String {
        var data= ""
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/tag?tag_id=$tagId")
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
    fun editTag(tag:String,tagId:String,activity: Activity): String {
        var data= ""
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    var requestBody: RequestBody = FormBody.Builder()
                        .build()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/tag?tag_id=$tagId&tag_name=$tag")
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
                            var id=json.get("id").toString()
                            var state=json.get("state").toString()
                            var jsonArray= JSONArray(json.get("list"))
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
    fun AddTag(name:String, activity: Activity):String
    {    var data=""
        thread {
            try {
                val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                val cookieforuser = prefs.getString("cookieforuser", "___")
                val userdata = prefs.getString("userdata", "___")
                val client = OkHttpClient()
                val requestBody= FormBody.Builder()
                    .add("tag_name",name)
                    .build()
                val request =
                    Request.Builder().url("http://121.37.86.25:8000/tag")
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
    fun getOnlyTagList(activity: Activity){
        try {
            thread {
                try {
                    val prefs = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
                    val cookieforuser = prefs.getString("cookieforuser", "___")
                    val userdata = prefs.getString("userdata", "___")
                    val client = OkHttpClient()
                    val request =
                        Request.Builder().url("http://121.37.86.25:8000/taglist")
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
                            val response=parseJSONWithGSON2(responseData)
                            for (x in response){
                                onlyTagData.add(x)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("服务器错误",e.toString())
                }

            }
        }catch (e:Exception){
            Log.d("登录问题",e.toString())
        }
    }
    private fun parseJSONWithGSON2(jsonData: String):List<OnlyTagModel> {
        val gson = Gson()
        val typeOf = object : TypeToken<List<OnlyTagModel>>() {}.type
        val appList = gson.fromJson<List<OnlyTagModel>>(jsonData, typeOf)
        for (app in appList) {
            Log.d("MainActivity", "id is ${app.id}")
            Log.d("MainActivity", "name is ${app.tag}")
        }
        return appList
    }
    //数据库
    fun local(activity: Activity,state:Int){
        val database= Room.databaseBuilder(activity, Database::class.java,"NovelDataBase").allowMainThreadQueries().build()
        if (state==1)
        //noteData = database.dataDao().getNotes()
        else{
        //database.dataDao().insertNotes()
        }
    }
}