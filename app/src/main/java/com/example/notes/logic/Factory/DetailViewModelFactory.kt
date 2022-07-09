package com.example.notes.logic.Factory

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.logic.ViewModel.DetailViewModel
import com.example.notes.logic.ViewModel.DisplayViewModel
import com.example.notes.logic.ViewModel.LogInViewModel

class DetailViewModelFactory(private val activity: Activity): ViewModelProvider.Factory  {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(activity) as T
    }
}