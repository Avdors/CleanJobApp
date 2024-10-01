package com.example.favoritevacancy.domain.repository

import com.example.favoritevacancy.domain.model.FavoriteVacancyDomainModel
import kotlinx.coroutines.flow.Flow


interface FavoriteVacancyRepository {
    suspend fun getVacancies(): Flow<List<FavoriteVacancyDomainModel>>
    suspend fun saveFavorite(vacancy: FavoriteVacancyDomainModel)
    suspend fun deleteFavorite(vacancy: FavoriteVacancyDomainModel)
}