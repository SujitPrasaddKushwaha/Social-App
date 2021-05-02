package com.example.socialapp.models

import com.example.google_sign_in.models.User

data class Post(
    val text : String = "",
    val createdBy: User = User(),
    val createdAt : Long = 0L,
    val LikedBy : ArrayList<String> = ArrayList()
)