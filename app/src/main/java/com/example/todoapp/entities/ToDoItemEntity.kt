package com.example.todoapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false
)