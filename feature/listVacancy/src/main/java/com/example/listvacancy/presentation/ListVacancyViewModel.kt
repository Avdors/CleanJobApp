package com.example.listvacancy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import com.example.listvacancy.presentation.model.ButtonModel
import com.example.listvacancy.presentation.model.OfferModel
import com.example.listvacancy.presentation.model.VacancyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListVacancyViewModel(private val getMappedVacanciesUseCase: GetMappedVacanciesUseCase): ViewModel() {

    private val _vacancies = MutableStateFlow<List<VacancyModel>>(emptyList())
    val vacancies: StateFlow<List<VacancyModel>> = _vacancies

    // Для хранения списка предложений (offers)
    private val _offers = MutableStateFlow<List<OfferModel>>(emptyList())
    val offers: StateFlow<List<OfferModel>> = _offers

    // StateFlow для ошибок
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // Загружаем данные сразу при инициализации ViewModel
        viewModelScope.launch {
            loadVacancies()
            loadOffers()
        }
    }

    suspend fun loadVacancies() {
       viewModelScope.launch {
            try {
                // Получаем список Domain моделей
                val domainVacancies = getMappedVacanciesUseCase.getVacancy()

                // Маппим Domain модели в UI модели
                val uiVacancies = VacancyMapper.mapToUIModelList(domainVacancies)
                _vacancies.value = uiVacancies

                // Логирование данных вакансий
                uiVacancies.forEach { vacancy ->
                    Log.d("ListVacancyViewModel", "Vacancy: ${vacancy.title}")
                }
            }  catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка загрузки вакансий: ${e.message}")
                _error.value = e.message
            }
        }
    }

    // Загрузка предложений (offers)
   suspend fun loadOffers() {
        viewModelScope.launch {
            try {
                val domainOffers = getMappedVacanciesUseCase.getOffers()
                val uiOffers = domainOffers.map { domainModel ->
                    OfferModel(
                        id = domainModel.id ?: "",
                        title = domainModel.title,
                        link = domainModel.link,
                        button = domainModel.button?.let { ButtonModel(it.text) }
                    )
                }
                uiOffers.forEach {offers ->
                    Log.d("ListVacancyViewModel", "Vacancy: ${offers.title}")
                }
                _offers.value = uiOffers
                _error.value = null
            } catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка загрузки предложений: ${e.message}")
                _error.value = e.message
            }
        }
    }
}