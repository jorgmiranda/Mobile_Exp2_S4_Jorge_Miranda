package com.example.exp2_s3_jorge_miranda.screen

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exp2_s3_jorge_miranda.activity.MainActivity
import com.example.exp2_s3_jorge_miranda.classes.PreferencesManager
import com.example.exp2_s3_jorge_miranda.classes.Usuario


@Composable
fun RegistroScreen(){

    var nombre by remember {      mutableStateOf("")    }
    var apellidos by remember {      mutableStateOf("")    }
    var correo by remember {      mutableStateOf("")    }
    var password by remember {      mutableStateOf("")    }

    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    ) // Define tus colores de degradado

    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        //Nombre
        OutlinedTextField(value = nombre, onValueChange = {
            nombre = it
        }, label = { Text(text = "Nombres") })

        Spacer(modifier = Modifier.height(5.dp))
        //Apellidos
        OutlinedTextField(value = apellidos, onValueChange = {
            apellidos = it
        }, label = { Text(text = "Apellidos") })


        Spacer(modifier = Modifier.height(5.dp))
        //Correo
        OutlinedTextField(value = correo, onValueChange = {
            correo = it
        }, label = { Text(text = "Correos") })


        Spacer(modifier = Modifier.height(5.dp))
        //contraseña
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = { Text(text = "Contraseña")
        }, visualTransformation =  PasswordVisualTransformation())

        Button(
            onClick = {
                //Se valida que el usuario no existe
                val temp = preferencesManager.getObject("usuario_"+correo,Usuario::class.java)
                if(temp != null){
                    // Crea un objeto Usuario con los datos ingresados
                    val nuevoUsuario = Usuario(nombre, apellidos, correo, password)
                    preferencesManager.saveObject("usuario_"+correo, nuevoUsuario)

                    // Limpia los campos de texto después de guardar
                    nombre = ""
                    apellidos = ""
                    correo = ""
                    password = ""

                    Toast.makeText(context, "¡Usuario Registrado!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Ya existe un usuario con este correo", Toast.LENGTH_LONG).show()
                }

            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =Color(0xFF0C6E7A),
                contentColor = Color.White
            )
        ) {
            Text(text = "Crear Cuenta")
        }

        Spacer(modifier = Modifier.height(25.dp))

        //olvide mi contrseña y registrarse
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.SpaceEvenly

        ) {

            Text(text = "Volver",
                modifier = Modifier.clickable {
                    val navigate = Intent(context, MainActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold
            )

        }
    }
}

@Preview
@Composable
fun vistaPreviaRegistro(){


    RegistroScreen()
}