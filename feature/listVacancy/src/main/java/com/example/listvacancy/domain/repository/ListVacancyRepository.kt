package com.example.listvacancy.domain.repository

import com.example.core.data.model.ResponseOfferAndVacancies
import com.example.listvacancy.domain.model.ListOfferDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel

interface ListVacancyRepository {
    suspend fun getVacancies(): List<ListVacancyDomainModel>
    suspend fun getOffers(): List<ListOfferDomainModel>
    suspend fun saveVacancies(vacancies: List<ListVacancyDomainModel>)
    suspend fun saveOffers(offers: List<ListOfferDomainModel>)
    suspend fun getFavorites(): List<ListVacancyDomainModel>
    suspend fun saveFavorite(vacancy: ListVacancyDomainModel)
    suspend fun deleteFavorite(vacancy: ListVacancyDomainModel)
}