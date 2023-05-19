package com.example.todo.ui.todo_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.todo.R
import com.example.todo.R.layout
import com.example.todo.databinding.FragmentTaskManagerBinding
import com.example.todo.ui.login_page.MainActivity
import com.example.todo.ui.todo_page.adapter.ViewPageAdapter
import com.example.todo.ui.todo_page.dialog.AddDialogFragment
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskManagerFragment : Fragment(layout.fragment_task_manager) {
    private lateinit var binding: FragmentTaskManagerBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPageAdapter: ViewPageAdapter
    private lateinit var tabLayout: TabLayout
    private val viewModel: TaskManagerViewModel by activityViewModels()
    private lateinit var menuHost: MenuHost
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskManagerBinding.inflate(inflater)
        navController = findNavController()
        setViewPager()
        setTabLayout()
        binding.floatingActionButton.setOnClickListener { showDialogAdd() }
        viewModel.longClick.observe(viewLifecycleOwner) {
            binding.floatingActionButton.isVisible = !it
        }
        setUpMenu()

        return binding.root
    }

    private fun setViewPager() {
        viewPageAdapter = ViewPageAdapter(this)
        viewPager = binding.viewPager2
        viewPager.adapter = viewPageAdapter
    }

    private fun setTabLayout() {
        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "ToDo"
                1 -> tab.text = "Doing"
                else -> tab.text = "Done"
            }
        }.attach()
    }

    private fun showDialogAdd() {
        AddDialogFragment().show(childFragmentManager, "add")
    }

    private fun setUpMenuDefault() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_todo_page, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionDeleteAll -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Are you sure you want to delete the task?")
                            .setPositiveButton("yes") { _, _ ->
                                viewModel.deleteAllTask()
                            }.setNegativeButton(
                                "cancel", null
                            )
                        builder.create()
                        builder.show()
                        true
                    }

                    R.id.actionLogout -> {
                        logout()
                        true
                    }

                    R.id.actionSearch -> {
                        actionSearch()
                        true
                    }

                    else -> false
                }
            }
        })
    }

    private fun setUpMenuWhenSelect() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_todo_page_select, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionDelete -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Are you sure you want to delete the task?")
                            .setPositiveButton("yes") { _, _ ->
                                viewModel.deleteSelectTask()
                            }.setNegativeButton(
                                "cancel", null
                            )
                        builder.create()
                        builder.show()
                        true
                    }

                    R.id.actionMove -> {
                        true
                    }

                    R.id.actionSelectAll -> {
                         viewModel.selectAllTask()
                        true
                    }

                    R.id.actionUnSelectAll -> {
                        viewModel.unSelectAllTask()
                        true
                    }

                    else -> false
                }
            }
        })
    }



    private fun actionSearch() {
        navController.navigate(R.id.action_taskManagerFragment_to_searchFragment)
    }

    private fun setUpMenu() {
        viewModel.longClick.observe(viewLifecycleOwner) {
            if (!it) {
                setUpMenuDefault()
            } else {
                setUpMenuWhenSelect()
            }
        }
    }

    private fun logout() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(MainActivity.UN_REGISTER, true)
        requireContext().startActivity(intent)
        activity?.finish()
    }

}