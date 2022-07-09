package com.example.notes.logic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "localNote")
data class NoteEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "noteName")
    val name:String,
    @ColumnInfo(name = "date")
    val date: Time,
    @ColumnInfo(name = "id")
    val tag:String,
    @ColumnInfo(name = "id")
    val num:Int,
    @ColumnInfo(name = "id")
    var state:Int,
    @ColumnInfo(name = "id")
    var isChoose:Boolean,
    @ColumnInfo(name = "id")
    var image:Int
    )