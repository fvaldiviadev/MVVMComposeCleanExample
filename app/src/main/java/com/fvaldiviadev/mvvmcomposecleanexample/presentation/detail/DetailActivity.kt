package com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fvaldiviadev.mvvmcomposecleanexample.AppDependencies
import usecase.GetCharacterDetailUseCase

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos el ID que pasamos en el Intent (por defecto -1 si falla)
        val characterId = intent.getIntExtra("CHARACTER_ID", -1)

        // Usamos el repositorio compartido para que lea de la caché
        val getCharacterDetailUseCase = GetCharacterDetailUseCase(AppDependencies.repository)

        // Creamos la factoría pasándole el caso de uso y el ID
        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(getCharacterDetailUseCase, characterId) as T
            }
        }

        setContent {
            MaterialTheme {
                val viewModel: DetailViewModel = viewModel(factory = factory)

                DetailScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() } // Cierra la Activity y vuelve a la lista
                )
            }
        }
    }
}