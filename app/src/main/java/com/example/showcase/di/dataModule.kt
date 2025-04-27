package com.example.showcase.di

import com.example.showcase.features.mainlist.domain.MediaRepository
import com.example.showcase.features.shared.data.InMemoryMediaStorage
import com.example.showcase.features.shared.data.MediaRepositoryImpl
import com.example.showcase.features.shared.data.MediaStorage
import com.example.showcase.features.shared.data.PdfToImageService
import com.example.showcase.features.shared.data.PdfToImageServiceImpl
import com.example.showcase.features.shared.data.TempFileManager
import com.example.showcase.features.shared.data.TempFileManagerImpl
import com.example.showcase.features.shared.data.VegansLabAPI
import com.example.showcase.features.shared.data.createVegansLabAPI
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
    singleOf(::PdfToImageServiceImpl) bind PdfToImageService::class
    singleOf(::TempFileManagerImpl) bind TempFileManager::class

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