package com.task.noteapp.data

import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.model.Note

class FakeRepository: INoteRepository {

    var notesDBData: LinkedHashMap<String, Note> = LinkedHashMap()

    override suspend fun getNotes(): Result<MutableList<Note>> {
        if (notesDBData.values.isNullOrEmpty()) {
            return Result.Error(ERROR_CODE, NO_NOTES)
        }
        return Result.Success(notesDBData.values.toMutableList())
    }

    override suspend fun getNoteById(noteId: String): Result<Note> {
        val note = notesDBData[noteId]
        note?.let {
            return Result.Success(notesDBData[noteId]!!)
        }
        return Result.Error(ERROR_CODE, NO_SUCH_NOTE)
    }

    override suspend fun saveNote(note: Note) {
        notesDBData[note.id] = note
    }

    override suspend fun updateNote(note: Note) {
        notesDBData[note.id] = note
    }

    fun saveNotes(notes: MutableList<Note>) {
        for (note in notes) {
            notesDBData[note.id] = note
        }
    }

    fun clearNotes() {
        notesDBData.clear()
    }

    companion object {
        const val NO_SUCH_NOTE = "No such note"
        const val NO_NOTES = "No notes"
        const val ERROR_CODE = "0"
    }
}