package com.example.listvacancy.presentation

class VacancyModel(
    private val id: String,
    private val lookingNumber: Int,
    private val title: String,
    private val address: AddressMainFragmentModel,
    private val company: String,
    private val experience: ExperienceMainFragmentModel,
    private val publishedDate: String,
    private val isFavorite: Boolean,
    private val salary: SalaryMainFragmentModel,
    private val schedules: List<String>,
    private val appliedNumber: Int,
    private val description: String,
    private val responsibilities: String,
    private val questions: List<String>
){

}

class AddressMainFragmentModel(val town: String, val street: String, val house: String)
class ExperienceMainFragmentModel(val previewText: String, val text: String)
class SalaryMainFragmentModel(val full: String, val short: String)