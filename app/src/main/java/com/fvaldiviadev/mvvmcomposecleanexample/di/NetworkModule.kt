package com.fvaldiviadev.mvvmcomposecleanexample.di

import com.fvaldiviadev.domain.repository.CharacterRepository
import android.util.Log
import com.fvaldiviadev.data.remote.Api
import com.fvaldiviadev.data.repository.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.fvaldiviadev.domain.usecase.GetCharacterDetailUseCase
import com.fvaldiviadev.domain.usecase.GetCharactersUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Le dice a Hilt que estos objetos vivirán siempre
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val customLogger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith("{") && !message.startsWith("[")) {
                    Log.d("API_NETWORK", message)
                    return
                }
                try {
                    val prettyPrintJson = if (message.startsWith("{")) {
                        JSONObject(message).toString(4)
                    } else {
                        JSONArray(message).toString(4)
                    }
                    Log.d("API_NETWORK", "\n$prettyPrintJson")
                } catch (e: Exception) {
                    Log.d("API_NETWORK", message)
                }
            }
        }

        val loggingInterceptor = HttpLoggingInterceptor(customLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 2. Hilt inyecta automáticamente el okHttpClient aquí
    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): Api {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient) // <-- Añadimos el cliente a Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api): CharacterRepository {
        return CharacterRepositoryImpl(api)
    }

    @Provides
    fun provideGetCharactersUseCase(repository: CharacterRepository): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

    @Provides
    fun provideGetCharacterDetailUseCase(repository: CharacterRepository): GetCharacterDetailUseCase {
        return GetCharacterDetailUseCase(repository)
    }
}
