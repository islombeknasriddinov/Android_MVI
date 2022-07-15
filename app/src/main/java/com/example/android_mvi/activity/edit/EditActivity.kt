package com.example.android_mvi.activity.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android_mvi.R
import com.example.android_mvi.activity.edit.intentstate.EditIntent
import com.example.android_mvi.activity.edit.helper.EditHelperImpl
import com.example.android_mvi.activity.edit.intentstate.EditState
import com.example.android_mvi.activity.edit.viewModel.EditViewModel
import com.example.android_mvi.model.Post
import com.example.android_mvi.network.RetrofitBuilder
import com.example.android_mvi.activity.edit.viewModel.EditViewModelFactory
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        initViews()
        observerViewModel()
    }

    private fun observerViewModel() {
        lifecycleScope.launch{
            viewModel.state.collect{
                when (it){
                    is EditState.Init -> {
                        Log.d("TAGEditPage", "Init")
                    }
                    is EditState.Loading -> {
                        Log.d("TAGEditPage", "Loading")
                    }
                    is EditState.EditPost -> {
                        Log.d("TAGEditPage", "AllPosts: ${it.post}")
                        val intent = Intent()
                        setResult(10, intent)
                        finish()
                    }
                    is EditState.Error -> {
                        Log.d("TAGEditPage", "Error: ${it.error}")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = EditViewModelFactory(EditHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory)[EditViewModel::class.java]

        val edt_title = findViewById<EditText>(R.id.edt_title)
        val edt_body = findViewById<EditText>(R.id.edt_message)
        val b_update = findViewById<ImageView>(R.id.b_update_post)
        var post: Post = intent.getSerializableExtra("post") as Post
        edt_title.setText(post.title)
        edt_body.setText(post.body)

        b_update.setOnClickListener {
            if (edt_title.text.isNotEmpty() && edt_body.text.isNotEmpty() && edt_body.text.toString() != post.body && edt_title.text.toString() != post.title){
                post.title = edt_title.text.toString()
                post.body = edt_body.text.toString()
                intentEditPost(post)
            }
        }

    }

    private fun intentEditPost(post: Post){
        lifecycleScope.launch{
            viewModel.post = post
            viewModel.editIntent.send(EditIntent.EditPost)
        }
    }

}