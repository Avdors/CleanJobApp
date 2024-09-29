package com.example.favoritevacancy.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritevacancy.domain.usecase.FavoriteVacanciesUseCase
import com.example.favoritevacancy.presentation.mapper.FavoriteVacancyMapper
import com.example.favoritevacancy.presentation.model.FavoriteVacancyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteVacancyViewModel(
    private val favoriteUseCase: FavoriteVacanciesUseCase
): ViewModel()  {

    private val _vacancies = MutableStateFlow<List<FavoriteVacancyModel>>(emptyList())
    val vacancies: StateFlow<List<FavoriteVacancyModel>> = _vacancies

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // Подписываемся на поток данных вакансий
        viewModelScope.launch {
            favoriteUseCase.getListVacancies()
                .catch { e ->
                    _error.value = "Ошибка загрузки вакансий: ${e.message}"
                }
                .collect { domainVacancies ->
                    val uiVacancies = FavoriteVacancyMapper.mapToUIModelList(domainVacancies)
                    _vacancies.value = uiVacancies
                }
        }
    }

    fun updateFavorites(vacancy: FavoriteVacancyModel) {
        if (vacancy.isFavorite) {
            removeFromFavorites(vacancy)
        } else {
            addToFavorites(vacancy)
        }
    }

    private fun addToFavorites(vacancy: FavoriteVacancyModel) {
        viewModelScope.launch {
            try {
                val domainModel = FavoriteVacancyMapper.mapToDomainModel(vacancy)
                favoriteUseCase.addToFavorites(domainModel)
                updateFavoriteStatus(vacancy.id, true)
            } catch (e: Exception) {
                _error.value = "Ошибка добавления в избранное: ${e.message}"
            }
        }
    }

    private fun removeFromFavorites(vacancy: FavoriteVacancyModel) {
        viewModelScope.launch {
            try {
                val domainModel = FavoriteVacancyMapper.mapToDomainModel(vacancy)
                favoriteUseCase.removeFromFavorites(domainModel)
                updateFavoriteStatus(vacancy.id, false)
            } catch (e: Exception) {
                _error.value = "Ошибка удаления из избранного: ${e.message}"
            }
        }
    }

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