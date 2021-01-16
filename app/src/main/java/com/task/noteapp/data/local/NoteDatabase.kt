package com.task.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}