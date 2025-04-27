package com.example.showcase.di

import com.example.showcase.features.mainlist.domain.GetListUseCase
import com.example.showcase.features.mainlist.domain.GetListUseCaseImpl
import com.example.showcase.features.mediadetails.domain.GetMediaUseCase
import com.example.showcase.features.mediadetails.domain.GetMediaUseCaseImpl
import com.example.showcase.features.mediadetails.domain.LoadMediaImageUseCase
import com.example.showcase.features.mediadetails.domain.LoadMediaImageUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetListUseCaseImpl) bind GetListUseCase::class
    factoryOf(::GetMediaUseCaseImpl) bind GetMediaUseCase::class
    factoryOf(::LoadMediaImageUseCaseImpl) bind LoadMediaImageUseCase::class
}