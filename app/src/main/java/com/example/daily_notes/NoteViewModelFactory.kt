package com.example.daily_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(private val noteDataAccessObject: NoteDataAccessObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteDataAccessObject) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}