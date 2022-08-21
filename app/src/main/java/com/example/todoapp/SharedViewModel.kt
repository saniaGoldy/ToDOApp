package com.example.todoapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.entities.ToDoItemEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "todoitementity"
    ).build()

    private val _toDoItemEntities =
        MutableLiveData(mutableListOf(ToDoItemEntity(0, "Some content")))

    val toDoItemEntities: LiveData<MutableList<ToDoItemEntity>>
        get() = _toDoItemEntities

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

    fun saveData(): Job {
        return viewModelScope.launch {
            _toDoItemEntities.value?.let { database.toDoDao().insertAll(it) }
        }
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