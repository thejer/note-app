package com.task.noteapp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.local.NoteDataSource
import com.task.noteapp.data.Result
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource

): ViewModel() {

    private val _notes = MutableLiveData<MutableList<Note>>()
    val notes: LiveData<MutableList<Note>>
        get() = _notes


    fun getNotes() {
        viewModelScope.launch {
            when (val result = noteDataSource.getNotes()) {
                is Result.Success -> {
                    _notes.value = result.data
                }
            }
        }
    }
}