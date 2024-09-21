package com.example.listvacancy.domain.usecase

import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.domain.repository.ListVacancyRepository

class FavoriteUseCase(private val repository: ListVacancyRepository) {


    suspend fun addToFavorites(vacancy: ListVacancyDomainModel) {
        repository.saveFavorite(vacancy)
    }

    suspend fun removeFromFavorites(vacancy: ListVacancyDomainModel) {
        repository.deleteFavorite(vacancy)
    }


}