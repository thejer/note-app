package com.task.noteapp.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.data.model.Note
import com.task.noteapp.databinding.NoteItemLayoutBinding
import com.task.noteapp.extensions.inflate
import com.task.noteapp.extensions.underline

class NotesAdapter : ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(NoteItemLayoutBinding.bind(parent.inflate(R.layout.note_item_layout)))

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem

    }

    inner class NotesViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.imageUrl.underline()
            binding.executePendingBindings()
        }
    }

}