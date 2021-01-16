package com.task.noteapp

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.data.model.Note
import com.task.noteapp.extensions.hide
import com.task.noteapp.extensions.show
import com.task.noteapp.notes.NotesAdapter


@BindingAdapter("notes")
fun bindNotes(recyclerView: RecyclerView, data: MutableList<Note>?) {
    if (data.isNullOrEmpty()){
        recyclerView.hide()
    } else {
        recyclerView.show()
        val adapter = recyclerView.adapter as NotesAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("notesList")
fun bindEmptyNotesView(emptyNotesView: ConstraintLayout, data: MutableList<Note>?) {
    if (data.isNullOrEmpty()) {
        emptyNotesView.show()
    } else {
        emptyNotesView.hide()
    }
}