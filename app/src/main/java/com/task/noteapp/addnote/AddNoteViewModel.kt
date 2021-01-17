package com.task.noteapp.addnote

import androidx.lifecycle.ViewModel
import com.task.noteapp.data.local.INoteRepository
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): ViewModel() {
}