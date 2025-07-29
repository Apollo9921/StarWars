package com.example.starwars.networking.instance

import com.example.starwars.BuildConfig
import com.example.starwars.networking.requests.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Singleton object responsible for configuring and providing a Retrofit instance
 * for network operations.
 *
 * This object sets up the base URL, an OkHttpClient with a logging interceptor,
 * and a Moshi converter factory for JSON serialization/deserialization.
 * The Retrofit service (`api`) is lazily initialized.
 */
object Instance {

    // The base URL for all API requests.
    // It's good practice to fetch this from BuildConfig to allow different URLs for
    // different build variants (e.g., debug, release).
    private const val BASE_URL = BuildConfig.BASE_URL

    // Configures an HTTP logging interceptor to log request and response bodies.
    // This is very useful for debugging network calls.
    // The level is set to BODY to see the full request and response.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Configures the OkHttpClient, adding the logging interceptor.
    // OkHttpClient is used by Retrofit for making HTTP requests.
    // You can add other interceptors here for things like authentication, caching, etc.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // Add other interceptors or configurations like timeouts if needed:
        // .connectTimeout(30, TimeUnit.SECONDS)
        // .readTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Lazily initialized Retrofit service instance that implements the [Service] interface.
     *
     * This instance is configured with:
     * - The [BASE_URL].
     * - The custom [okHttpClient] (which includes the logging interceptor).
     * - [MoshiConverterFactory] for parsing JSON responses into Kotlin objects.
     *
     * The `lazy` delegate ensures that the Retrofit instance is created only when it's first accessed,
     * making the setup more efficient if the API isn't used immediately.
     */
    val api: Service by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the configured OkHttpClient.
            .addConverterFactory(MoshiConverterFactory.create()) // Use Moshi for JSON conversion.
            .build()
            .create(Service::class.java) // Create an implementation of the API endpoints defined in Service interface.
    }
}