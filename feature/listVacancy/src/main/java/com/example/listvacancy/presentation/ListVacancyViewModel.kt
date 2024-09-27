package com.example.listvacancy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetMappedVacanciesUseCase
import com.example.listvacancy.domain.usecase.FavoriteUseCase
import com.example.listvacancy.domain.usecase.ListVacancyUseCase
import com.example.listvacancy.presentation.model.ButtonModel
import com.example.listvacancy.presentation.model.OfferModel
import com.example.listvacancy.presentation.model.VacancyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListVacancyViewModel(
    private val listVacancyUseCase: ListVacancyUseCase,
    private val favoriteUseCase: FavoriteUseCase
): ViewModel() {

    private val _vacancies = MutableStateFlow<List<VacancyModel>>(emptyList())
    val vacancies: StateFlow<List<VacancyModel>> = _vacancies

    private val _offers = MutableStateFlow<List<OfferModel>>(emptyList())
    val offers: StateFlow<List<OfferModel>> = _offers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            loadVacancies()
            loadOffers()
        }
    }

    suspend fun loadVacancies() {
        viewModelScope.launch {
            try {
                // Получаем список Domain моделей через use case
                val domainVacancies = listVacancyUseCase.getVacancies()
                Log.d("ListVacancyViewModel", "domainVacancies: ${domainVacancies.size}")
                // Маппим Domain модели в UI модели
                val uiVacancies = VacancyMapper.mapToUIModelList(domainVacancies)
                _vacancies.value = uiVacancies

                // Логирование данных вакансий
                uiVacancies.forEach { vacancy ->
                    Log.d("ListVacancyViewModel", "Vacancy: ${vacancy.title}")
                }
            } catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка загрузки вакансий: ${e.message}")
                _error.value = e.message
            }
        }
    }

    suspend fun loadOffers() {
        viewModelScope.launch {
            try {
                val domainOffers = listVacancyUseCase.getOffers()
                Log.d("ListVacancyViewModel", "domainOffers: ${domainOffers.size}")
                val uiOffers = domainOffers.map { domainModel ->
                    OfferModel(
                        id = domainModel.id ?: "",
                        title = domainModel.title,
                        link = domainModel.link,
                        button = domainModel.button?.let { ButtonModel(it.text) }
                    )
                }
                _offers.value = uiOffers
            } catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка загрузки предложений: ${e.message}")
                _error.value = e.message
            }
        }
    }

    // Добавление вакансии в избранное

    fun updateFavorites(vacancy: VacancyModel){
        if (vacancy.isFavorite) {
            removeFromFavorites(vacancy)
        } else{
            addToFavorites(vacancy)
        }
    }
    fun addToFavorites(vacancy: VacancyModel) {
        viewModelScope.launch {
            try {
                val domainModel = VacancyMapper.mapToDomainModel(vacancy)
                favoriteUseCase.addToFavorites(domainModel)
                updateFavoriteStatus(vacancy.id, true)
            } catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка добавления в избранное: ${e.message}")
            }
        }
    }

    // Удаление вакансии из избранного
    fun removeFromFavorites(vacancy: VacancyModel) {
        viewModelScope.launch {
            try {
                val domainModel = VacancyMapper.mapToDomainModel(vacancy)
                favoriteUseCase.removeFromFavorites(domainModel)
                updateFavoriteStatus(vacancy.id, false)
            } catch (e: Exception) {
                Log.e("ListVacancyViewModel", "Ошибка удаления из избранного: ${e.message}")
            }
        }
    }

    // Обновление статуса избранного
    private fun updateFavoriteStatus(vacancyId: String, isFavorite: Boolean) {
        val updatedVacancies = _vacancies.value.map { vacancy ->
            if (vacancy.id == vacancyId) {
                vacancy.copy(isFavorite = isFavorite)
            } else {
                vacancy
            }
        }
        _vacancies.value = updatedVacancies
    }
}