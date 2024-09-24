package com.example.exp2_s3_jorge_miranda

import android.app.Application
import com.google.firebase.FirebaseApp


class FirebaseApplication : Application () {
    override fun onCreate() {
        super.onCreate()
        // Inicializa Firebase
        FirebaseApp.initializeApp(this)
    }
}