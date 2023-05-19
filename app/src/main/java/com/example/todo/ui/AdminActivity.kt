package com.example.todo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }
}