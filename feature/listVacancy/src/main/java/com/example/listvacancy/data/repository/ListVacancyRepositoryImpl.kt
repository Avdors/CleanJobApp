package com.example.listvacancy.data.repository

import com.example.listvacancy.data.LocalDataSource
import com.example.listvacancy.data.RemoteDataSource
import com.example.listvacancy.domain.mapper.VacancyDataMapper
import com.example.listvacancy.domain.model.ListOfferDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.domain.repository.ListVacancyRepository

class ListVacancyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val vacancyDataMapper: VacancyDataMapper
) : ListVacancyRepository {
    override suspend fun getVacancies(): List<ListVacancyDomainModel> {
        val vacanciesFromRemote = remoteDataSource.getVacanciesFromApi()
        val domainVacancies = vacanciesFromRemote.map { vacancyDataMapper.mapToDomain(it) }

        // Сохранение данных в локальную базу
        localDataSource.saveVacanciesToDB(vacanciesFromRemote)

        return domainVacancies
    }

    override suspend fun getOffers(): List<ListOfferDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveVacancies(vacancies: List<ListVacancyDomainModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveOffers(offers: List<ListOfferDomainModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<ListVacancyDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(vacancy: ListVacancyDomainModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavorite(vacancy: ListVacancyDomainModel) {
        TODO("Not yet implemented")
    }
}