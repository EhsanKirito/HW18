package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.FragmentSearchBinding
import com.example.todo.ui.todo_page.adapter.TaskSearchAdapter
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    val viewModel: TaskManagerViewModel by activityViewModels()
    lateinit var adapter: TaskSearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        setUpMenuDefault()
        setListAdapter()
        viewModel.searchTask
            .observe(viewLifecycleOwner) { taskList ->
                adapter.submitList(taskList)
            }


        return binding.root
    }


    private fun setUpMenuDefault() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.actionMainSearch)
                val searchView = searchItem.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.searchTask(newText.toString())
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun setListAdapter() {
        adapter = TaskSearchAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
}
