package com.example.notes.logic.Factory

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.logic.ViewModel.LogInViewModel
import com.example.notes.logic.ViewModel.MainViewModel

class LogInViewModelFactory(private val activity: Activity): ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogInViewModel(activity) as T
    }
}