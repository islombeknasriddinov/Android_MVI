package com.example.android_mvi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvi.R
import com.example.android_mvi.activity.main.helper.MainHelperImpl
import com.example.android_mvi.activity.main.intentstate.MainIntent
import com.example.android_mvi.activity.main.intentstate.MainState
import com.example.android_mvi.activity.main.viewModel.MainViewModel
import com.example.android_mvi.activity.main.viewModel.MainViewModelFactory
import com.example.android_mvi.adapter.PostAdapter
import com.example.android_mvi.model.Post
import com.example.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        observerViewModel()
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Init -> {
                        Log.d("MainActivity", "Init")
                    }
                    is MainState.Lading -> {
                        Log.d("MainActivity", "Loading")
                    }
                    is MainState.AllPost -> {
                        Log.d("MainActivity", "PostList")
                        refreshAdapter(it.posts)
                    }
                    is MainState.DeletePost -> {
                        Log.d("MainActivity", "DeletePost " + it.post)
                        intentAllPost()
                    }
                    is MainState.Error -> {
                        Log.d("MainActivity", "Error " + it.error)
                    }
                }
            }
        }
    }

    private fun refreshAdapter(posts: ArrayList<Post>) {
        val adapter = PostAdapter(this, posts)
        recyclerView.adapter = adapter
    }

    private fun initView() {
        val factory = MainViewModelFactory(MainHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        intentAllPost()
    }

    private fun intentAllPost() {
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.AllPosts)
        }
    }

    fun intentDeletePost(id: Int) {
        viewModel.postId = id
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.DeletePost)
        }
    }
}