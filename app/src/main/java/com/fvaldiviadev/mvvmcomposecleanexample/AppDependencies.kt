package com.fvaldiviadev.mvvmcomposecleanexample

import CharacterRepository
import android.util.Log
import com.fvaldiviadev.data.remote.Api
import com.fvaldiviadev.data.repository.CharacterRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppDependencies {

    // 1. Creamos un Logger personalizado
    private val customLogger = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            // Comprobamos si el mensaje tiene pinta de ser un JSON (empieza por { o [ )
            if (!message.startsWith("{") && !message.startsWith("[")) {
                Log.d("API_NETWORK", message)
                return
            }
            try {
                // Formateamos el JSON con una indentación de 4 espacios
                val prettyPrintJson = if (message.startsWith("{")) {
                    JSONObject(message).toString(4)
                } else {
                    JSONArray(message).toString(4)
                }
                Log.d("API_NETWORK", "\n$prettyPrintJson")
            } catch (e: Exception) {
                // Si falla el formateo, lo imprimimos normal
                Log.d("API_NETWORK", message)
            }
        }
    }

    // 2. Le pasamos nuestro customLogger al interceptor
    private val loggingInterceptor = HttpLoggingInterceptor(customLogger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 3. El resto se queda exactamente igual
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    val repository: CharacterRepository = CharacterRepositoryImpl(api)
}