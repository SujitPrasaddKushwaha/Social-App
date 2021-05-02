package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.adapter.IPostAdapter
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.dao.PostDao
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

class Dashboard_Activity : AppCompatActivity(), IPostAdapter {

    private lateinit var fab_createPost : FloatingActionButton
    private lateinit var postAdapter : PostAdapter
    private lateinit var post_recyclerview : RecyclerView
    private lateinit var postDao : PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_)

        fab_createPost = findViewById(R.id.fab_createPost)
        post_recyclerview = findViewById(R.id.post_recyclerView)

        fab_createPost.setOnClickListener(){
            startActivity(Intent(this,CreatePost_Activity::class.java))
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        postDao = PostDao()
        val postCollection = postDao.postCollection
        val query = postCollection.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()

        postAdapter = PostAdapter(recyclerViewOptions,this)

        post_recyclerview.adapter = postAdapter
        post_recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }

    override fun onLikeCliked(postId: String) {
        postDao.updateLike(postId)
    }
}