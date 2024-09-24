package com.example.exp2_s3_jorge_miranda.screen

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exp2_s3_jorge_miranda.activity.HomeActivity
import com.example.exp2_s3_jorge_miranda.activity.MainActivity
import com.example.exp2_s3_jorge_miranda.classes.PreferencesManager
import com.example.exp2_s3_jorge_miranda.classes.Usuario
import com.example.exp2_s3_jorge_miranda.repository.FirebaseService


@Composable
fun DetallesUsuarioScreen(usuario:Usuario?) {
    var showDialog by remember { mutableStateOf(false) } // Estado para controlar si se muestra el diálogo

    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    ) // Define tus colores de degradado

    //Inicialización de valores
    // Inicializa los valores de los campos de texto
    var id by remember { mutableStateOf(usuario?.id ?: "") }
    var nombre by remember { mutableStateOf(usuario?.nombres ?: "") }
    var apellidos by remember { mutableStateOf(usuario?.apellidos ?: "") }
    var correo by remember { mutableStateOf(usuario?.correo ?: "") }
    var password by remember { mutableStateOf(usuario?.contrasena ?: "") }

    val context = LocalContext.current

    // Evita inicializar Firebase en vista previa
    val firestoreService = if (!LocalInspectionMode.current) {
        FirebaseService<Usuario>()
    } else {
        null // se comenta para ver si arregla la vista previa
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "id: "+id,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)

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

        /*
        Spacer(modifier = Modifier.height(5.dp))
        //Correo
        OutlinedTextField(value = correo, onValueChange = {
            correo = it
        }, label = { Text(text = "Correos") })
        */

        Spacer(modifier = Modifier.height(5.dp))
        //contraseña
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = { Text(text = "Contraseña")
        }, visualTransformation =  PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val updatedData = mapOf(
                "nombres" to nombre,
                "apellidos" to apellidos,
                "contrasena" to password
            )

            firestoreService?.actualizarDocumento("usuarios", id, updatedData,{
                // Código en caso de éxito, como mostrar un mensaje o redirigir
                Toast.makeText(context, "¡Usuario Actualizado!", Toast.LENGTH_SHORT).show()
            }, { exception ->
                // Código en caso de error
                Toast.makeText(context, "Ocurrio un errro al actualizar el usuario", Toast.LENGTH_SHORT).show()
                println("Error actualizando el documento: ${exception.message}")
            })
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF0C6E7A)
            )) {
            Text(text = "Actualizar Datos")
        }

        Button(onClick = {
            showDialog = true // se muestra el dialog al ahcer click
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )) {
            Text(text = "Cerrar Cuenta")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false // Al hacer clic fuera del modal, se cierra
                },
                title = {
                    Text(text = "Confirmar cierre de cuenta")
                },
                text = {
                    Text("¿Estás seguro de que deseas cerrar tu cuenta?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Acción para cerrar la cuenta
                            firestoreService?.eliminarDocumento("usuarios",id, {
                                // Código en caso de éxito, como mostrar un mensaje o redirigir
                                Toast.makeText(context, "¡Cuenta Cerrada! Redireccionado", Toast.LENGTH_SHORT).show()
                                //Se retrasa la redirección para mostrar el mensaje
                                Handler(Looper.getMainLooper()).postDelayed({
                                    // Acción que se ejecuta después del retraso
                                    val navigate = Intent(context, MainActivity::class.java)
                                    context.startActivity(navigate)
                                    // Se convirte context a una actividad para poder llamar a la funcion finish
                                    if (context is Activity) {
                                        (context as Activity).finish()
                                    }
                                }, 3000)

                                
                            }, { exception ->
                                // Código en caso de error
                                Toast.makeText(context, "Ocurrio un errro al cerrar la cuenta del usuario", Toast.LENGTH_SHORT).show()
                                println("Error al eliminar el documento: ${exception.message}")
                            })
                            showDialog = false
                        }
                    ) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false // Si se cancela, simplemente se cierra el modal
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun VistaPreviaDetallesUsuario(){
    var usuario: Usuario? = null
    DetallesUsuarioScreen(usuario)
}