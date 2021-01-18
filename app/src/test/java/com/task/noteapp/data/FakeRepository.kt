package com.task.noteapp.data

import com.task.noteapp.data.Result.Error
import com.task.noteapp.data.Result.Success
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.model.Note

class FakeRepository: INoteRepository {

    var notesDBData: LinkedHashMap<String, Note> = LinkedHashMap()

    override suspend fun getNotes(): Result<MutableList<Note>> {
        if (notesDBData.values.isNullOrEmpty()) {
            return Error(ERROR_CODE, NO_NOTES)
        }
        return Success(notesDBData.values.toMutableList())
    }

    override suspend fun getNoteById(noteId: String): Result<Note> {
        val note = notesDBData[noteId]
        note?.let {
            return Success(it)
        }
        return Error(ERROR_CODE, NO_SUCH_NOTE)
    }

    override suspend fun saveNote(note: Note): Result<Long> {
        notesDBData[note.id] = note
        return if (note.title == "failed")
            Error(ERROR_CODE, NOTE_ADDING_FAILED)
        else
            Success(1L)
    }

    override suspend fun updateNote(note: Note): Result<Int> {
        notesDBData[note.id] = note
        val updatedNote = notesDBData[note.id]
        updatedNote?.let {
            if (it.isEdited){
                return Success(1)
            }
        }
        return Error(ERROR_CODE, NOTE_UPDATE_FAILED)
    }

    override suspend fun deleteNote(noteId: String): Result<Int> {
        if (noteId != "noteId2")
            notesDBData.remove(noteId)
        val deletedNote = notesDBData[noteId]
        deletedNote?.let {
            return Error(ERROR_CODE, NOTE_DELETION_FAILED)
        }
        return Success(1)
    }

    override suspend fun bulkDeleteNotes(noteIds: MutableSet<String>): Result<Int> {
        val keys = notesDBData.keys
        if (!noteIds.contains( "noteId2")) keys.removeAll(noteIds)
        if (keys.contains(noteIds.elementAt(0)))
            return Error(ERROR_CODE, NOTE_DELETION_FAILED)
        return Success(noteIds.size)
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
        const val NOTE_UPDATE_FAILED = "Note update failed"
        const val NOTE_ADDING_FAILED = "Note adding failed"
        const val NOTE_DELETION_FAILED = "Note deletion failed"
        const val NO_NOTES = "No notes"
        const val ERROR_CODE = "0"
    }
}