package com.example.favoritevacancy.di

import com.example.core.data.database.AppDatabase
import com.example.favoritevacancy.data.repository.FavoriteVacancyRepositoryImpl
import com.example.favoritevacancy.domain.mapper.FavoriteVacancyDataMaper
import com.example.favoritevacancy.domain.repository.FavoriteVacancyRepository
import com.example.favoritevacancy.domain.usecase.FavoriteVacanciesUseCase
import com.example.favoritevacancy.presentation.FavoriteVacancyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteVacancyModule= module  {

    // RemoteDataSource и LocalDataSource не нужны, т.к. работа идет только с БД

    // Репозиторий для избранных вакансий
    single<FavoriteVacancyRepository> {
        FavoriteVacancyRepositoryImpl(
            vacancyDataMaper = get(),
            favoritesDao = get() // Dao для работы с БД (FavoritesDao)
        )
    }

    // Маппер для избранных вакансий (преобразование данных между Domain и Database слоями)
    single { FavoriteVacancyDataMaper() }

    // Use case для работы с избранными вакансиями (CRUD операции)
    factory { FavoriteVacanciesUseCase(get()) }

    // ViewModel для работы с избранными вакансиями
    viewModel {
        FavoriteVacancyViewModel(
            favoriteUseCase = get() // Подключаем use case к ViewModel
        )
    }

    // DAO для работы с таблицей избранных вакансий (FavoritesDao) - получаем из core
    single { get<AppDatabase>().favoritesDao() }
}