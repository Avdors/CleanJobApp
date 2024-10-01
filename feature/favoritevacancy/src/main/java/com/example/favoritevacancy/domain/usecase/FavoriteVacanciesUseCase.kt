package com.example.favoritevacancy.domain.usecase

import com.example.favoritevacancy.domain.model.FavoriteVacancyDomainModel
import com.example.favoritevacancy.domain.repository.FavoriteVacancyRepository
import kotlinx.coroutines.flow.Flow

class FavoriteVacanciesUseCase(private val repository: FavoriteVacancyRepository) {

    suspend fun getListVacancies(): Flow<List<FavoriteVacancyDomainModel>> {
        return repository.getVacancies()
    }

    suspend fun addToFavorites(vacancy: FavoriteVacancyDomainModel) {
        repository.saveFavorite(vacancy)
    }

    suspend fun removeFromFavorites(vacancy: FavoriteVacancyDomainModel) {
        repository.deleteFavorite(vacancy)
    }

}