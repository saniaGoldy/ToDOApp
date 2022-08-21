package com.example.todoapp.database

import androidx.room.*
import com.example.todoapp.entities.ToDoItemEntity

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoitementity")
    fun getAll(): List<ToDoItemEntity>

    @Query("SELECT * FROM todoitementity WHERE id LIKE :id")
    fun findById(id: Int): ToDoItemEntity

    @Insert
    fun insertAll(todoEntities: List<ToDoItemEntity>)

    @Delete
    fun delete(todo: ToDoItemEntity)

    @Update
    fun updateTodo(vararg todos: ToDoItemEntity)
}