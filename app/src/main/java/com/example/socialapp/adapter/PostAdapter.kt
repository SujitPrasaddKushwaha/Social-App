package com.example.socialapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.R
import com.example.socialapp.Utils.Utils
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener : IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val postText : TextView = itemView.findViewById(R.id.postTitle)
        val username : TextView = itemView.findViewById(R.id.userName)
        val createdAt : TextView = itemView.findViewById(R.id.createdAt)
        val likeCount : TextView = itemView.findViewById(R.id.likeCount)
        val userImage : ImageView = itemView.findViewById(R.id.userImage)
        val likedbutton : ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false))
        view.likedbutton.setOnClickListener(){
            listener.onLikeCliked(snapshots.getSnapshot(view.adapterPosition).id)
        }
        return view
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.username.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.photoUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.LikedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

        val auth = Firebase.auth
        val currentUserID = auth.currentUser!!.uid
        val is_liked = model.LikedBy.contains(currentUserID)
        if(is_liked){
            holder.likedbutton.setImageDrawable(ContextCompat.getDrawable(holder.likedbutton.context,R.drawable.ic_liked))
        }
        else
        {
            holder.likedbutton.setImageDrawable(ContextCompat.getDrawable(holder.likedbutton.context,R.drawable.ic_unliked))
        }
    }
}

interface IPostAdapter{
    fun onLikeCliked(postId : String)
}