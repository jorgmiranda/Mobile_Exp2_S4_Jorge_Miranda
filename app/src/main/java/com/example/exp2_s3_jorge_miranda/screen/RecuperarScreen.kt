package com.example.exp2_s3_jorge_miranda.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exp2_s3_jorge_miranda.R
import com.example.exp2_s3_jorge_miranda.activity.HomeActivity
import com.example.exp2_s3_jorge_miranda.activity.MainActivity
import com.example.exp2_s3_jorge_miranda.classes.PreferencesManager
import com.example.exp2_s3_jorge_miranda.classes.Usuario

@Composable
fun RecuperarScreen(){

    val grandientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )
    var email by remember { mutableStateOf("") }

    val context = LocalContext.current;
    //Se instancia sharedPreferences
    val preferencesManager = PreferencesManager(context);

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(grandientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.contrasena), contentDescription = "Recuperar contraseña" )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = { Text(text = "Correo")})

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val recuperarUsuario = preferencesManager.getObject("usuario_"+email, Usuario::class.java)
            if(recuperarUsuario != null){
                Toast.makeText(context, recuperarUsuario.verContrasena(), Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "El usuario no existe!", Toast.LENGTH_LONG).show()
            }
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF0C6E7A)
            )
        ) {
            Text(text = "Recuperar contraseña")
            
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = "Volver",
                modifier = Modifier.clickable {
                    val navegar = Intent(context, MainActivity::class.java)
                    context.startActivity(navegar)
                },
                fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun previewRecuperarScreen(){
    RecuperarScreen()
}