package com.example.notes.logic.Factory

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.logic.ViewModel.RegisterViewModel
import com.example.notes.logic.ViewModel.TagsViewModel

class TagsViewModelFactory(private val activity: Activity): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TagsViewModel(activity) as T
    }
}