package com.example.vacancy.domain.usecase

import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.domain.repository.CardVacancyRepository
import com.example.vacancy.presentation.model.CardVacancyModel

class FavoriteCardVacancyUseCase(private val repository: CardVacancyRepository) {

    suspend fun togleFavorite(vacancyModel: CardVacancyDomainModel){
        repository.toggleFavorite(vacancyModel)
    }
}