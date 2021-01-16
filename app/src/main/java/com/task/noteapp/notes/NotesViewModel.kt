package com.task.noteapp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.data.NoteDatabase
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val noteDatabase: NoteDatabase

): ViewModel() {

    private val _notes = MutableLiveData<MutableList<Note>>()
    val notes: LiveData<MutableList<Note>>
        get() = _notes

    init {
        _notes.value = mutableListOf(
            Note(
                UUID.randomUUID().toString(),
                "I see the stars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Moon",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Sun",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Nebula",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Jupiter",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                UUID.randomUUID().toString(),
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            )

        )
    }

    fun getNotes() {
        viewModelScope.launch {
            noteDatabase.noteDao().getAllNotes()
        }
    }
}