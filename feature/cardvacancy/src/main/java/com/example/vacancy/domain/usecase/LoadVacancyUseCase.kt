package com.example.vacancy.domain.usecase

import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.domain.repository.CardVacancyRepository
import kotlinx.coroutines.flow.Flow

class LoadVacancyUseCase(private val repository: CardVacancyRepository) {
    suspend fun loadVacancyById(vacancyId: String): Flow<CardVacancyDomainModel> {
        return repository.loadVacancyById(vacancyId)
    }
    suspend fun loadVacancyByIdFavorite(vacancyId: String): Flow<CardVacancyDomainModel> {
        return repository.loadVacancyByIdFromFavorites(vacancyId)
    }

}