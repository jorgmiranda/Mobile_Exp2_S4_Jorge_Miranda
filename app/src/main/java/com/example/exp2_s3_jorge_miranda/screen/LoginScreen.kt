package com.example.exp2_s3_jorge_miranda.screen

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exp2_s3_jorge_miranda.R
import com.example.exp2_s3_jorge_miranda.activity.HomeActivity
import com.example.exp2_s3_jorge_miranda.activity.RecuperarActivity
import com.example.exp2_s3_jorge_miranda.activity.RegistroActivity
import com.example.exp2_s3_jorge_miranda.classes.PreferencesManager
import com.example.exp2_s3_jorge_miranda.classes.Usuario
import com.example.exp2_s3_jorge_miranda.repository.FirebaseService

@Composable
fun LoginScreen(){
    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    ) // Define los colores del fondo para el degradado

    val context = LocalContext.current;
    //Se instancia sharedPreferences
    //val preferencesManager = PreferencesManager(context);
    //Se instancia firebase
    val firestoreService = if (!LocalInspectionMode.current) {
        FirebaseService<Usuario>()
    } else {
        null // se comenta para ver si arregla la vista previa
    }


    //Variaables que almacenan valores y validan errores
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
            modifier = Modifier.size(180.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Bienvenid@",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)

        OutlinedTextField(value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text(text = "Correo")},
            isError = emailError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        if (emailError){
            Text(text = "Correo Invalido",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            label = {Text(text = "Password")},
            isError = passwordError,
            visualTransformation = PasswordVisualTransformation()
        )

        if (passwordError){
            Text(text = "La contraseña no puede estar vacia",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            // Validar el campo de correo
            emailError = email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()

            // Validar el campo de contraseña
            passwordError = password.isEmpty()

            if (!emailError && !passwordError) {
               //val recuperarUsuario = preferencesManager.getObject("usuario_"+email, Usuario::class.java)
                firestoreService?.login(email, password, { usuario ->
                    if (usuario != null) {
                        Toast.makeText(context, "Login exitoso", Toast.LENGTH_SHORT).show()
                        // Navegar a la siguiente pantalla y pasar el usuario

                        val intent = Intent(context, HomeActivity::class.java).apply {
                            putExtra("usuario", usuario)
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }, { error ->
                    Toast.makeText(context, "Error al iniciar sesión: ${error.message}", Toast.LENGTH_SHORT).show()
                })
                /*
                if(recuperarUsuario != null){
                    Toast.makeText(context, "Credenciales Validas!", Toast.LENGTH_SHORT).show()
                    val navigate = Intent(context, HomeActivity::class.java)
                    navigate.putExtra("correoUsuario", recuperarUsuario.correo)
                    context.startActivity(navigate)

                }else{
                    Toast.makeText(context, "Credenciales Invalidas", Toast.LENGTH_LONG).show()
                }
                */

            }

        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF0C6E7A)
            )) {
            Text(text = "Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(text = "Recuperar Contraseña",
            modifier = Modifier.clickable {
               val navegar = Intent(context, RecuperarActivity::class.java)
                context.startActivity(navegar)
            },
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(text = "Registrarse",
            modifier = Modifier.clickable {
                val navegar = Intent(context, RegistroActivity::class.java)
                context.startActivity(navegar)
            },
            fontWeight = FontWeight.SemiBold)


    }
}

@Preview
@Composable
fun VistaPreviaLogin(){
    LoginScreen()
}