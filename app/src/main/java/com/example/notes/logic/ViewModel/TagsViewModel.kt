package com.example.notes.logic.ViewModel

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.logic.NetRepository
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.TagModel
import kotlin.concurrent.thread


@RequiresApi(Build.VERSION_CODES.O)
class TagsViewModel(activity: Activity):ViewModel() {
    private val activity=activity
    var liveTagData= MutableLiveData<MutableList<TagModel>>()
    var tag:MutableList<TagModel>
    var noteList:MutableList<NoteModel>
    private val repository : NetRepository by lazy { NetRepository() }
    init {
        tag= mutableListOf()
        noteList= mutableListOf()
        repository.getHttpData(activity)
        Thread.sleep(500)
        tag= repository.getClassLiveData()
        setData()
        liveTagData.value=repository.getClassLiveData()
    }
    fun delete(id:String):String{
        return repository.deleteTag(id, activity)
    }
    fun edit(tag:String,id:String):String{
        return repository.editTag(tag,id,activity)
    }
    fun newTag(name:String){
        repository.AddTag(name,activity)
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
    fun refresh(){
        repository.getHttpData(activity)
        Thread.sleep(500)
        tag= repository.getClassLiveData()
        setData()
        liveTagData.value=repository.getClassLiveData()
    }
}