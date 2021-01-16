package com.task.noteapp.data

import com.task.noteapp.data.model.Note

interface NoteDataSource {

    suspend fun getNotes(): Result<MutableList<Note>>

    suspend fun getNoteById(noteId: String): Result<Note>

    suspend fun saveNote(note: Note)

    suspend fun updateNote(note: Note)

}