package com.example.listvacancy.presentation

import com.example.core.domain.model.VacancyDomainModel
import com.example.listvacancy.presentation.model.AddressMainFragmentModel
import com.example.listvacancy.presentation.model.ExperienceMainFragmentModel
import com.example.listvacancy.presentation.model.SalaryMainFragmentModel
import com.example.listvacancy.presentation.model.VacancyModel

object VacancyMapper {
    // Функция для маппинга одного объекта VacancyDomainModel в VacancyModel
    fun mapToUIModel(domainModel: VacancyDomainModel): VacancyModel {
        return VacancyModel(
            id = domainModel.id,
            lookingNumber = domainModel.lookingNumber,
            title = domainModel.title,
            address = AddressMainFragmentModel(
                domainModel.address.town,
                domainModel.address.street,
                domainModel.address.house
            ),
            company = domainModel.company,
            experience = ExperienceMainFragmentModel(
                domainModel.experience.previewText,
                domainModel.experience.text
            ),
            publishedDate = domainModel.publishedDate,
            isFavorite = domainModel.isFavorite,
            salary = SalaryMainFragmentModel(
                domainModel.salary.full,
                domainModel.salary.short
            ),
            schedules = domainModel.schedules,
            appliedNumber = domainModel.appliedNumber,
            description = domainModel.description,
            responsibilities = domainModel.responsibilities,
            questions = domainModel.questions
        )
    }

    // Функция для маппинга списка Domain моделей в список UI моделей
    fun mapToUIModelList(domainModels: List<VacancyDomainModel>): List<VacancyModel> {
        return domainModels.map { mapToUIModel(it) }
    }
}