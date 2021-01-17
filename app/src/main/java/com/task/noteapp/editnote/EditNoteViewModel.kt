package com.task.noteapp.editnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.Result
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditNoteViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): ViewModel() {

    private val _noteAdded = MutableLiveData<Boolean>()
    val noteAdded: LiveData<Boolean>
        get() = _noteAdded

    private val _noteUpdated = MutableLiveData<Boolean>()
    val noteEdited: LiveData<Boolean>
        get() = _noteUpdated

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun saveNote(note: Note){
        viewModelScope.launch {
            when (val result = noteRepository.saveNote(note)) {
                is Result.Success -> {
                    _noteAdded.value = true
                }
                is Result.Error -> {
                    _noteAdded.value = false
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            when (val result = noteRepository.updateNote(note)) {
                is Result.Success -> {
                    _noteUpdated.value = true
                }
                is Result.Error -> {
                    _noteUpdated.value = false
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }

}