package com.example.android_mvi.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android_mvi.R
import com.example.android_mvi.activity.detail.helper.DetailHelperImpl
import com.example.android_mvi.activity.detail.intentstate.DetailIntent
import com.example.android_mvi.activity.detail.intentstate.DetailState
import com.example.android_mvi.activity.detail.viewModel.DetailViewModel
import com.example.android_mvi.activity.detail.viewModel.DetailViewModelFactory
import com.example.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var tv_title: TextView
    private lateinit var tv_body: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViews()
        observerViewModel()
    }

    private fun observerViewModel() {
        lifecycleScope.launch{
            viewModel.state.collect{
                when (it){
                    is DetailState.Init -> {
                        Log.d("TAGDetailPage", "Init")
                    }
                    is DetailState.Loading -> {
                        Log.d("TAGDetailPage", "Loading")
                    }
                    is DetailState.DetailPost -> {
                        Log.d("TAGDetailPage", "AllPosts: ${it.post}")
                        tv_title.text = it.post.title
                        tv_body.text = it.post.body
                    }
                    is DetailState.Error -> {
                        Log.d("TAGDetailPage", "Error: ${it.error}")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = DetailViewModelFactory(DetailHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        tv_title = findViewById(R.id.tv_title_detail)
        tv_body = findViewById(R.id.tv_body_detail)
        val iv_back = findViewById<ImageView>(R.id.iv_back)
        iv_back.setOnClickListener { finish() }
        val id: Int = intent.getIntExtra("id", 0)
        intentDetailPost(id)
    }

    private fun intentDetailPost(id: Int) {
        lifecycleScope.launch {
            viewModel.id = id
            viewModel.detailIntent.send(DetailIntent.DetailPost)
        }
    }

}