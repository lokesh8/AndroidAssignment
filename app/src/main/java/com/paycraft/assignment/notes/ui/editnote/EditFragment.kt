package com.paycraft.assignment.notes.ui.editnote

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.ui.NoteActivity
import com.paycraft.assignment.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*


class EditFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareNoteForEditing()
    }

    override fun saveNoteToDatabase() {

        (activity as NoteActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
        } else {
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()

            val id: Int = EditFragmentArgs.fromBundle(arguments!!).note?.id!!
            noteViewModel.deleteById(id)
        }
    }

    private fun saveNote() {

        val id:Int? = EditFragmentArgs.fromBundle(arguments!!).note?.id

        val note = Note(id!!,edtTitle.text.toString(),edtNotes.text.toString(),if (selectedColor.isEmpty())"#FFFFFF" else selectedColor,isPin)

        //If title is null set Empty Title
        if (edtTitle.text.isNullOrEmpty()) {
            note.title = "Empty Title"
            noteViewModel.update(note)
        }else{
            noteViewModel.update(note)
        }
    }

    private fun prepareNoteForEditing() {
        arguments?.let {
            val safeArgs = EditFragmentArgs.fromBundle(it)
            val note = safeArgs.note
            edtTitle.setText(note?.title.toString())
            edtNotes.setText(note?.description.toString())
            selectedColor = if (note?.tag.toString().isEmpty())"#FFFFFF" else note?.tag.toString()
            viewTag.setBackgroundColor(Color.parseColor(selectedColor))
            isPin= note?.isPin!!
            if (isPin==0){
                activity?.let { it1 -> ContextCompat.getColor(it1, R.color.black) }?.let { it2 ->
                    ibPin.setColorFilter(
                        it2, android.graphics.PorterDuff.Mode.SRC_IN)
                }
            }else{
                activity?.let { it1 -> ContextCompat.getColor(it1, R.color.design_default_color_primary) }?.let { it2 ->
                    ibPin.setColorFilter(
                        it2, android.graphics.PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }
}

