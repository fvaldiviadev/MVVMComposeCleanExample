package com.fvaldiviadev.mvvmcomposecleanexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail.DetailActivity
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.main.MainScreen
import com.fvaldiviadev.mvvmcomposecleanexample.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                //Hilt crea el viewModel con todas sus dependencias
                val viewModel: MainViewModel = hiltViewModel()

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