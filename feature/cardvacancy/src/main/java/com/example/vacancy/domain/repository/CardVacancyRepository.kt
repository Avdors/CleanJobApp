package com.example.vacancy.domain.repository

import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.presentation.model.CardVacancyModel
import kotlinx.coroutines.flow.Flow

interface CardVacancyRepository {

    suspend fun toggleFavorite(vacancyModel: CardVacancyDomainModel)
    suspend fun loadVacancyById(vacancyId: String): Flow<CardVacancyDomainModel>
}