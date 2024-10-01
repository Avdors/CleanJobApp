package com.example.favoritevacancy.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.utils.SpacesItemDecoration
import com.example.core.utils.WordDeclension
import com.example.favoritevacancy.presentation.adapter.FavoriteVacancyAdapter
import com.example.favoritevacancy.presentation.model.FavoriteVacancyModel
import com.example.listvacancy.R
import com.example.listvacancy.databinding.FragmentFavoriteVacanciesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteVacanciesFragment : Fragment() {
    private val vacancyViewModel: FavoriteVacancyViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteVacanciesBinding
    private lateinit var vacancyAdapter: FavoriteVacancyAdapter
    private val wordDeclension = WordDeclension()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyRecyclerView = binding.favoritesRecyclerVacancy
        vacancyRecyclerView.layoutManager = LinearLayoutManager(context)

        // расстояние между карточками через ItemDecoration
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dp8)
        vacancyRecyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))

        vacancyAdapter = FavoriteVacancyAdapter(
            emptyList(),
            onVacancyClick = { vacancy ->
                // Переход к CardVacancyFragment
                val vacancyId = vacancy.id
                val isFromFavorites = true // Передаем информацию, что это вызов из избранного
                val deepLinkUri = Uri.parse("app://vacancy.com/vacancy/$vacancyId?fromFavorites=$isFromFavorites")
                findNavController().navigate(deepLinkUri)
            },
            // клик по кнопке избранное
            onFavoriteClick = { vacancy ->
                // jobsViewModel.toggleFavorite(vacancy)
                vacancyViewModel.updateFavorites(vacancy = vacancy)
                vacancyAdapter.updateVacancies(vacancyViewModel.vacancies.value,  vacancyViewModel.vacancies.value.size)
            },

            onApplyClick = { vacancy ->
//                val responseDialog = ResponseDialog()
//                responseDialog.show(requireActivity().supportFragmentManager, "ResponseDialog")
            }
        )
        vacancyRecyclerView.adapter = vacancyAdapter

        // Наблюдаю за списком vacancies
        lifecycleScope.launch {
            vacancyViewModel.vacancies.collect { vacancies ->

                val vacancy = wordDeclension.getVacancyCountString(vacancies.size, requireContext())

                binding.quantityVacancy.text = "$vacancy"

                vacancyAdapter.updateVacancies(vacancies, vacancies.size)

            }
        }
    }


}