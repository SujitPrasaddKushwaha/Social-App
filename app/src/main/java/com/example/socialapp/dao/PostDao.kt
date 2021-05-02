package com.example.socialapp.dao

import com.example.google_sign_in.models.User
import com.example.socialapp.models.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("posts")

    val auth = Firebase.auth

    fun addPost(text: String) {
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Post(text, user, currentTime)
            postCollection.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLike(postId: String) {
        GlobalScope.launch {
            val currenctUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val is_Liked = post.LikedBy.contains(currenctUserId)

            if (is_Liked) {
                post.LikedBy.remove(currenctUserId)
            } else {
                post.LikedBy.add(currenctUserId)
            }
            postCollection.document(postId).set(post)
        }
    }
}