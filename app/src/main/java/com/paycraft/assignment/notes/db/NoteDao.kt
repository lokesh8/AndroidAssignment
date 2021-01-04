package com.paycraft.assignment.notes.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)

    @Query("delete from table_note where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun delete(note: Note)

    @Query("select * from table_note order by isPin DESC")
    fun getAllNotes():LiveData<List<Note>>
}