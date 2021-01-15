package com.task.noteapp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.noteapp.data.model.Note
import javax.inject.Inject

class NotesViewModel @Inject constructor(): ViewModel() {

    private val _notes = MutableLiveData<MutableList<Note>>()
    val notes: LiveData<MutableList<Note>>
        get() = _notes

    init {
        _notes.value = mutableListOf(
            Note(
                "I see the stars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Moon",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Sun",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Nebula",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Jupiter",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),
            Note(
                "I see the Mars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021"
            ),

        )
    }
}