package com.example.socialapp.dao

import com.example.google_sign_in.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

    val db = FirebaseFirestore.getInstance()
    val userColections = db.collection("users")

    //adding user to firestone
    fun addUser(user: User?) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userColections.document(user.uid).set(it)
            }
        }
    }

    //getting user information by ID
    fun getUserById(uId : String): Task<DocumentSnapshot>{
        return userColections.document(uId).get()
    }
}