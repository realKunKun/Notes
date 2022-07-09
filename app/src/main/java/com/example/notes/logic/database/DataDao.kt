package com.example.notes.logic.database

import androidx.room.*

@Dao
interface DataDao {
    @Query("SELECT noteName from localNote where noteName=(:name)")
    fun getNovel(name:String):String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNovel(novelEntity: NoteEntity)

    @Delete
    fun deleteNovel(novelEntity: NoteEntity)

    @Update
    fun updateNovel(novelEntity: NoteEntity)
}