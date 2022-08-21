package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.entities.ToDoItemEntity

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoitementity")
    fun getAll(): LiveData<MutableList<ToDoItemEntity>>

    @Query("SELECT * FROM todoitementity WHERE id LIKE :id")
    fun findById(id: Int): ToDoItemEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg todo: ToDoItemEntity)

    @Delete
    fun delete(todo: ToDoItemEntity)

    @Update
    fun updateTodo(vararg todos: ToDoItemEntity)
}