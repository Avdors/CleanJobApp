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
import com.example.cleanjobapp.databinding.ActivityMainBinding
import com.example.listvacancy.presentation.ListVacanciesFragment
import com.example.listvacancy.presentation.ListVacancyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val listVacancyViewModel: ListVacancyViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> loadFragment(ListVacanciesFragment())
//                R.id.favoritesFragment -> loadFragment(Favorites())
//                R.id.responsesFragment -> loadFragment(Responses())
//                R.id.messagesFragment -> loadFragment(Messages())
//                R.id.profileFragment -> loadFragment(Profile())
            }
            true
        }

        // Подписываемся на ошибки и показываем их пользователю
        lifecycleScope.launch(Dispatchers.IO) {
            listVacancyViewModel.loadVacancies()
            listVacancyViewModel.loadOffers()
        }



        //loadFragment(ListVacanciesFragment())
        }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    }




