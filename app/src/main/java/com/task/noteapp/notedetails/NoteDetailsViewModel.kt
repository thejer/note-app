package com.task.noteapp.notedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.Result
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
    private val repository: INoteRepository
): ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _noteDeleted = MutableLiveData<Boolean>()
    val noteDeleted: LiveData<Boolean>
        get() = _noteDeleted

    fun getNoteById(noteId: String) {
        viewModelScope.launch {
            when (val result = repository.getNoteById(noteId)) {
                is Result.Success -> {
                    _note.value = result.data
                }

                is Result.Error -> {
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            when (val result = repository.deleteNote(noteId)) {
                is Result.Success -> {
                    _noteDeleted.value = true
                }
                is Result.Error -> {
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }
}
