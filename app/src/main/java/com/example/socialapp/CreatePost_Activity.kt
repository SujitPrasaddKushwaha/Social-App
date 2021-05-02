package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialapp.dao.PostDao
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class CreatePost_Activity : AppCompatActivity() {

    private lateinit var postInput : TextInputEditText
    private lateinit var btn_post : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_)

        postInput = findViewById(R.id.postInput)
        btn_post = findViewById(R.id.btn_post)

        btn_post.setOnClickListener(){
            val input = postInput.text.toString().trim()
            if (input.isNotEmpty()){
                val postDao = PostDao()
                postDao.addPost(input)
                Toast.makeText(this, "Post Successful", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
}