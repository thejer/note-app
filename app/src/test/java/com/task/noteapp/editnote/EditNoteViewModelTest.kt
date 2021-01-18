package com.task.noteapp.editnote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.LiveDataTestUtil
import com.task.noteapp.data.FakeRepository
import com.task.noteapp.data.FakeRepository.Companion.NOTE_ADDING_FAILED
import com.task.noteapp.data.FakeRepository.Companion.NOTE_UPDATE_FAILED
import com.task.noteapp.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class EditNoteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeRepository
    private lateinit var viewModel: EditNoteViewModel

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = FakeRepository()
        viewModel = EditNoteViewModel(repository)
    }

    @After
    fun tearDown() {
        repository.clearNotes()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `saveNote on success noteAdded is true`() {
        val note =  Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021",
            "22/3/2021"
        )

        viewModel.saveNote(note)
        assertThat(LiveDataTestUtil.getValue(viewModel.noteAdded), `is`(true))
    }

    @Test
    fun `updateNote on success noteEdited is true`() {
        val note =  Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            true,
            "22/3/2021",
            "22/3/2021"
        )

        viewModel.updateNote(note)
        assertThat(LiveDataTestUtil.getValue(viewModel.noteUpdated), `is`(true))
    }

    @Test
    fun `saveNote on failed errorMessage is as expected` () {
        val note =  Note(
            UUID.randomUUID().toString(),
            "failed",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021",
            "22/3/2021"
        )

        viewModel.saveNote(note)
        assertThat(LiveDataTestUtil.getValue(viewModel.errorMessage), `is`(NOTE_ADDING_FAILED))
    }

    @Test
    fun `updateNote on failed errorMessage is as expected`() {
        val note =  Note(
            UUID.randomUUID().toString(),
            "I see the stars",
            "The stars see me, God bless the stars and God Bless me.",
            "https://picsum.photos/200",
            false,
            "22/3/2021",
            "22/3/2021"
        )

        viewModel.updateNote(note)
        assertThat(LiveDataTestUtil.getValue(viewModel.errorMessage), `is`(NOTE_UPDATE_FAILED))
    }
}