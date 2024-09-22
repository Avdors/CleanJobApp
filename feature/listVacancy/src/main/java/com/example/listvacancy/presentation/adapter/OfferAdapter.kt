package com.example.listvacancy.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listvacancy.R
import com.example.listvacancy.databinding.OfferItemBinding
import com.example.listvacancy.presentation.model.OfferModel

// адаптер для загрузки списка offers
class OfferAdapter(
    private var offers: List<OfferModel>,
    private val onItemClick: (String) -> Unit
): RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {


    class OfferViewHolder(val binding: OfferItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titleTextView: TextView = itemView.findViewById(R.id.offer_title)
        private val buttonTextView: TextView = itemView.findViewById(R.id.offers_action_tv)
        private val iconImageView: ImageView = itemView.findViewById(R.id.recommendation_icon)

        fun bind(offer: OfferModel, onItemClick: (String) -> Unit) {
            // Устанавливаю иконки в зависимости от id
            val iconResId = when (offer.id) {
                "near_vacancies" -> R.drawable.near_map_icon
                "level_up_resume" -> R.drawable.level_up_resume_icon
                "temporary_job" -> R.drawable.temporary_job_icon
                else -> null
            }

            if (iconResId != null) {
                binding.recommendationIcon.setImageResource(iconResId)
                binding.recommendationIcon.visibility = View.VISIBLE
            } else {
                binding.recommendationIcon.visibility = View.GONE
            }


            binding.offerTitle.text = offer.title
            binding.offerTitle.maxLines = if (offer.button != null) 2 else 3

            // Установливаю текст кнопки
            if (offer.button != null) {
                binding.offersActionTv.text = offer.button.text
                binding.offersActionTv.visibility = View.VISIBLE
            } else {
                binding.offersActionTv.visibility = View.GONE
            }

            // Обработка нажатия на весь элемент
            itemView.setOnClickListener {
                onItemClick(offer.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.offer_item, parent, false)
        val binding: OfferItemBinding = OfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position], onItemClick)
    }

    override fun getItemCount(): Int = offers.size


    fun updateOffers(newOffers: List<OfferModel>) {
        Log.d("OfferAdapter", "Updating offers: ${offers.size}")
        this.offers = newOffers
        notifyDataSetChanged()
    }
}