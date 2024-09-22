package com.example.listvacancy.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.utils.WordDeclension
import com.example.listvacancy.R
import com.example.listvacancy.databinding.ItemShowMoreButtonBinding
import com.example.listvacancy.databinding.VacancyItemBinding
import com.example.listvacancy.presentation.model.VacancyModel

class ListVacancyAdapter  (private var vacancies: List<VacancyModel>,
                           private val onVacancyClick: (VacancyModel) -> Unit,
                           private val onFavoriteClick: (VacancyModel) -> Unit,
                           private val onShowMoreClick: () -> Unit, // Кнопка "Еще вакансий"
                           private val onApplyClick: (VacancyModel) -> Unit // Кнопка "Откликнуться"
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_VACANCY = 0
    private val ITEM_TYPE_SHOW_MORE = 1

    // состояние списка вакансий
    private var isFullListDisplayed = false

    // общий список вакансий
    private var totalVacanciesCount: Int = vacancies.size


    // ViewHolder для вакансий
    class VacancyViewHolder(val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val peopleTextView: TextView? = itemView.findViewById(R.id.item_peopl_count)
//        private val titleTextView: TextView? = itemView.findViewById(R.id.item_vacancy_title)
//        private val salaryTextView: TextView? = itemView.findViewById(R.id.item_vacancy_salary)
//        private val townTextView: TextView? = itemView.findViewById(R.id.item_vacancy_adress)
//        private val companyTextView: TextView? = itemView.findViewById(R.id.item_vacancy_company)
//        private val experienceTextView: TextView? = itemView.findViewById(R.id.item_experience)
//        private val publishedDateTextView: TextView? = itemView.findViewById(R.id.item_date)
//        private val favoriteImageView: ImageView = itemView.findViewById(R.id.search_like_bttn)
//        private val buttonrespons: Button = itemView.findViewById(R.id.respons_button)
        private val wordDeclension = WordDeclension()

        fun bind(
            vacancy: VacancyModel,
            onVacancyClick: (VacancyModel) -> Unit,
            onFavoriteClick: (VacancyModel) -> Unit,
            onApplyClick: (VacancyModel) -> Unit
        ) {
            if (vacancy.lookingNumber > 0) {
                val human = wordDeclension.getHumanCountString(vacancy.lookingNumber.toInt(), itemView.context)
                binding.itemPeoplCount.text = "Сейчас просматривают $human"
                binding.itemPeoplCount.visibility = View.VISIBLE
            } else {
                binding.itemPeoplCount.visibility = View.GONE
            }

            binding.itemVacancyTitle.text = vacancy.title
            binding.itemVacancySalary.text = vacancy.salary.full
            binding.itemVacancyAdress.text = vacancy.address.town
            binding.itemVacancyCompany.text = vacancy.company
            binding.itemExperience.text = vacancy.experience.previewText
            binding.itemDate.text = "Опубликовано ${vacancy.publishedDate}"


            binding.searchLikeBttn.setImageResource(
                if (vacancy.isFavorite) R.drawable.fill_heart_icon else R.drawable.heart_icon
            )
            // Обработка нажатия на весь элемент
            itemView.setOnClickListener { onVacancyClick(vacancy) }
            binding.searchLikeBttn.setOnClickListener { onFavoriteClick(vacancy) }
            binding.responsButton.setOnClickListener { onApplyClick(vacancy) } // Обработка нажатия на кнопку "Откликнуться"
        }
    }

    // ViewHolder для кнопки "Еще вакансий"
    class ShowMoreViewHolder(val binding: ItemShowMoreButtonBinding) : RecyclerView.ViewHolder(binding.root) {
       // private val showMoreButton: Button = itemView.findViewById(R.id.more_vacancies_button)
        private val wordDeclension = WordDeclension()
        fun bind(onShowMoreClick: () -> Unit, remainingCount: Int) {

            val vacancy = wordDeclension.getVacancyCountString(remainingCount, itemView.context)
            binding.moreVacanciesButton.text = "Еще $vacancy"
            binding.moreVacanciesButton.setOnClickListener { onShowMoreClick() }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position < 3 || isFullListDisplayed) ITEM_TYPE_VACANCY else ITEM_TYPE_SHOW_MORE

    }

    fun updateVacancies(
        allVacancies: List<VacancyModel>,
        showFullList: Boolean,
        totalVacanciesCount: Int
    ) {
        Log.d("ListVacancyAdapter", "Updating vacancies: ${allVacancies.size}")
        isFullListDisplayed = showFullList
        this.totalVacanciesCount = totalVacanciesCount
        this.vacancies = allVacancies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_VACANCY) {
//            val view =
//                LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
            val binding: VacancyItemBinding = VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VacancyViewHolder(binding)
        } else {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_show_more_button, parent, false)
            val binding: ItemShowMoreButtonBinding = ItemShowMoreButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ShowMoreViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        // Возвращаю размер списка + 1, чтобы показать кнопку "Еще вакансий"
        return if (isFullListDisplayed) vacancies.size else 4
        // return if (vacancies.size >= 3) 4 else vacancies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_TYPE_VACANCY) {
            val vacancy = vacancies[position]
            Log.d("VacancyAdapter", "Binding vacancy at position: $position")
            (holder as VacancyViewHolder).bind(
                vacancy,
                onVacancyClick,
                onFavoriteClick,
                onApplyClick
            )
        } else {
            Log.d("VacancyAdapter", "Binding show more button at position: $position")

            val remainingVacancies = totalVacanciesCount - 3
            (holder as ShowMoreViewHolder).bind(onShowMoreClick, remainingVacancies)
        }
    }
}