package com.task.noteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}