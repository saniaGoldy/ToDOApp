package com.example.todoapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.entities.ToDoItemEntity
import kotlinx.coroutines.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "todoitementity"
    ).build()

    private var _toDoItemEntities =
        MutableLiveData<MutableList<ToDoItemEntity>>(mutableListOf())

    private val toDoItemEntities: LiveData<MutableList<ToDoItemEntity>>
        get() = _toDoItemEntities

    private val _isYellowThemeSelected: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>(updateIsYellowThemeItemSelected())

    val isYellowThemeSelected: LiveData<Boolean> get() = _isYellowThemeSelected

    private fun fetchData(): LiveData<MutableList<ToDoItemEntity>> {
        return database.toDoDao().getAll()
    }

    val toDoItemsList = MediatorLiveData<MutableList<ToDoItemEntity>>().apply {
        addSource(toDoItemEntities){value = it}
        addSource(fetchData()){value = it}
    }

    fun switchColorPrefs() {
        _isYellowThemeSelected.value = !_isYellowThemeSelected.value!!
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

    private fun saveEntry(toDoItemEntity: ToDoItemEntity): Job {
        return GlobalScope.launch {
            database.toDoDao().insertAll(toDoItemEntity)
        }
    }

    fun addAListEntry(content: String) {
        val newEntity = ToDoItemEntity(_toDoItemEntities.value!!.size, content)
        _toDoItemEntities.value?.add(newEntity)
        Log.d(TAG, "addAListEntry: $newEntity")
        saveEntry(newEntity)
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