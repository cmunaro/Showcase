package com.example.showcase

import android.content.Context
import com.example.showcase.di.dataModule
import com.example.showcase.di.domainModule
import com.example.showcase.di.viewModelModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class DependencyInjection : KoinTest {
    @Test
    fun `check koin modules`() {
        module {
            includes(
                dataModule,
                domainModule,
                viewModelModule
            )
        }.verify(
            extraTypes = listOf(Context::class)
        )
    }
}