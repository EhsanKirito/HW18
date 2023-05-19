package com.example.todo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.localdata.entity.User
import com.example.todo.databinding.FragmentAdminBinding
import com.example.todo.ui.login_page.MainActivity
import com.example.todo.ui.todo_page.ToDoActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminFragment : Fragment() {
    private lateinit var binding: FragmentAdminBinding
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var adapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater)
        setListAdapter()
        setMenu()
        return binding.root
    }

    private fun setListAdapter() {
        adapter = UserAdapter { user ->
            showDialog(user)
        }
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.usersList.observe(viewLifecycleOwner) { taskList ->
            adapter.submitList(taskList)

        }
    }

    private fun showDialog(user: User) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("what todo what not todo?????")
            .setNeutralButton("DeleteUser") { _, _ ->
                viewModel.deleteUser(user)

            }
            .setPositiveButton("Go to tasksUser") { _, _ ->

                toGoTaskPage(user)
            }
            .setNegativeButton(
                "cancel", null
            )
        builder.create()
        builder.show()
    }

    private fun setMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.login_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.action_logout) {
                    logout()
                    true
                } else {
                    true
                }
            }

        })

    }

    private fun toGoTaskPage(user: User) {
        val intent = Intent(requireContext(), ToDoActivity::class.java)
        intent.putExtra(ToDoActivity.USER_ID, user.id)
        requireContext().startActivity(intent)
    }


    private fun logout() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(MainActivity.UN_REGISTER, true)
        requireContext().startActivity(intent)
        activity?.finish()
    }

}