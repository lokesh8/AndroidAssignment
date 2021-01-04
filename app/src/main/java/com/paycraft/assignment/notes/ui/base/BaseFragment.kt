package com.paycraft.assignment.notes.ui.base

import android.graphics.Color
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.ui.NoteViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener


abstract class BaseFragment : DaggerFragment() {

    protected var selectedColor: String=""

    lateinit var noteViewModel: NoteViewModel

    protected fun pickColorTag() {
        activity?.let { it1 ->
            val colorPicker = ColorPicker(activity)
            val viewColor = if (selectedColor.isEmpty())"#FFFFFF" else selectedColor

            colorPicker.setOnChooseColorListener(object : OnChooseColorListener {
                override fun onChooseColor(position: Int, color: Int) {
                    selectedColor = java.lang.String.format("#%06X", 0xFFFFFF and color)
                    viewTag.setBackgroundColor(color)
                }

                override fun onCancel() {
                }
            })
                .disableDefaultButtons(false)
                .setColors(R.array.colors)
                .setDefaultColorButton(Color.parseColor(viewColor))
                .setDismissOnButtonListenerClick(true)
                .setColumns(5)
                .show()
        }
    }

    abstract fun saveNoteToDatabase()

    override fun onDestroyView() {
        super.onDestroyView()
        saveNoteToDatabase()
    }

    fun validations(): Boolean {
        return !(edtTitle.text.isNullOrEmpty()
                && edtNotes.text.isNullOrEmpty())
    }
}