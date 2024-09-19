package com.example.listvacancy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListVacancyViewModel(private val getMappedVacanciesUseCase: GetMappedVacanciesUseCase): ViewModel() {

    private val _vacancies = MutableStateFlow<List<VacancyModel>>(emptyList())
    val vacancies: StateFlow<List<VacancyModel>> = _vacancies

    fun loadVacancies() {
        viewModelScope.launch {
            try {
                // Получаем список Domain моделей
                val domainVacancies = getMappedVacanciesUseCase()

                // Маппим Domain модели в UI модели
                val uiVacancies = VacancyMapper.mapToUIModelList(domainVacancies)
                _vacancies.value = uiVacancies

                // Логирование данных вакансий
                uiVacancies.forEach { vacancy ->
                    Log.d("ListVacancyViewModel", "Vacancy: $vacancy")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ListVacancyViewModel", "Error loading vacancies: ${e.message}")
            }
        }
    }
}