package com.task.noteapp.data.model

data class Note(
    var title: String,
    var description: String,
    var imageUrl: String,
    var isEdited: Boolean = false,
    val dateCreated: String
)