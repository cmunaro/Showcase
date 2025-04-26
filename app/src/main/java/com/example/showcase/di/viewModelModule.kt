package com.example.showcase.di

import com.example.showcase.features.mainlist.ui.MainListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainListViewModel)
}