package com.task.noteapp.editnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.App
import com.task.noteapp.R
import com.task.noteapp.data.model.Note
import com.task.noteapp.databinding.FragmentEditNoteBinding
import com.task.noteapp.extensions.isEmpty
import com.task.noteapp.extensions.showDialog
import com.task.noteapp.extensions.showSnackbar
import com.task.noteapp.extensions.stringContent
import com.task.noteapp.main.MainActivity
import com.task.noteapp.utils.DateUtils
import com.task.noteapp.utils.validateTextLayouts
import java.util.*
import javax.inject.Inject


class EditNoteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentEditNoteBinding

    private lateinit var viewModel: EditNoteViewModel

    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
    private val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as App).component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditNoteViewModel::class.java)

        viewModel.noteAdded.observe(viewLifecycleOwner, {
            if (it != null && it) {
                view.showSnackbar(getString(R.string.note_added), Snackbar.LENGTH_SHORT)
                findNavController().popBackStack()
            }
        })

        viewModel.noteUpdated.observe(viewLifecycleOwner, {
            if (it != null && it) {
                view.showSnackbar(getString(R.string.note_updated), Snackbar.LENGTH_SHORT)
                findNavController().popBackStack()
            }
        })

        val args = EditNoteFragmentArgs.fromBundle(requireArguments())

        val note = args.note
        note?.let {
            binding.note = note
            isEditing = true
        }

        setOnBackPressedCallback(note)

        binding.saveNote.setOnClickListener {
            saveNote(note)
        }
    }


    override fun onResume() {
        super.onResume()
        mainActivity.showUpButton()
    }

    private fun saveNote(note: Note?) {
        val isImageUrlUrlValid = validateImageUrl()
        val areInputsValid = validateTextLayouts(binding.titleField, binding.descField)
        if (areInputsValid && isImageUrlUrlValid) {
            if (isEditing) updateNote(note!!)
            else createNote()
        } else {
            requireView().showSnackbar(getString(R.string.cannot_save_note), Snackbar.LENGTH_SHORT)
        }
    }

    private fun setOnBackPressedCallback(note: Note?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val dialogParametersMap = mapOf(
                        "title" to getString(R.string.discard_editing_title),
                        "message" to getString(R.string.discard_editing_message),
                        "neutral_text" to getString(R.string.continue_editing),
                        "negative_text" to getString(R.string.discard_note),
                        "positive_text" to getString(R.string.save_note),
                    )
                    requireContext().showDialog(
                        dialogParametersMap,
                        { it.dismiss() },
                        { it.dismiss()
                            findNavController().popBackStack()
                        }){
                        it.dismiss()
                        saveNote(note)
                    }
                }
            })
    }

    private fun updateNote(note: Note){
        val lastModifiedDate = DateUtils.getCurrentDate(false)
        note.title = binding.titleField.stringContent()
        note.description = binding.descField.stringContent()
        note.imageUrl = binding.imageUrlInput.stringContent()
        note.lastModifiedDate = lastModifiedDate
        note.isEdited = true
        viewModel.updateNote(note)
    }

    private fun createNote() {
        val createdDate = DateUtils.getCurrentDate(true)
        val lastModifiedDate = DateUtils.getCurrentDate(false)
        val note = Note(
            UUID.randomUUID().toString(),
            binding.titleField.stringContent(),
            binding.descField.stringContent(),
            binding.imageUrlInput.stringContent(),
            false,
            createdDate,
            lastModifiedDate
        )
        viewModel.saveNote(note)
    }

    private fun validateImageUrl(): Boolean {
        val isImageUrlUrlValid = binding.imageUrlInput.isEmpty() ||
                URLUtil.isValidUrl(binding.imageUrlInput.stringContent())

        if (!isImageUrlUrlValid)
            binding.imageUrlInput.error = getString(R.string.invalid_image_url)
        else
            binding.imageUrlInput.error = null
        return isImageUrlUrlValid
    }

}