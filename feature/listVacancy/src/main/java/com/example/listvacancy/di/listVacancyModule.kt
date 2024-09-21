package com.example.listvacancy.di

import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import com.example.listvacancy.data.LocalDataSource
import com.example.listvacancy.data.RemoteDataSource
import com.example.listvacancy.data.repository.ListVacancyRepositoryImpl
import com.example.listvacancy.domain.mapper.OfferDataMaper
import com.example.listvacancy.domain.mapper.VacancyDataMapper
import com.example.listvacancy.domain.repository.ListVacancyRepository
import com.example.listvacancy.domain.usecase.FavoriteUseCase
import com.example.listvacancy.domain.usecase.ListVacancyUseCase
import com.example.listvacancy.presentation.ListVacancyViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listVacancyModule = module {
    // Регистрация HttpClient
    single {
        HttpClient(Android){
            install(ContentNegotiation){
                json()
            }
            install(Logging){
                level = LogLevel.ALL
            }
        }
    }
    // Биндинг для RemoteDataSource и LocalDataSource
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get(), get()) }  // Получение DAO-ов из core (VacancyDao, OfferDao, FavoritesDao)

    // Маппер для вакансий
    single { VacancyDataMapper() }
    single { OfferDataMaper() }

    // Биндинг для репозитория ListVacancyRepositoryImpl
    single<ListVacancyRepository> {
        ListVacancyRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            vacancyDataMapper = get(),
            offerDataMapper = get()
        )
    }

    // Use cases
    factory { ListVacancyUseCase(get()) }
    factory { FavoriteUseCase(get()) }

    // ViewModel
    viewModel {
        ListVacancyViewModel(
            listVacancyUseCase = get(),
            favoriteUseCase = get()
        )
    }
}