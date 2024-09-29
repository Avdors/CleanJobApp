package com.example.cleanjobapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cleanjobapp.databinding.ActivityMainBinding
import com.example.listvacancy.presentation.ListVacancyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val listVacancyViewModel: ListVacancyViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Устанавливаем Toolbar как ActionBar
        setSupportActionBar(binding.toolbar)

        // Подключаем NavHostFragment к MainActivity

        val navController = findNavController(R.id.nav_host_fragment)

        // Настраиваем ActionBar для работы с NavController
        binding.bottomNav.setupWithNavController(navController)

        // Подписываемся на обработку кликов по элементам меню
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.listVacanciesFragment -> navController.navigate(R.id.listVacanciesFragment)
                R.id.favoriteVacanciesFragment -> navController.navigate(R.id.favoriteVacanciesFragment)
                else -> navController.navigate(R.id.inProgressFragment) // Для всех остальных пунктов
            }
            true
        }

        // Загрузка данных
        lifecycleScope.launch() {
            listVacancyViewModel.loadVacancies()
            listVacancyViewModel.loadOffers()
        }


        }

    }




