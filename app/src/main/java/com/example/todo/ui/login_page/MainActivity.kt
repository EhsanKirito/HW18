package com.example.todo.ui.login_page

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    companion object {
        const val REGISTER_ADMIN = "ADMIN"
        const val REGISTER = "REGISTER"
        const val UN_REGISTER = "UN_REGISTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.getBooleanExtra(UN_REGISTER, false)) {
            this.getPreferences(Context.MODE_PRIVATE).edit()
                .putBoolean(REGISTER, false)
                .putBoolean(REGISTER_ADMIN, false)
                .apply()
        }

    }
}