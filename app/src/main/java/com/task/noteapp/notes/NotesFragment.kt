package com.task.noteapp.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.App
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.extensions.showSnackbar
import com.task.noteapp.extensions.viewUrl
import com.task.noteapp.main.MainActivity
import javax.inject.Inject

class NotesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentNotesBinding

    private val checkedNotes = mutableSetOf<String>()

    private lateinit var menuItem: MenuItem

    private lateinit var viewModel: NotesViewModel

    private val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menuItem = menu.findItem(R.id.delete_note)
        menuItem.isVisible = checkedNotes.isNotEmpty()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_note -> {
                viewModel.deleteNotes(checkedNotes)
                return true
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideUpButton()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as App).appComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        setupRecyclerview()
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                view.showSnackbar(it, Snackbar.LENGTH_SHORT)
            }
        })

        viewModel.notesDeleted.observe(viewLifecycleOwner, {
            viewModel.getNotes()
        })

        viewModel.getNotes()
    }

    private fun setupRecyclerview() {
        binding.notesRecyclerview.adapter = NotesAdapter(
            {
                requireActivity().viewUrl(it)
            },
            { noteId: String, cardView: MaterialCardView ->
                onNoteClicked(noteId, cardView)
            })
        { noteId: String, noteCard: MaterialCardView ->
            onLongPressNote(noteId, noteCard)
        }

        binding.addNote.setOnClickListener {
            findNavController()
                .navigate(NotesFragmentDirections.actionNotesFragmentToEditNoteFragment())
        }
    }

    private fun onLongPressNote(
        noteId: String,
        noteCard: MaterialCardView
    ) {
        checkedNotes.add(noteId)
        menuItem.isVisible = checkedNotes.isNotEmpty()
        noteCard.isChecked = true
    }

    private fun onNoteClicked(noteId: String, cardView: MaterialCardView) {
        when {
            cardView.isChecked -> {
                checkedNotes.remove(noteId)
                cardView.isChecked = false
                menuItem.isVisible = checkedNotes.isNotEmpty()
            }
            checkedNotes.isNotEmpty() -> {
                cardView.isChecked = true
                checkedNotes.add(noteId)
            }
            else -> findNavController()
                .navigate(
                    NotesFragmentDirections
                        .actionNotesFragmentToNoteDetailsFragment(noteId)
                )
        }
    }

}