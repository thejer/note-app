package com.task.noteapp.data.local

import androidx.room.*
import com.task.noteapp.data.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long?

    @Update
    suspend fun updateNote(note: Note): Int?

    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNote(noteId: String): Int?

    @Query("DELETE FROM note WHERE id IN (:noteIds)")
    suspend fun deleteNotes(noteIds: MutableSet<String>): Int?
}