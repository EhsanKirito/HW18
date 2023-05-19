package com.example.todo.ui.todo_page.done

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.localdata.entity.Task
import com.example.todo.databinding.FragmentDoneBinding
import com.example.todo.todo_page.TaskManagerFragmentDirections
import com.example.todo.ui.todo_page.adapter.TaskAdapter
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoneFragment : Fragment(R.layout.fragment_done) {
    private val viewModel: TaskManagerViewModel by activityViewModels()
    private lateinit var binding: FragmentDoneBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoneBinding.inflate(inflater)
        adapter = TaskAdapter(viewModel.clickLong, taskClick) { task ->
            shareText(task)
        }
        setListAdapter()
        binding.recyclerView.adapter = adapter
        navController = findNavController()
        return binding.root
    }

    override fun onPause() {
        viewModel.unSelectAllTask()
        setListAdapter()
        super.onPause()
    }

    private fun shareText(task: Task) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "title is :${task.title} description is:${task.description}" +
                        "date and Time is :${task.date} ${task.time}"
            )

            type = "text/plain"
        }
        val intent = Intent.createChooser(sendIntent, null)
        startActivity(intent)
    }

    private fun setListAdapter() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.listDoneTaskLiveData().observe(viewLifecycleOwner) { taskList ->
            if (taskList.isEmpty()) showEmptyPage()
            else hideEmptyPage()
            adapter.submitList(taskList)


        }
    }

    private fun showEmptyPage() {
        binding.textEmpty.isVisible = true
    }

    private fun hideEmptyPage() {
        binding.textEmpty.isVisible = false
    }

    private val taskClick: (Task) -> Unit = { task ->
        if (viewModel.longClick.value == true) {
            viewModel.clickWhenLong(task)
        } else {
            navController.navigate(
                TaskManagerFragmentDirections.actionTaskManagerFragmentToEditDialogFragment(
                    task.id
                )
            )
        }

    }
}