package com.example.todo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.localdata.entity.User

typealias Click = (User) -> Unit

class UserAdapter(private val click: Click) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback) {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemHost: ConstraintLayout = itemView.findViewById(R.id.constraint_layout_admin)
        private val itemTitle: TextView = itemView.findViewById(R.id.tv_userName)
        private val itemDate: TextView = itemView.findViewById(R.id.tv_date_login)

        init {
            itemHost.setOnClickListener {
                click(getItem(adapterPosition))
            }
        }

        fun bind(user: User) {
            itemTitle.text = user.userName
            itemDate.text = user.dateSignup
        }
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userName == newItem.userName
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.admin_item_re, parent, false)
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

}