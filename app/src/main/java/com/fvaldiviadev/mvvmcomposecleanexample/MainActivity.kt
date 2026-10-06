package com.fvaldiviadev.mvvmcomposecleanexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fvaldiviadev.data.remote.Api
import com.fvaldiviadev.data.repository.CharacterRepositoryImpl
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail.DetailActivity
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.main.MainScreen
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import usecase.GetCharactersUseCase
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Construimos las dependencias manualmente (Instancias únicas)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)
        val repository = CharacterRepositoryImpl(api)
        val getCharactersUseCase = GetCharactersUseCase(AppDependencies.repository)

        // 2. Creamos una Factory para poder pasarle el caso de uso al ViewModel
        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(getCharactersUseCase) as T
            }
        }

        setContent {
            MaterialTheme {
                // Pedimos el ViewModel pasándole nuestra Factory
                val viewModel: MainViewModel = viewModel(factory = factory)

                MainScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { id ->
                        val intent = Intent(this, DetailActivity::class.java).apply {
                            putExtra("CHARACTER_ID", id)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}