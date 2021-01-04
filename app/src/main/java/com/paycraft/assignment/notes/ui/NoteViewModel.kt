package com.paycraft.assignment.notes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.repository.local.NoteRepository
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun insert(note: Note) {
        return noteRepository.insert(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deleteById(id:Int){
        noteRepository.deleteById(id)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteRepository.getAllNotes()
    }

}