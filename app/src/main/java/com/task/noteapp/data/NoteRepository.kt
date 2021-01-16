package com.task.noteapp.data

import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabase: NoteDatabase
) {
    
}