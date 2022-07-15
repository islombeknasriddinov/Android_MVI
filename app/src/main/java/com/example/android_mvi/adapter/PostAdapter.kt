package com.example.android_mvi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvi.R
import com.example.android_mvi.activity.MainActivity
import com.example.android_mvi.model.Post
import com.example.android_mvi.utils.Utils

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PosterViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PosterViewHolder) {
            val ll_poster = holder.ll_poster
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body
            val iv_edit = holder.iv_edit

            tv_title.setText(post.title.toUpperCase())
            tv_body.setText(post.body)

            ll_poster.setOnClickListener {
                activity.callDetailPage(post.id)
            }
            ll_poster.setOnLongClickListener {
                deletePostDialog(post)
                notifyDataSetChanged()
                true
            }
            iv_edit.setOnClickListener {
                activity.callEditPage(post)
            }
        }
    }

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView
        var tv_body: TextView
        var ll_poster: LinearLayout
        var iv_edit: ImageView

        init {
            ll_poster = view.findViewById(R.id.ll_poster)
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
            iv_edit = view.findViewById(R.id.iv_edit)
        }
    }

    private fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"
        Utils.customDialog(activity, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                activity.intentDeletePost(post.id)
            }

            override fun onNegativeClick() {

            }
        })
    }

}