package com.paycraft.assignment.notes.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.paycraft.assignment.notes.R
import com.paycraft.assignment.notes.di.ViewModelProviderFactory
import com.paycraft.assignment.notes.ui.NoteViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    protected var selectedColor: String=""

    protected var isPin: Int = 0

    lateinit var noteViewModel: NoteViewModel

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = ViewModelProvider(this,viewmodelProviderFactory).get(NoteViewModel::class.java)

        tvTag.setOnClickListener {
            pickColorTag()
        }

        viewTag.setOnClickListener {
            pickColorTag()
        }

        btnAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.container).popBackStack()
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

    protected fun pickColorTag() {
        activity?.let { it1 ->
            val colorPicker = ColorPicker(it1)
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