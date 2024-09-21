package com.example.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.database.model.VacancyModelDataBase

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancies")
    suspend fun getAllVacancies(): List<VacancyModelDataBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancies(vacancies: List<VacancyModelDataBase>)
}