package com.paycraft.assignment.notes.ui.addnote

import android.widget.Toast
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.ui.NoteActivity
import com.paycraft.assignment.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*


class AddNoteFragment : BaseFragment() {

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