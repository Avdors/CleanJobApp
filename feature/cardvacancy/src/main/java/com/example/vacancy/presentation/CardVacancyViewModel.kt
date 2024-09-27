package com.example.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vacancy.domain.model.CardVacancyDomainModel
import com.example.vacancy.domain.usecase.FavoriteCardVacancyUseCase
import com.example.vacancy.domain.usecase.LoadVacancyUseCase
import com.example.vacancy.presentation.mapper.CardVacancyMapperUI
import com.example.vacancy.presentation.model.CardVacancyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardVacancyViewModel(
    private val loadVacancyUseCase: LoadVacancyUseCase,
    private val favoriteUseCase: FavoriteCardVacancyUseCase
): ViewModel() {

    private val _vacancy = MutableStateFlow<CardVacancyModel?>(null)
    val vacancy: StateFlow<CardVacancyModel?> = _vacancy

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private lateinit var currentVacancy: CardVacancyDomainModel

    // Загрузка вакансии по ID
    fun loadVacancy(vacancyId: String) {
        viewModelScope.launch {
            try {
                loadVacancyUseCase.loadVacancyById(vacancyId).collect { vacancyDomain ->
                    currentVacancy = vacancyDomain
                    _vacancy.value = CardVacancyMapperUI.mapToUIModel(currentVacancy)
                    _isFavorite.value = currentVacancy.isFavorite
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    // Переключение состояния избранного
    fun toggleFavorite() {
        viewModelScope.launch {
            currentVacancy?.let { vacancy ->
                _isFavorite.value.let { isFavorite ->
                    val updatedVacancy = vacancy.copy(isFavorite = !isFavorite)
                    favoriteUseCase.togleFavorite(updatedVacancy)
                    _isFavorite.value = updatedVacancy.isFavorite
                    currentVacancy = updatedVacancy
                }
            } ?: run {
                // Обработка случая, когда currentVacancy еще не загружена
                // Лог или сообщение об ошибке
            }
        }
    }
}