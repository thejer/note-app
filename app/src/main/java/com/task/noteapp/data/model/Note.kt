package com.task.noteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val id: String,
    var title: String,
    var description: String,
    var imageUrl: String?,
    var isEdited: Boolean? = false,
    val dateCreated: String
)