package com.example.vacancy.domain.repository

import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.presentation.model.CardVacancyModel

interface CardVacancyRepository {

    suspend fun toggleFavorite(vacancyModel: CardVacancyDomainModel)
}