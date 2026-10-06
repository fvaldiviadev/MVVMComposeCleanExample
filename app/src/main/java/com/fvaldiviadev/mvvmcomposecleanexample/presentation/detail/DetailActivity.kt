package com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val viewModel: DetailViewModel = hiltViewModel()

                DetailScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() } // Cierra la Activity y vuelve a la lista
                )
            }
        }
    }
}