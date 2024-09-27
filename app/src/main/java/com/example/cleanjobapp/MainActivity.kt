package com.example.cleanjobapp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.cleanjobapp.databinding.ActivityMainBinding
import com.example.listvacancy.presentation.ListVacanciesFragment
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
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Настраиваем ActionBar для работы с NavController
        setupActionBarWithNavController(this, navController)

//        binding.bottomNav.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.searchFragment -> loadFragment(ListVacanciesFragment())
////                R.id.favoritesFragment -> loadFragment(Favorites())
////                R.id.responsesFragment -> loadFragment(Responses())
////                R.id.messagesFragment -> loadFragment(Messages())
////                R.id.profileFragment -> loadFragment(Profile())
//            }
//            true
//        }

        // Подписываемся на ошибки и показываем их пользователю
        lifecycleScope.launch(Dispatchers.IO) {
            listVacancyViewModel.loadVacancies()
            listVacancyViewModel.loadOffers()
        }



        //loadFragment(ListVacanciesFragment())
        }
//    private fun loadFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.content, fragment)
//            .commit()
//    }



    }




