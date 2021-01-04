package com.paycraft.assignment.notes.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.di.ViewModelProviderFactory
import com.paycraft.assignment.notes.ui.NoteActivity
import com.paycraft.assignment.notes.ui.NoteViewModel
import com.paycraft.assignment.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*
import javax.inject.Inject


class AddNoteFragment : BaseFragment() {

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    var isPin: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(NoteViewModel::class.java)

        tvTag.setOnClickListener {
            pickColorTag()
        }

        viewTag.setOnClickListener {
            pickColorTag()
        }

        btnAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
        }

        ibPin.setOnClickListener {
            if (isPin>0){
                isPin=0
                activity?.let { it1 -> ContextCompat.getColor(it1, R.color.black) }?.let { it2 ->
                    ibPin.setColorFilter(
                        it2, android.graphics.PorterDuff.Mode.SRC_IN)
                }
            }else{
                isPin=1
                activity?.let { it1 -> ContextCompat.getColor(it1, R.color.design_default_color_primary) }?.let { it2 ->
                    ibPin.setColorFilter(
                        it2, android.graphics.PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }

    override fun saveNoteToDatabase() {

        (activity as NoteActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
        } else
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()

    }

    private fun saveNote() {
        val note = Note(
            0,
            edtTitle.text.toString(),
            edtNotes.text.toString(),
            if (selectedColor.isEmpty()) "#FFFFFF" else selectedColor,
            isPin
        )

        //If title is null set Empty Title
        if (edtTitle.text.isNullOrEmpty()) {
            note.title = "Empty Title"
            noteViewModel.insert(note)

        } else {
            noteViewModel.insert(note)
        }
    }

}