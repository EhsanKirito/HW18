package com.example.todo.ui.todo_page.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.todo.localdata.entity.Task
import com.example.todo.databinding.FragmentDialogEditBinding
import com.example.todo.todo_page.dialog.EditDialogFragmentArgs
import com.example.todo.ui.todo_page.enums.TaskState
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class EditDialogFragment : DialogFragment() {
    private val viewModel: TaskManagerViewModel by activityViewModels()
    private val args: EditDialogFragmentArgs by navArgs()
    private lateinit var binding: FragmentDialogEditBinding
    private lateinit var calendar: Calendar
    private lateinit var state: TaskState
    private lateinit var task: Task
    private var uriSrc: String? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uriSrc = uri.toString()
            Glide.with(requireView())
                .load(uriSrc)
                .into(binding.imageTask)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        calendar = Calendar.getInstance()
        binding = FragmentDialogEditBinding.inflate(inflater)
        val taskId = args.id

        viewModel.getTask(taskId).observe(viewLifecycleOwner) { task ->
            this.task = task
            bind(task)
        }

        binding.imageTask.setOnClickListener {
            selectImage()
        }

        binding.etDatePiker.setOnClickListener {
            setDatePicker()
        }
        binding.etTimePiker.setOnClickListener {
            setTimePicker()
        }
        binding.radioGroups.setOnCheckedChangeListener { _, id ->
            state = binding.root.findViewById<RadioButton>(id).tag as TaskState
        }
        binding.btnEditeTask.setOnClickListener {
            updateTask()
            dismiss()
        }
        binding.btnDeleteTask.setOnClickListener {
            Log.e("TAG", "onCreateView: $task")

            deleteTask()
            dismiss()
        }
        binding.btnCancelTask.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


    private fun bind(task: Task) {
        binding.apply {
            etTitleTask.setText(task.title)
            etDescriptionTask.setText(task.description)
            etDatePiker.setText(task.date)
            etTimePiker.setText(task.time)
            if (!task.imageSrc.isNullOrEmpty()) {
                Glide.with(requireView())
                    .load(task.imageSrc)
                    .into(binding.imageTask)
            }
            when (task.state) {
                TaskState.TODO -> binding.radioTodo.isChecked = true
                TaskState.DOING -> binding.radioDoing.isChecked = true
                TaskState.DONE -> binding.radioDone.isChecked = true
            }
        }
    }

    private fun setDatePicker() {
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val date = (year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth)
                binding.etDatePiker.setText(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setTimePicker() {
        TimePickerDialog(
            requireContext(), { _, selectHors, selectMinute ->
                val time = "$selectHors:$selectMinute"
                binding.etTimePiker.setText(time)
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true
        ).show()
    }

    private fun updateTask() {
        task.let {
            binding.apply {
                task.title = binding.etTitleTask.text.toString()
                task.description = binding.etDescriptionTask.text.toString()
                task.date = binding.etDatePiker.text.toString()
                task.time = binding.etTimePiker.text.toString()
                task.state = state
                if (!uriSrc.isNullOrEmpty()) {
                    task.imageSrc = uriSrc
                }
            }
            viewModel.updateTask(task)
        }
    }

    private fun selectImage() {
        getContent.launch("image/*")
    }

    private fun deleteTask() {
        dismiss()
        task.let {
            viewModel.deleteTask(it)
        }
    }
}
