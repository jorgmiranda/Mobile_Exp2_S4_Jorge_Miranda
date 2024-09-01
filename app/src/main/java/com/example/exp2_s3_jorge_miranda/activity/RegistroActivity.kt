package com.example.exp2_s3_jorge_miranda.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import com.example.exp2_s3_jorge_miranda.screen.RegistroScreen

class RegistroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            RegistroScreen()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VistaPreviaRegistro() {
    RegistroScreen()
}