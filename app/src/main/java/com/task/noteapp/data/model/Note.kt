package com.task.noteapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey
    val id: String,
    var title: String,
    var description: String,
    var imageUrl: String?,
    var isEdited: Boolean = false,
    val dateCreated: String
) : Parcelable