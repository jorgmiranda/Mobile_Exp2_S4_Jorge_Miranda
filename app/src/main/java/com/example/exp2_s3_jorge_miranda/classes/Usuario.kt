package com.example.exp2_s3_jorge_miranda.classes

import com.example.exp2_s3_jorge_miranda.interfaces.funcionesUsuario
import java.io.Serializable

data class Usuario(
    var id: String? = null,
    val nombres: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val contrasena: String = ""
): funcionesUsuario, Serializable{

    // Constructor vacío requerido para la deserialización
    constructor() : this(null, "", "", "", "")

    override fun verDatos(): String {
        return nombres + " " +apellidos
    }

    override fun verContrasena(): String {
        return contrasena
    }
}
