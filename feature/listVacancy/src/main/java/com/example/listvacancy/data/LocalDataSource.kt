package com.example.listvacancy.data

import com.example.core.data.database.dao.FavoritesDao
import com.example.core.data.database.dao.OfferDao
import com.example.core.data.database.dao.VacancyDao
import com.example.core.data.database.model.FavoriteVacModelDataBase
import com.example.core.data.database.model.OfferModelDataBase
import com.example.core.data.database.model.VacancyModelDataBase

class LocalDataSource(
    private val vacancyDao: VacancyDao,
    private val offerDao: OfferDao,
    private val favoritesDao: FavoritesDao
) {

    suspend fun getVacanciesFromDB(): List<VacancyModelDataBase> {
        return vacancyDao.getAllVacancies()
    }

    suspend fun saveVacanciesToDB(vacancies: List<VacancyModelDataBase>) {
        vacancyDao.insertVacancies(vacancies)
    }

    suspend fun getOffersFromDB(): List<OfferModelDataBase> {
        return offerDao.getAllOffers()
    }

    suspend fun saveOffersToDB(offers: List<OfferModelDataBase>) {
        offerDao.insertOffers(offers)
    }

    suspend fun upsertFavoriteDB(item: FavoriteVacModelDataBase) {
        // здесь надо правильно преобразовать item в FavoriteVacModelDataBase
         favoritesDao.upsertItem(item)
    }

    suspend fun deleteFavoriteDB(item: FavoriteVacModelDataBase) {
        // здесь надо правильно преобразовать item в FavoriteVacModelDataBase
        favoritesDao.delete(item)
    }

}