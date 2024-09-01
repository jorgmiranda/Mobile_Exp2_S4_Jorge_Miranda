package com.example.exp2_s3_jorge_miranda.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.exp2_s3_jorge_miranda.screen.RecuperarScreen
import com.example.exp2_s3_jorge_miranda.ui.theme.Exp2_S3_Jorge_MirandaTheme

class RecuperarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperarScreen()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    RecuperarScreen()
}