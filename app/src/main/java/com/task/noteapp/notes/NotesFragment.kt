package com.task.noteapp.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.App
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.extensions.showSnackbar
import com.task.noteapp.extensions.viewUrl
import javax.inject.Inject

class NotesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as App).component.inject(this)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.notesRecyclerview.adapter = NotesAdapter(onImageUrlClicked = {requireActivity().viewUrl(it)}) {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        }
        binding.addNote.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToEditNoteFragment())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                view.showSnackbar(it, Snackbar.LENGTH_SHORT)
            }
        })
        viewModel.getNotes()
    }

}