package com.example.core.domain.usecase


import com.example.core.domain.model.AddressDomainModel
import com.example.core.domain.model.ExperienceDomainModel
import com.example.core.domain.model.SalaryDomainModel
import com.example.core.domain.model.VacancyDomainModel
import com.example.core.domain.repository.VacancyRepository

class GetMappedVacanciesUseCase(private val vacancyRepository: VacancyRepository) {


    suspend operator fun invoke(): List<VacancyDomainModel> {
        val response = vacancyRepository.getVacancies()

        // Собираем все вакансии из всех responseData в один список и мапим их

        return response.vacancies?.filterNotNull()?.map { vacancy ->
                VacancyDomainModel(
                    id = vacancy.id ?: "",
                    lookingNumber = vacancy.lookingNumber ?: 0,
                    title = vacancy.title ?: "",
                    address = AddressDomainModel(
                        vacancy.address?.town ?: "",
                        vacancy.address?.street ?: "",
                        vacancy.address?.house ?: ""
                    ),
                    company = vacancy.company ?: "",
                    experience = ExperienceDomainModel(
                        vacancy.experience?.previewText ?: "",
                        vacancy.experience?.text ?: ""
                    ),
                    publishedDate = vacancy.publishedDate ?: "",
                    isFavorite = vacancy.isFavorite ?: false,
                    salary = SalaryDomainModel(
                        vacancy.salary?.full ?: "",
                        vacancy.salary?.short ?: ""
                    ),
                    schedules = vacancy.schedules.orEmpty().filterNotNull(),
                    appliedNumber = vacancy.appliedNumber ?: 0,
                    description = vacancy.description ?: "",
                    responsibilities = vacancy.responsibilities ?: "",
                    questions = vacancy.questions.orEmpty().filterNotNull()
                )
            } ?: emptyList()

    }
}