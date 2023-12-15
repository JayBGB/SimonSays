package com.example.simondice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.simondice.ui.theme.SimonSays

// La función MainActivity() es la función principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonSays {
                Surface {
                    Greeting(myModel = VModel())
                }

            }
        }
    }



}