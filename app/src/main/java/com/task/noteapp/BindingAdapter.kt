package com.task.noteapp

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.data.model.Note
import com.task.noteapp.notes.NotesAdapter


@BindingAdapter("notes")
fun bindNotes(recyclerView: RecyclerView, data: MutableList<Note>?) {
    data?.let {
        val adapter = recyclerView.adapter as NotesAdapter
        adapter.submitList(data)
    }
}