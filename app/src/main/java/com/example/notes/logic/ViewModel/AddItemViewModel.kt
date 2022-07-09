package com.example.notes.logic.ViewModel

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.notes.logic.UploadNetRepository

@RequiresApi(Build.VERSION_CODES.O)
class AddItemViewModel:ViewModel() {
    private val repository : UploadNetRepository by lazy { UploadNetRepository() }
    fun uploadImage(mActivty:Activity){
        repository.upLoad(mActivty)
    }
}