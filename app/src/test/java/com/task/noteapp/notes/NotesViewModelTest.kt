package com.task.noteapp.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.LiveDataTestUtil
import com.task.noteapp.data.FakeRepository
import com.task.noteapp.data.FakeRepository.Companion.NO_NOTES
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

@RunWith(JUnit4::class)
class NotesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeRepository
    private lateinit var viewModel: NotesViewModel

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
        viewModel = NotesViewModel(repository)
    }

    @After
    fun tearDown() {
        repository.clearNotes()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `getNotes on success notes count must be 5`() {
        viewModel.getNotes()
        assertThat(LiveDataTestUtil.getValue(viewModel.notes).size, `is`(3))
    }

    @Test
    fun `getNotes on failure error message must be valid`() {
        repository.clearNotes()
        viewModel.getNotes()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorMessage), `is`(NO_NOTES))
    }

}