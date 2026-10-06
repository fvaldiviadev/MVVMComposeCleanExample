package com.fvaldiviadev.mvvmcomposecleanexample.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // Escuchamos el evento exacto de ON_RESUME
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadCharacters()
            }
        }

        // Añadimos el observador
        lifecycleOwner.lifecycle.addObserver(observer)

        // Lo limpiamos cuando la pantalla se destruya para evitar fugas de memoria
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Aquí observamos el estado. Cada vez que cambie, esta función se vuelve a ejecutar (Recomposition)
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Personajes Rick & Morty") }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.characters) { character ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigateToDetail(character.id) }
                                    .padding(16.dp)
                            ) {
                                AsyncImage(
                                    model = character.image,
                                    contentDescription = "Imagen de ${character.name}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(56.dp) // Tamaño de la imagen
                                        .clip(CircleShape) // Forma circular
                                )

                                // Espacio entre la imagen y el texto
                                Spacer(modifier = Modifier.width(16.dp))

                                // Nombre del personaje
                                Text(
                                    text = character.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                             }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}