package com.app.pocketpal.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.app.pocketpal.presentation.ui.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.pocketpal.di.DiTest
import com.app.pocketpal.presentation.common.PocketPalTopBar
import com.app.pocketpal.presentation.navigation.graph.MainNavRoute
import com.app.pocketpal.presentation.navigation.graph.mainNavigation
import com.app.pocketpal.presentation.ui.theme.CharCoal
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var testData : DiTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDark by remember { mutableStateOf(true) }
            PocketPalTheme(darkTheme = isDark) {
                App(modifier = Modifier
                        .fillMaxSize()
                        .background(color = CharCoal)
                )
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold(modifier = modifier,
        topBar = {
            PocketPalTopBar()
        }
    ) { innerPadding ->
        Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = MainNavRoute){
                mainNavigation(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    PocketPalTheme {
        App()
    }
}