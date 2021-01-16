package com.task.noteapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private lateinit var noteDao: NoteDao

    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertNoteAndGetNotes() = runBlocking {
        val note = Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021"
        )
        noteDao.insertNote(note)
        val dbNotes = noteDao.getAllNotes()
        assertThat(dbNotes.size, `is`(1))
        assertThat(dbNotes[0], `is`(note))
    }

    @Test
    @Throws(Exception::class)
    fun insertNoteAndGetNoteById() = runBlocking {
        val note = Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021"
        )
        noteDao.insertNote(note)
        val dbNote = noteDao.getNoteById(note.id)
        assertThat(dbNote, `is`(note))
    }

    @Test
    @Throws(Exception::class)
    fun insertNoteAndUpdateNote() = runBlocking {
        val note = Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021"
        )
        noteDao.insertNote(note)
        val dbNote = noteDao.getNoteById(note.id)
        assertThat(dbNote, `is`(note))
        dbNote?.isEdited = true
        dbNote?.title = "Let us start over."
        noteDao.updateNote(dbNote!!)
        val updatedNote = noteDao.getNoteById(note.id)
        assertThat(updatedNote?.isEdited, `is`(true))
        assertThat(updatedNote?.title, `is`("Let us start over."))
    }

    @Test
    @Throws(Exception::class)
    fun insertNoteAndDeleteNote() = runBlocking {
        val note = Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021"
        )
        noteDao.insertNote(note)
        val dbNotes = noteDao.getAllNotes()
        assertThat(dbNotes.size, `is`(1))
        noteDao.deleteNote(note.id)
        val emptyNotes = noteDao.getAllNotes()
        assertThat(emptyNotes.size, `is`(0))
    }
}