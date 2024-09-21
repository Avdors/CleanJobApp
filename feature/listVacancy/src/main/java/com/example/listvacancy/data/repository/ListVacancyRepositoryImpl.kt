package com.example.listvacancy.data.repository

import android.util.Log
import com.example.listvacancy.data.LocalDataSource
import com.example.listvacancy.data.RemoteDataSource
import com.example.listvacancy.domain.mapper.OfferDataMaper
import com.example.listvacancy.domain.mapper.VacancyDataMapper
import com.example.listvacancy.domain.model.ListOfferDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.domain.repository.ListVacancyRepository

class ListVacancyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val vacancyDataMapper: VacancyDataMapper,
    private val offerDataMapper: OfferDataMaper,
) : ListVacancyRepository {
    override suspend fun getVacancies(): List<ListVacancyDomainModel> {
        return try {
            Log.d("ListVacancyRepositoryImpl", "start: ")
            // Получаем данные из сети через RemoteDataSource
            val response = remoteDataSource.getData()

            // Маппим вакансии из сети в доменные модели
            val vacancies = response.vacancies?.filterNotNull()?.map { vacancy ->
                vacancyDataMapper.mapToDomain(vacancy)
            } ?: emptyList()
            Log.d("ListVacancyRepositoryImpl", "vacancies: ${vacancies.size}")
            // Сохраняем в кеш
            localDataSource.saveVacanciesToDB(vacancies.map { vacancyDataMapper.mapToDatabase(it) })

            // Возвращаем вакансии
            vacancies
        } catch (e: Exception) {
            // Если ошибка сети, загружаем данные из кеша
            val cachedVacancies = localDataSource.getVacanciesFromDB()
            if (cachedVacancies.isNotEmpty()) {
                cachedVacancies.map {vacancy ->
                    vacancyDataMapper.mapToDomainFromDB(vacancy) }
            } else {
                throw Exception("Не удалось получить данные")
            }
        }
    }

    override suspend fun getOffers(): List<ListOfferDomainModel> {
        return try {
            val response = remoteDataSource.getData()

            // Маппинг предложений
            val offers = response.offers?.filterNotNull()?.map { offer ->
                offerDataMapper.mapOfferToDomain(offer)
            } ?: emptyList()

            // Сохраняем в локальную БД
            localDataSource.saveOffersToDB(offers.map { offerDataMapper.mapOfferToDatabase(it) })

            offers
        } catch (e: Exception) {
            // Загружаем из кеша при ошибке сети
            val cachedOffers = localDataSource.getOffersFromDB()
            if (cachedOffers.isNotEmpty()) {
                cachedOffers.map { offerDataMapper.mapOfferToDomainFromDB(it) }
            } else {
                throw Exception("Не удалось получить данные")
            }
        }
    }

    override suspend fun saveFavorite(vacancy: ListVacancyDomainModel) {
        //мапим в другой класс
        localDataSource.upsertFavoriteDB(vacancyDataMapper.mapFavoriteToDatabase(vacancy))
    }

    override suspend fun deleteFavorite(vacancy: ListVacancyDomainModel) {
        localDataSource.deleteFavoriteDB(vacancyDataMapper.mapFavoriteToDatabase(vacancy))
    }
}