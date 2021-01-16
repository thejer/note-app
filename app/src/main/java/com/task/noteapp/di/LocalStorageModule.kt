package com.task.noteapp.di

import android.app.Application
import androidx.room.Room
import com.task.noteapp.data.NoteRepository
import com.task.noteapp.data.local.INoteRepository
import com.task.noteapp.data.local.NoteDatabase
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
    fun provideNoteRepository(noteDatabase: NoteDatabase): INoteRepository {
        return NoteRepository(noteDatabase)
    }

}