package com.example.starwars

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.starwars.core.StarWarsTheme
import com.example.starwars.navigation.BasicNavigation

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        actionBar?.hide()
        setContent {
            StarWarsTheme(dynamicColor = false) {
                BasicNavigation()
            }
        }
    }
}