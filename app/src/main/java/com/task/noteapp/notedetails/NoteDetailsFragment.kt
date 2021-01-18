package com.task.noteapp.notedetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.task.noteapp.App
import com.task.noteapp.R
import com.task.noteapp.data.model.Note
import com.task.noteapp.databinding.FragmentNoteDetailsBinding
import com.task.noteapp.extensions.hide
import javax.inject.Inject

class NoteDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentNoteDetailsBinding

    private lateinit var viewModel: NoteDetailsViewModel

    private var selectedNote: Note? = null

    private var noteId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_note -> {
                viewModel.deleteNote(noteId!!)
                return true
            }
        }

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().applicationContext as App).component.inject(this)
        val args = NoteDetailsFragmentArgs.fromBundle(requireArguments())
        noteId = args.noteId

        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailsViewModel::class.java)

        viewModel.note.observe(viewLifecycleOwner, {
            it?.let {
                selectedNote = it
                if (it.imageUrl.isNullOrBlank())
                    binding.detailImage.hide()
                binding.editNote.show()
                binding.createdDate.text = String.format(getString(R.string.note_details_created), it.dateCreated)
                binding.modifiedDate.text = String.format(getString(R.string.mote_details_last_modified), it.lastModifiedDate)
            }
        })

        viewModel.noteDeleted.observe(viewLifecycleOwner, {
            if (it != null && it) findNavController().popBackStack()
        })

        viewModel.getNoteById(noteId!!)

        binding.viewModel = viewModel
        binding.editNote.setOnClickListener {
            findNavController()
                .navigate(
                    NoteDetailsFragmentDirections
                        .actionNoteDetailsFragmentToEditNoteFragment(
                            selectedNote
                        )
                )
        }
    }
}