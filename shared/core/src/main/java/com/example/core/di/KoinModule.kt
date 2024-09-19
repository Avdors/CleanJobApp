package com.example.core.di

import com.example.core.api.KtorKlienProvider
import com.example.core.data.repository.VacancyRepositoryImpl
import com.example.core.domain.repository.VacancyRepository
import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val KoinModule = module {
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
    single { KtorKlienProvider.provideHttpClient() }
    single<VacancyRepository> { VacancyRepositoryImpl(get()) }
    factory { GetMappedVacanciesUseCase(get()) }
}