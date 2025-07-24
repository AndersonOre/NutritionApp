package com.anderson.nutritionapp.presentation.user_auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, onResult: (Boolean, FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, auth.currentUser)
                } else {
                    onResult(false, null)
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, auth.currentUser, null)
                } else {
                    val errorMsg = task.exception?.localizedMessage ?: "Authentication failed."
                    onResult(false, null, errorMsg)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun currentUser(): FirebaseUser? = auth.currentUser
}