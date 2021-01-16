package com.task.noteapp.data.local

import com.task.noteapp.data.Result
import com.task.noteapp.data.model.Note

interface INoteRepository {

    suspend fun getNotes(): Result<MutableList<Note>>

    suspend fun getNoteById(noteId: String): Result<Note>

    suspend fun saveNote(note: Note)

    suspend fun updateNote(note: Note)

}