package com.task.noteapp.data

import android.util.Log
import com.task.noteapp.data.Result.Error
import com.task.noteapp.data.Result.Success
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.local.NoteDatabase
import com.task.noteapp.data.model.Note
import com.task.noteapp.utils.Constants.ERROR_ADDING_NOTE
import com.task.noteapp.utils.Constants.ERROR_DELETING_NOTE
import com.task.noteapp.utils.Constants.ERROR_UPDATING_NOTE
import com.task.noteapp.utils.Constants.GENERIC_ERROR_CODE
import com.task.noteapp.utils.Constants.GENERIC_ERROR_MESSAGE
import com.task.noteapp.utils.Constants.NOTE_NOT_FOUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(
    noteDatabase: NoteDatabase
) : INoteRepository {

    private val noteDao = noteDatabase.noteDao
    private val ioDispatcher = Dispatchers.IO

    override suspend fun getNotes(): Result<MutableList<Note>> =
        withContext(ioDispatcher) {
            try {
                Success(noteDao.getAllNotes())
            } catch (e: Exception) {
                Log.e("getNotes", "getNotes: ", e)
                Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }

    override suspend fun getNoteById(noteId: String): Result<Note> =
        withContext(ioDispatcher) {
            try {
                val note = noteDao.getNoteById(noteId)
                note?.let {
                    Success(it)
                }
                Error(GENERIC_ERROR_CODE, NOTE_NOT_FOUND)
            } catch (e: Exception) {
                Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }

    override suspend fun saveNote(note: Note): Result<Long> =
        withContext(ioDispatcher) {
            try {
                val noteId = noteDao.insertNote(note)
                noteId?.let {
                    Success(it)
                }
                Error(GENERIC_ERROR_CODE, ERROR_ADDING_NOTE)
            } catch (e: Exception) {
                Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }

    override suspend fun updateNote(note: Note): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val updatedNotes = noteDao.updateNote(note)
                if (updatedNotes == null || updatedNotes == 0)
                    Error(
                        GENERIC_ERROR_CODE,
                        ERROR_UPDATING_NOTE
                    ) else
                    Success(updatedNotes)
            } catch (e: Exception) {
                Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }

        }

    override suspend fun deleteNote(noteId: String): Result<Int> =
        withContext(ioDispatcher) {
            try {
                val deletedNotes = noteDao.deleteNote(noteId)
                if (deletedNotes == null || deletedNotes == 0)
                    Error(
                        GENERIC_ERROR_CODE,
                        ERROR_DELETING_NOTE
                    ) else
                    Success(deletedNotes)
            } catch (e: Exception) {
                Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }

        }

}