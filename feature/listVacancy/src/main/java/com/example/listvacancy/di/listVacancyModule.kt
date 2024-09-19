package com.example.listvacancy.di

import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import com.example.listvacancy.presentation.ListVacancyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listVacancyModule = module {
    factory { GetMappedVacanciesUseCase(get()) }
    viewModel { ListVacancyViewModel(get()) }
}