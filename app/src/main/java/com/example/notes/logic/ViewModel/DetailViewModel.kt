package com.example.notes.logic.ViewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.logic.NetRepository
import com.example.notes.logic.NoteRepository
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.TagModel
import com.example.notes.logic.model.UserModel
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DetailViewModel(activity: Activity): ViewModel() {
    private val repository : NetRepository by lazy { NetRepository()}
    private val newNote:NoteRepository by lazy { NoteRepository() }
    private var name:String=""
    private var date:String=""
    private var num:Int=0
    private var state:Int=0
    private var image:String=""
    //TAG Data
    private var info:String=""
    private var data= MutableLiveData<NoteModel>()
    lateinit var tag:TagModel
    val activity=activity

    fun setDate(endTime: String, startTime:String){
     //date= dateDiff(endTime,startTime).toString()
     //state=dateDiff(endTime,startTime).toInt()
      state=countDate(startTime,endTime).toInt()
      date=countDate(startTime,endTime)
    }
    fun setNum(num:Int){
        this.num=num
    }
    fun setInfo(info:String){
        this.info=info
    }

    fun setName(name:String){
        this.name=name
    }
    fun SetTag(tag:TagModel){
        this.tag=tag
    }
    fun setUrl(str:String){
        this.image=str
    }
    @SuppressLint("SdCardPath")
    fun upload():String{
        return newNote.AddNote(name,date,num,state,image,tag.tag,tag.id,info,activity)
    }
    @SuppressLint("SimpleDateFormat")
    fun setDate(str: String?): Date? {
        try {
            val formatter: SimpleDateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            val date = formatter.parse(str)
            return date
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @SuppressLint("SimpleDateFormat")
    fun dateDiff(endTime: String, startTime:String): Long {
        var strTime: Long = 0
        // 按照传入的格式生成一个simpledateformate对象
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val nd = (1000 * 24 * 60 * 60).toLong() // 一天的毫秒数
        val nh = (1000 * 60 * 60).toLong() // 一小时的毫秒数
        val nm = (1000 * 60).toLong() // 一分钟的毫秒数
        val ns: Long = 1000 // 一秒钟的毫秒数
        val diff: Long
        var day: Long = 0
        //val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val curDate = startTime
        val nowtime = simpleDateFormat.format(curDate)
        try {
            // 获得两个时间的毫秒时间差异
            diff = simpleDateFormat.parse(nowtime).time - simpleDateFormat.parse(endTime).time
            day = diff / nd // 计算差多少天
            val hour = diff % nd / nh // 计算差多少小时
            val min = diff % nd % nh / nm // 计算差多少分钟
            val sec = diff % nd % nh % nm / ns // 计算差多少秒
            // 输出结果
            strTime = min
            return strTime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return strTime
    }
    @SuppressLint("SimpleDateFormat")
    fun countDate(start: String, end: String):String{
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")

        val startTime: Date = dateFormat.parse(start)
        val endTime: Date = dateFormat.parse(end)

        val diff = endTime.time - startTime.time
        var days = diff / (1000 * 60 * 60 * 24)
        val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        val second = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000
        return minutes.toString()
    }
}