package com.task.noteapp.di

import android.app.Application
import androidx.room.Room
import com.task.noteapp.data.NoteDataSource
import com.task.noteapp.data.NoteDatabase
import com.task.noteapp.data.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase = Room.databaseBuilder(app,
        NoteDatabase::class.java,
        "note-database.db"
    ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteDataSource {
        return NoteRepository(noteDatabase)
    }

}