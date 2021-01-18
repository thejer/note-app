package com.task.noteapp.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.task.noteapp.R
import com.task.noteapp.data.model.Note
import com.task.noteapp.databinding.NoteItemLayoutBinding
import com.task.noteapp.extensions.hide
import com.task.noteapp.extensions.inflate
import com.task.noteapp.extensions.show
import com.task.noteapp.extensions.underline

class NotesAdapter(
    val onImageUrlClicked: (String) -> Unit,
    val noteClickListener: (String, MaterialCardView) -> Unit,
    val noteLongClickedListener: (String, MaterialCardView) -> Unit
) : ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallback) {

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
            binding.noteCard.setOnLongClickListener {
                noteLongClickedListener(note.id, binding.noteCard)
                true
            }
            binding.noteCard.setOnClickListener {
                noteClickListener(note.id, binding.noteCard)
            }
            binding.imageUrl.setOnClickListener {
                onImageUrlClicked(note.imageUrl ?: "")
            }
            binding.imageUrl.underline()
            if (note.imageUrl.isNullOrBlank()) binding.imageUrl.hide()
            else binding.imageUrl.show()
            if (note.isEdited) binding.isEdited.show() else binding.isEdited.hide()
            binding.executePendingBindings()
        }
    }

}