package com.example.notes.logic.model

import java.io.Serializable
import java.sql.Time

data class NoteModel (//Item Information
    val id:String,
    val name:String,
    var date:String,
    val num:Int,
    var state:Int,
    var image:String,
                      //TAG Data
    var tag:String,
    var tagId: Int,
    var info: String):Serializable
