package com.example.listvacancy.data.repository

import android.util.Log
import com.example.listvacancy.data.LocalDataSource
import com.example.listvacancy.data.RemoteDataSource
import com.example.listvacancy.domain.mapper.OfferDataMaper
import com.example.listvacancy.domain.mapper.VacancyDataMapper
import com.example.listvacancy.domain.model.ListOfferDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.domain.repository.ListVacancyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.asin

class ListVacancyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val vacancyDataMapper: VacancyDataMapper,
    private val offerDataMapper: OfferDataMaper,
) : ListVacancyRepository {
    override suspend fun getVacancies(): List<ListVacancyDomainModel> {


        // Сначала загружаем данные из кеша (БД) и возвращаем их
        val cachedVacancies = localDataSource.getVacanciesFromDB().map { vacancy ->
            vacancyDataMapper.mapToDomainFromDB(vacancy)
        }

        // Параллельно запускаем загрузку данных из сети для обновления кеша
        launchVacancyNetworkLoad()

        // Возвращаем данные из кеша, если они есть
        return cachedVacancies

    }

    private fun launchVacancyNetworkLoad() {
        // Запуск корутины для параллельной загрузки данных из сети и кеширования
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = remoteDataSource.getData()

                // Маппим вакансии из сети в доменные модели
                val vacancies = response.vacancies?.filterNotNull()?.map { vacancy ->
                    vacancyDataMapper.mapToDomain(vacancy)
                } ?: emptyList()

                if (vacancies.isNotEmpty()) {
                    // Сохраняем новые данные в кеш (БД)
                    localDataSource.saveVacanciesToDB(vacancies.map { vacancyDataMapper.mapToDatabase(it) })
                    Log.d("ListVacancyRepositoryImpl", "Данные из сети успешно загружены и сохранены в кеш")
                }
            } catch (e: Exception) {
                Log.e("ListVacancyRepositoryImpl", "Ошибка при получении данных из сети: ${e.message}")
            }
        }
    }

    override suspend fun getOffers(): List<ListOfferDomainModel> {

        // Сразу загружаем данные из кеша (БД)
        val cachedOffers = localDataSource.getOffersFromDB().map { offer ->
            offerDataMapper.mapOfferToDomainFromDB(offer)
        }

        // Параллельно запускаем загрузку из сети для обновления данных
        launchNetworkLoad()

        // Возвращаем данные из БД, если они есть
        return cachedOffers
    }

    override suspend fun saveFavorite(vacancy: ListVacancyDomainModel) {
        //мапим в другой класс
        localDataSource.upsertFavoriteDB(vacancyDataMapper.mapFavoriteToDatabase(vacancy))
    }

    override suspend fun deleteFavorite(vacancy: ListVacancyDomainModel) {
        localDataSource.deleteFavoriteDB(vacancyDataMapper.mapFavoriteToDatabase(vacancy))
    }

    private fun launchNetworkLoad() {
        // Запуск корутины для параллельной загрузки данных из сети и кеширования

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = remoteDataSource.getData()

                // Маппим предложения из сети в доменные модели
                val offers = response.offers?.filterNotNull()?.map { offer ->
                    offerDataMapper.mapOfferToDomain(offer)
                } ?: emptyList()

                if (offers.isNotEmpty()) {
                    // Сохраняем новые данные в кеш (БД)
                    localDataSource.saveOffersToDB(offers.map { offerDataMapper.mapOfferToDatabase(it) })
                    Log.d("ListVacancyRepositoryImpl", "Данные из сети успешно загружены и сохранены в кеш")
                }
            } catch (e: Exception) {
                Log.e("ListVacancyRepositoryImpl", "Ошибка при получении данных из сети: ${e.message}")
            }
        }
    }
}