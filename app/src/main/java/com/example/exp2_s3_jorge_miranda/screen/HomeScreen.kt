package com.example.exp2_s3_jorge_miranda.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.exp2_s3_jorge_miranda.R
import com.example.exp2_s3_jorge_miranda.activity.DetalleUsuarioActivity
import com.example.exp2_s3_jorge_miranda.activity.HomeActivity
import com.example.exp2_s3_jorge_miranda.classes.PreferencesManager
import com.example.exp2_s3_jorge_miranda.classes.Receta
import com.example.exp2_s3_jorge_miranda.classes.Usuario

@Composable
fun HomeScreen(usuario:Usuario?){
    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    ) // Define tus colores de degradado

    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)

   // val usuario = preferencesManager.getObject("usuario_"+correoUsuario, Usuario::class.java)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C6E7A), contentColor = Color.White),
            onClick = {
                val intent = Intent(context, DetalleUsuarioActivity::class.java).apply {
                    putExtra("usuario", usuario)
                }
                context.startActivity(intent)
            }
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Image(painter = painterResource(id = R.drawable.avartar2), contentDescription = "Imagen Login",
                    modifier = Modifier.size(100.dp))

                if (usuario != null) {
                    Text(text = "Hola "+usuario.nombres +" "+usuario.apellidos,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(20.dp))
                }else{
                    Text(text = "Hola Test",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(20.dp))
                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Estas son las recetas del dia:",
                fontSize = 15.sp,
                fontWeight= FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(20.dp, 20.dp))

            //Se cargan las recetas predeterminadas
            val recetas = Receta.generarRecetasPreCargadas()

            LazyColumn {
                items(recetas)
                {
                    receta:Receta ->Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
                    colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
                    ){
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ){
                            Image(painter = rememberAsyncImagePainter(model = receta.imagen), contentDescription = "URL Imagen",
                                modifier = Modifier.size(70.dp))

                            Text(text = receta.nombre,
                                fontSize = 16.sp,
                                fontWeight= FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp))
                        }
                         Spacer(modifier = Modifier.height(8.dp))
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ){
                            Text(text = receta.ingredientes,
                                fontSize = 16.sp,
                                fontWeight= FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp))
                        }
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun VistaPreviaHome(){
    var usuario: Usuario? = null
    HomeScreen(usuario)
}