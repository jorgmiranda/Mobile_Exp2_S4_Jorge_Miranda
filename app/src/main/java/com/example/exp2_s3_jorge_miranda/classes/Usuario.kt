package com.example.exp2_s3_jorge_miranda.classes

import com.example.exp2_s3_jorge_miranda.interfaces.funcionesUsuario

class Usuario(
    val nombres: String,
    val apellidos: String,
    val correo: String,
    val contrasena: String
): funcionesUsuario{

    override fun verDatos(): String {
        return nombres + " " +apellidos
    }

    override fun verContrasena(): String {
        return contrasena
    }
}
