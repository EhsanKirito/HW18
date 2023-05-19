package com.example.todo.ui.todo_page

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todo.R
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoActivity : AppCompatActivity() {
    companion object {
        const val USER_ID = "USER_ID"
    }

    private val viewModel: TaskManagerViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        viewModel.setUserId(intent.getLongExtra(USER_ID, 0))
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}