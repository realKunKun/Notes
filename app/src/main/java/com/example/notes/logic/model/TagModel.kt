package com.example.notes.logic.model

import java.io.Serializable

data class TagModel (val id:Int, val tag:String, val list:MutableList<NoteModel>):Serializable