package com.example.exp2_s3_jorge_miranda.repository

import coil.compose.AsyncImagePainter
import com.example.exp2_s3_jorge_miranda.classes.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlin.reflect.KClass

class FirebaseService<T: Any> {
    private val db = FirebaseFirestore.getInstance()

    //Agregar objeto a collección
    fun agregarDocumento(nombreColeccion: String, item: T, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit){
        db.collection(nombreColeccion)
            .add(item)
            .addOnSuccessListener { documentReference ->
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    //Leer todos los objetos de una coleccion
    fun obtenerDocumentos(nombreColeccion: String, itemType: KClass<T>, onSuccess: (List<T>) -> Unit, onFailure: (Exception) -> Unit){
        db.collection(nombreColeccion)
            .get()
            .addOnSuccessListener { result ->
                val items = result.map { documentSnapshot ->
                    val item = documentSnapshot.toObject(itemType.java)

                    // Usamos reflexion para vieridicar si la clase tiene un campo id
                    val idField = itemType.java.declaredFields.find { it.name == "id" }
                    if (idField != null) {
                        idField.isAccessible = true
                        idField.set(item, documentSnapshot.id)  // Asignar el ID generado por Firestore
                    }
                    item
                }
                onSuccess(items)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun actualizarDocumento(nombreColeccion: String, documentId: String, data: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        db.collection(nombreColeccion).document(documentId)
            .update(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun eliminarDocumento(nombreColeccion: String, documentId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        db.collection(nombreColeccion).document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    //Metodo para validar existencia de un usuario
    fun existeUsuarioConCorreo(correo: String, onResult: (Boolean) -> Unit, onError: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        // Consulta para verificar si ya existe un usuario con el correo proporcionado
        db.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { result ->
                // Si el resultado contiene documentos, el usuario ya existe
                if (!result.isEmpty) {
                    onResult(true)  // El usuario ya existe
                } else {
                    onResult(false)  // El usuario no existe
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    //Metodo para validar la existencia del usuario
    fun login(correo: String, contrasena: String, onResult: (Usuario?) -> Unit, onError: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    onResult(null) // El usuario no existe
                } else {
                    val document = result.documents.first()
                    val usuario = result.documents.first().toObject(Usuario::class.java)

                    if (usuario?.contrasena == contrasena) {
                        usuario.id = document.id
                        onResult(usuario) // Login exitoso, devuelve el objeto Usuario
                    } else {
                        onResult(null) // Contraseña incorrecta
                    }
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

}