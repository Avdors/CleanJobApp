package com.example.listvacancy.domain.mapper

import com.example.core.data.database.model.ButtonModDataBase
import com.example.core.data.database.model.OfferModelDataBase
import com.example.listvacancy.data.model.FeatListVacancyOffer
import com.example.listvacancy.domain.model.ListButton
import com.example.listvacancy.domain.model.ListOfferDomainModel

class OfferDataMaper {
    fun mapOfferToDomain(offer: FeatListVacancyOffer): ListOfferDomainModel {
        return ListOfferDomainModel(
                id = offer.id ?: "",
                title = offer.title ?: "",
                link = offer.link ?: "",
                button = offer.button?.let { ListButton(it.text.toString()) } // Если button существует, маппим его
        )

    }

    fun mapOfferToDomainFromDB(offer: OfferModelDataBase): ListOfferDomainModel {
        return ListOfferDomainModel(
            id = offer.id,
            title = offer.title,
            link = offer.link,
            button = offer.button?.let { ListButton(it.text) } // Если button существует, маппим его
        )

    }

    fun mapOfferToDatabase(offer: ListOfferDomainModel): OfferModelDataBase {
        return OfferModelDataBase(
            id = offer.id ?: "",
            title = offer.title,
            link = offer.link,
            button = offer.button?.let { ButtonModDataBase(it.text) }
        )
    }


}