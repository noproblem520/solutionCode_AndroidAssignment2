package com.example.hw2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "english")
    val english: String,
    @ColumnInfo(name = "chinese")
    val chinese: String,
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean
)
