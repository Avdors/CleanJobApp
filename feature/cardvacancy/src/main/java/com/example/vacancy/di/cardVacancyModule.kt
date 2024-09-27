package com.example.vacancy.di

import com.example.core.data.database.AppDatabase
import com.example.vacancy.data.mapper.CardVacancyMapper
import com.example.vacancy.data.repository.CardVacancyRepositoryImpl
import com.example.vacancy.domain.repository.CardVacancyRepository
import com.example.vacancy.domain.usecase.FavoriteCardVacancyUseCase
import com.example.vacancy.domain.usecase.LoadVacancyUseCase
import com.example.vacancy.presentation.CardVacancyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cardVacancyModule = module {
    // DAO
    single { get<AppDatabase>().vacancyDao() }
    single { get<AppDatabase>().favoritesDao() }

    // Mapper
    single { CardVacancyMapper() }

    // Repository
    single<CardVacancyRepository> { CardVacancyRepositoryImpl(get(), get(), get()) }

    // Use cases
    factory { LoadVacancyUseCase(get()) }
    factory { FavoriteCardVacancyUseCase(get()) }

    // ViewModel
    viewModel { CardVacancyViewModel(get(), get()) }
}