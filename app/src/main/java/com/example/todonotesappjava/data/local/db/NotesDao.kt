package com.example.todonotesappjava.data.local.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.todonotesappjava.data.local.db.Notes

//data access objects - DAO
@Dao
interface NotesDao{

    @Query("SELECT * from notesData")
    fun getAll():List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status:Boolean)
}