package com.example.todo.ui.todo_page.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todo.R
import com.example.todo.localdata.entity.Task

typealias click = (task: Task) -> Unit

class TaskAdapter(
    val longClickTask: click,
    val clickTask: click,
    val clickShare: click
) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback) {

    inner class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val itemHost: ConstraintLayout = itemView.findViewById(R.id.constraint_layout)
        private val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val itemDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val itemState: TextView = itemView.findViewById(R.id.tv_state)
        private val itemImageView: ImageView = itemView.findViewById(R.id.image_task)
        private val itemDate: TextView = itemView.findViewById(R.id.tv_date)
        private val itemTime: TextView = itemView.findViewById(R.id.tv_time)
        private val itemShare: TextView = itemView.findViewById(R.id.tv_share)

        fun bind(task: Task) {
            itemTitle.text = task.title
            itemDescription.text = task.description
            itemState.text = task.state.name
            itemDate.text = task.date
            itemTime.text = task.time
            if (!task.imageSrc.isNullOrEmpty()) {
                Glide.with(itemImageView)
                    .load(task.imageSrc)
                    .into(itemImageView)
            }
            bindBackground(task)
        }

        init {
            itemShare.setOnClickListener {
                clickShare(getItem(adapterPosition))
            }
        }

        fun bindBackground(task: Task) {
            itemHost.setBackgroundColor(Color.parseColor(if (task.select) "#DD2C00" else "#142DBA"))
        }
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_re, parent, false)
        return TaskViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
        holder.itemHost.setOnLongClickListener {
            longClickTask(task)
            holder.bindBackground(task)
            true
        }
        holder.itemHost.setOnClickListener {
            clickTask(task)
            holder.bindBackground(task)
        }

    }

}