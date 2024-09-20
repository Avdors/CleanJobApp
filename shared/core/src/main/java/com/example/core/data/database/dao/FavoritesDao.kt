package com.example.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.data.database.model.FavoriteVacModelDataBase
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Upsert
    suspend fun upsertItem(item: FavoriteVacModelDataBase)

    @Query("select * from vacancies")
    fun getItemList(): List<FavoriteVacModelDataBase>

    @Delete
    suspend fun delete(item: FavoriteVacModelDataBase)
}