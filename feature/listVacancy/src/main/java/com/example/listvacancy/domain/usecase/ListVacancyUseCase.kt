package com.example.listvacancy.domain.usecase

import com.example.listvacancy.domain.model.ListOfferDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.domain.repository.ListVacancyRepository

class ListVacancyUseCase(private val repository: ListVacancyRepository) {
    suspend fun getVacancies(): List<ListVacancyDomainModel> {
        return repository.getVacancies()
    }

    suspend fun getOffers(): List<ListOfferDomainModel> {
        return repository.getOffers()
    }
}