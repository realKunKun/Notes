package com.example.notes.logic.ViewModel

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.notes.logic.NoteRepository
import com.example.notes.logic.model.NoteModel

class DisplayViewModel(activity: Activity):ViewModel() {
    private val activity=activity
    private val repository:NoteRepository by lazy { NoteRepository() }
    private lateinit var note:NoteModel
    private var haveSet=false
    init {
       if (haveSet){

       }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun delete(noteId:String):String{
       return repository.deleteNote(noteId, activity = activity)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun edit(name:String, id:String):String{
        return repository.editNote(name,id,activity)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun get(id: Int){
        repository.getNote(id.toString(),activity)
    }
    fun setNote(note:NoteModel){
        this.note=note
    }
    fun getNote():NoteModel{
        return this.note
    }
}