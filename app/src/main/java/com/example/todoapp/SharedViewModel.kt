package com.example.todoapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SharedViewModel(application: Application) : AndroidViewModel(application) {


    private val _toDoItemData = MutableLiveData(mutableListOf(ToDoItemData("Some content")))

    val toDoItemData: LiveData<MutableList<ToDoItemData>>
        get() = _toDoItemData

    private val _isYellowThemeSelected: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>(updateIsYellowThemeItemSelected())

    val isYellowThemeSelected: LiveData<Boolean> get() = _isYellowThemeSelected

    fun switchColorPrefs() {
        _isYellowThemeSelected.value = !isYellowThemeSelected.value!!
        Log.d("MyApp", "switchColorPrefs: ${isYellowThemeSelected.value}")

        getApplication<Application>().getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        ).edit().putBoolean(
            IS_YELLOW_THEME_SELECTED_PREF_NAME,
            _isYellowThemeSelected.value!!
        )
            .apply()

    }

    private fun updateIsYellowThemeItemSelected(): Boolean {
        return getApplication<Application>().getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        ).getBoolean(
            IS_YELLOW_THEME_SELECTED_PREF_NAME, false
        )
    }

    companion object {
        const val IS_YELLOW_THEME_SELECTED_PREF_NAME = "isYellowThemeSelected"
        const val SHARED_PREFERENCES_NAME = "mySharedPref"
    }
}