package com.task.noteapp.data

import com.task.noteapp.data.local.NoteDataSource
import com.task.noteapp.data.local.NoteDatabase
import com.task.noteapp.data.model.Note
import com.task.noteapp.utils.Constants.GENERIC_ERROR_CODE
import com.task.noteapp.utils.Constants.GENERIC_ERROR_MESSAGE
import com.task.noteapp.utils.Constants.NOTE_NOT_FOUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(
    noteDatabase: NoteDatabase
) : NoteDataSource {

    private val noteDao = noteDatabase.noteDao()
    private val ioDispatcher = Dispatchers.IO

    override suspend fun getNotes(): Result<MutableList<Note>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(noteDao.getAllNotes())
            } catch (e: Exception) {
                Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }

    override suspend fun getNoteById(noteId: String): Result<Note> =
        withContext(ioDispatcher) {
            try {

                val note = noteDao.getNoteById(noteId)
                note?.let {
                    return@withContext Result.Success(it)
                }
                return@withContext Result.Error(GENERIC_ERROR_CODE, NOTE_NOT_FOUND)
            } catch (e: Exception) {
                Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }

    override suspend fun saveNote(note: Note) =
        withContext(ioDispatcher) { noteDao.insertNote(note)}

    override suspend fun updateNote(note: Note) =
        withContext(ioDispatcher) { noteDao.updateNote(note)}

}