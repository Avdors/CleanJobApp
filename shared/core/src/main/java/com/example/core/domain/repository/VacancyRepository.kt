package com.example.core.domain.repository

import com.example.core.data.model.ResponseOfferAndVacancies

interface VacancyRepository {
    suspend fun getData(): ResponseOfferAndVacancies

}