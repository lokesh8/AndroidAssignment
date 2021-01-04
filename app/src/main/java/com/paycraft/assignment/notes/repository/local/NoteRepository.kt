package com.paycraft.assignment.notes.repository.local

import androidx.lifecycle.LiveData
import com.paycraft.assignment.notes.db.Note
import com.paycraft.assignment.notes.db.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun insert(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insert(note)
        }
    }

    fun delete(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteById(id)
        }
    }

    fun update(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(note)
        }
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }
}