package com.example.vacancy.data.repository

import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.domain.repository.CardVacancyRepository

class CardVacancyRepositoryImpl(): CardVacancyRepository {
    override suspend fun toggleFavorite(vacancyModel: CardVacancyDomainModel) {
        TODO("Not yet implemented")
    }
}