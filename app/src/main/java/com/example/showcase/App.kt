package com.example.showcase

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.showcase.di.dataModule
import com.example.showcase.di.domainModule
import com.example.showcase.di.viewModelModule
import com.example.showcase.ui.theme.ShowcaseTheme
import org.koin.compose.KoinApplication

@Composable
fun App() {
    ShowcaseTheme {
        KoinApplication(
            application = {
                modules(viewModelModule, domainModule, dataModule)
            }
        ) {
            val snackBarHostState = remember { SnackbarHostState() }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                }
            ) { padding ->
                Navigation(
                    snackBarHostState = snackBarHostState,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}