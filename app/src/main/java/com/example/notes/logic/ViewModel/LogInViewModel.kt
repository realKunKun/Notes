package com.example.notes.logic.ViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.logic.LogInRepository
import com.example.notes.logic.NetRepository
import com.example.notes.logic.model.GlobalValue
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.UserModel


@RequiresApi(Build.VERSION_CODES.O)
class LogInViewModel(activity: Activity):ViewModel() {
    private val repository : LogInRepository by lazy { LogInRepository() }

    private var userData: UserModel? =null
    var state=""
    var access=false
    init {
        repository.autoLogIn(activity)
        Thread.sleep(1000)
        access = GlobalValue.accessState
    }
    fun setUser(user:UserModel,activity: Activity){
        userData=user
        val respond=repository.logIn(user.password,user.name,activity)
        state=respond
    }
}