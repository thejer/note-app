package com.task.noteapp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.Result
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val noteRepository: INoteRepository

): ViewModel() {

    private val _notes = MutableLiveData<MutableList<Note>>()
    val notes: LiveData<MutableList<Note>>
        get() = _notes

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _notesDeleted = MutableLiveData<Int>()
    val notesDeleted: LiveData<Int>
        get() = _notesDeleted

    fun getNotes() {
        viewModelScope.launch {
            when (val result = noteRepository.getNotes()) {
                is Result.Success -> {
                    _notes.value = result.data
                }
                is Result.Error -> {
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }

    fun deleteNotes(noteIds: MutableSet<String>) {
        viewModelScope.launch {
            when (val result = noteRepository.bulkDeleteNotes(noteIds)) {
                is Result.Success -> {
                    _notesDeleted.value = result.data
                }
                is Result.Error -> {
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }
}