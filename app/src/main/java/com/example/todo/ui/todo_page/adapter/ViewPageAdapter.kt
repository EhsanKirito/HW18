package com.example.todo.ui.todo_page.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todo.ui.todo_page.doing.DoingFragment
import com.example.todo.ui.todo_page.done.DoneFragment
import com.example.todo.ui.todo_page.todo.ToDoFragment

class ViewPageAdapter(itemFragment: Fragment) : FragmentStateAdapter(itemFragment) {


    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ToDoFragment()
            1 -> DoingFragment()
            else -> DoneFragment()
        }
    }
}