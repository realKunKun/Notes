package com.example.notes.logic.database

import androidx.room.*

@Dao
interface DataDao {
    @Query("SELECT * from localNote")
    fun getNotes():MutableList<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(novelEntity: NoteEntity)
}