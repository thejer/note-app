package com.task.noteapp.notedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.LiveDataTestUtil
import com.task.noteapp.data.FakeRepository
import com.task.noteapp.data.FakeRepository.Companion.NOTE_DELETION_FAILED
import com.task.noteapp.data.FakeRepository.Companion.NO_SUCH_NOTE
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeRepository
    private lateinit var viewModel: NoteDetailsViewModel

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = FakeRepository()
        val notes = mutableListOf(
            Note(
                "noteId1",
                "I see the stars",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021",
                "22/3/2021"
            ),
            Note(
                "noteId2",
                "I see the Moon",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021",
                "22/3/2021"
            ),
            Note(
                "noteId3",
                "I see the Sun",
                "The stars see me, God bless the stars and God Bless me.",
                "https://picsum.photos/200",
                false,
                "22/3/2021",
                "22/3/2021"
            ))
        repository.saveNotes(notes)
        viewModel = NoteDetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        repository.clearNotes()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `deleteNote on success noteDeleted is true` () {
        viewModel.deleteNote("noteId1")
        assertThat(LiveDataTestUtil.getValue(viewModel.noteDeleted), Matchers.`is`(true))
    }
    
    @Test
    fun `deleteNote on failure errorMessage is as expected` () {
        viewModel.deleteNote("noteId2")
        assertThat(LiveDataTestUtil.getValue(viewModel.errorMessage), Matchers.`is`(NOTE_DELETION_FAILED))
    }

    @Test
    fun `getNoteById on success note is as expected`() {
        viewModel.getNoteById("noteId3")
        val note =  Note(
            "noteId3",
            "I see the Sun",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021",
            "22/3/2021"
        )
        assertThat(LiveDataTestUtil.getValue(viewModel.note), Matchers.`is`(note))
    }

    @Test
    fun `getNoteById on failure errorMessage is as expected`() {
        viewModel.getNoteById("noteId0")
        assertThat(LiveDataTestUtil.getValue(viewModel.errorMessage), Matchers.`is`(NO_SUCH_NOTE))
    }

}