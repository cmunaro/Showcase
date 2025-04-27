package com.example.showcase.di

import com.example.showcase.features.mainlist.data.InMemoryMediaStorage
import com.example.showcase.features.mainlist.data.MediaRepositoryImpl
import com.example.showcase.features.mainlist.data.MediaStorage
import com.example.showcase.features.mainlist.data.VegansLabAPI
import com.example.showcase.features.mainlist.data.createVegansLabAPI
import com.example.showcase.features.mainlist.domain.MediaRepository
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::MediaRepositoryImpl) bind MediaRepository::class
    singleOf(::InMemoryMediaStorage) bind MediaStorage::class

    single<VegansLabAPI> {
        Ktorfit.Builder()
            .httpClient(
                HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
            )
            .baseUrl("https://apivegans.veganslab.xyz/")
            .build()
            .createVegansLabAPI()
    }
}