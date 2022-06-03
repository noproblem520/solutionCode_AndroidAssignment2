package com.example.hw2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from item WHERE isFavourite = 1 ORDER BY english ASC")
    fun getFavouriteItems(): Flow<List<Item>>

    @Query("SELECT * from item ORDER BY english ASC")
    fun getItems(): Flow<List<Item>>

}