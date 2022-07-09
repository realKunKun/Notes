package com.example.notes.logic.ViewModel

import android.app.Activity
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.notes.logic.NetRepository
import com.example.notes.logic.model.GlobalValue
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.TagModel
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(activity: Activity):ViewModel() {
    private val repository : NetRepository by lazy { NetRepository() }
    var noteList:MutableList<NoteModel>
    var liveTagData= MutableLiveData<MutableList<TagModel>>()
    var tag:MutableList<TagModel>
    init {
        tag= mutableListOf()
        noteList= mutableListOf()
        if (GlobalValue.accessState){
            repository.getHttpData(activity)
            Thread.sleep(500)
            tag= repository.getClassLiveData()
            setData()
            liveTagData.value=tag
        }
        repository.local(activity = activity,0)
    }
    fun setData(){
        thread {
            for( i in 0 until tag.size){
                val Tag = tag[i]
                val aTypeList:List<NoteModel> = Tag.list
                for(j in 0 until aTypeList.size){
                    val note = aTypeList[j]
                    //建立双向绑定关系
                    note.tag = Tag.tag
                    note.tagId = Tag.id
                }
                noteList.addAll(aTypeList)
            }
        }
    }
    fun logOut():Int{

        return 0
    }

}