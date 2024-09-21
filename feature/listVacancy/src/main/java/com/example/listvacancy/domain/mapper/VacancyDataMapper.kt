package com.example.listvacancy.domain.mapper

import com.example.core.data.database.model.AddressFModelDataBase
import com.example.core.data.database.model.AddressModelDataBase
import com.example.core.data.database.model.ExperienceFModelDataBase
import com.example.core.data.database.model.ExperienceModelDataBase
import com.example.core.data.database.model.FavoriteVacModelDataBase
import com.example.core.data.database.model.SalaryFModelDataBase
import com.example.core.data.database.model.SalaryModelDataBase
import com.example.core.data.database.model.VacancyModelDataBase
import com.example.listvacancy.data.model.FeatListVacancy
import com.example.listvacancy.domain.model.ListAddressDomainModel
import com.example.listvacancy.domain.model.ListExperienceDomainModel
import com.example.listvacancy.domain.model.ListSalaryDomainModel
import com.example.listvacancy.domain.model.ListVacancyDomainModel

class VacancyDataMapper {
    fun mapToDomain(vacancy: FeatListVacancy): ListVacancyDomainModel {
        return ListVacancyDomainModel(
            id = vacancy.id ?: "",
            lookingNumber = vacancy.lookingNumber ?: 0,
            title = vacancy.title ?: "",
            address = ListAddressDomainModel(
                vacancy.address?.town ?: "",
                vacancy.address?.street ?: "",
                vacancy.address?.house ?: ""
            ),
            company = vacancy.company ?: "",
            experience = ListExperienceDomainModel(
                vacancy.experience?.previewText ?: "",
                vacancy.experience?.text ?: ""
            ),
            publishedDate = vacancy.publishedDate ?: "",
            isFavorite = vacancy.isFavorite ?: false,
            salary = ListSalaryDomainModel(
                vacancy.salary?.full ?: "",
                vacancy.salary?.short ?: ""
            ),
            schedules = vacancy.schedules.orEmpty().filterNotNull(),
            appliedNumber = vacancy.appliedNumber ?: 0,
            description = vacancy.description ?: "",
            responsibilities = vacancy.responsibilities ?: "",
            questions = vacancy.questions.orEmpty().filterNotNull()
        )
    }

    fun mapToDomainFromDB(vacancy: VacancyModelDataBase): ListVacancyDomainModel {
        return ListVacancyDomainModel(
            id = vacancy.id ?: "",
            lookingNumber = vacancy.lookingNumber ?: 0,
            title = vacancy.title ?: "",
            address = ListAddressDomainModel(
                vacancy.address.town,
                vacancy.address.street,
                vacancy.address.house
            ),
            company = vacancy.company,
            experience = ListExperienceDomainModel(
                vacancy.experience.previewText ,
                vacancy.experience.text
            ),
            publishedDate = vacancy.publishedDate,
            isFavorite = vacancy.isFavorite,
            salary = ListSalaryDomainModel(
                vacancy.salary.full,
                vacancy.salary.short ?: ""
            ),
            schedules = vacancy.schedules,
            appliedNumber = vacancy.appliedNumber ?: 0,
            description = vacancy.description ?: "",
            responsibilities = vacancy.responsibilities ?: "",
            questions = vacancy.questions.orEmpty()
        )
    }

    // Маппинг доменной модели в модель базы данных
    fun mapToDatabase(vacancy: ListVacancyDomainModel): VacancyModelDataBase {
        return VacancyModelDataBase(
            id = vacancy.id,
            lookingNumber = vacancy.lookingNumber,
            title = vacancy.title,
            address = AddressModelDataBase(vacancy.address.town, vacancy.address.street, vacancy.address.house),
            company = vacancy.company,
            experience = ExperienceModelDataBase(vacancy.experience.previewText, vacancy.experience.text),
            publishedDate = vacancy.publishedDate,
            isFavorite = vacancy.isFavorite,
            salary = SalaryModelDataBase(vacancy.salary.full, vacancy.salary.short),
            schedules = vacancy.schedules,
            appliedNumber = vacancy.appliedNumber,
            description = vacancy.description,
            responsibilities = vacancy.responsibilities,
            questions = vacancy.questions
        )
    }

    fun mapFavoriteToDatabase(vacancy: ListVacancyDomainModel): FavoriteVacModelDataBase {
        return FavoriteVacModelDataBase(
            id = vacancy.id,
            lookingNumber = vacancy.lookingNumber,
            title = vacancy.title,
            address = AddressFModelDataBase(vacancy.address.town, vacancy.address.street, vacancy.address.house),
            company = vacancy.company,
            experience = ExperienceFModelDataBase(vacancy.experience.previewText, vacancy.experience.text),
            publishedDate = vacancy.publishedDate,
            isFavorite = vacancy.isFavorite,
            salary = SalaryFModelDataBase(vacancy.salary.full, vacancy.salary.short),
            schedules = vacancy.schedules,
            appliedNumber = vacancy.appliedNumber,
            description = vacancy.description,
            responsibilities = vacancy.responsibilities,
            questions = vacancy.questions
        )
    }
}