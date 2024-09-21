package com.example.listvacancy.presentation

import com.example.core.domain.model.VacancyDomainModel
import com.example.listvacancy.domain.model.ListAddressDomainModel
import com.example.listvacancy.domain.model.ListExperienceDomainModel
import com.example.listvacancy.domain.model.ListSalaryDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel
import com.example.listvacancy.presentation.model.AddressMainFragmentModel
import com.example.listvacancy.presentation.model.ExperienceMainFragmentModel
import com.example.listvacancy.presentation.model.SalaryMainFragmentModel
import com.example.listvacancy.presentation.model.VacancyModel

object VacancyMapper {
    fun mapToUIModel(domainModel: ListVacancyDomainModel): VacancyModel {
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

    fun mapToUIModelList(domainModels: List<ListVacancyDomainModel>): List<VacancyModel> {
        return domainModels.map { mapToUIModel(it) }
    }

    // Маппинг из UI-модели в доменную модель для работы с use cases
    fun mapToDomainModel(uiModel: VacancyModel): ListVacancyDomainModel {
        return ListVacancyDomainModel(
            id = uiModel.id,
            lookingNumber = uiModel.lookingNumber,
            title = uiModel.title,
            address = ListAddressDomainModel(
                uiModel.address.town,
                uiModel.address.street,
                uiModel.address.house
            ),
            company = uiModel.company,
            experience = ListExperienceDomainModel(
                uiModel.experience.previewText,
                uiModel.experience.text
            ),
            publishedDate = uiModel.publishedDate,
            isFavorite = uiModel.isFavorite,
            salary = ListSalaryDomainModel(
                uiModel.salary.full,
                uiModel.salary.short
            ),
            schedules = uiModel.schedules,
            appliedNumber = uiModel.appliedNumber,
            description = uiModel.description,
            responsibilities = uiModel.responsibilities,
            questions = uiModel.questions
        )
    }
}