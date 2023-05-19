package com.example.todo.ui.todo_page.dialog


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.todo.localdata.entity.Task
import com.example.todo.databinding.FragmentDialogAddBinding
import com.example.todo.ui.todo_page.enums.TaskState
import com.example.todo.ui.todo_page.viewmodel.TaskManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddDialogFragment : DialogFragment() {
    private val viewModel: TaskManagerViewModel by activityViewModels()
    private lateinit var binding: FragmentDialogAddBinding
    private lateinit var calendar: Calendar
    private var state: TaskState = TaskState.TODO
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
        binding = FragmentDialogAddBinding.inflate(inflater)
        calendar = Calendar.getInstance()
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
        binding.btnCreateTask.setOnClickListener {
            createTask()
            dismiss()
        }
        binding.btnCancelTask.setOnClickListener {
            dismiss()
        }

        return binding.root
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

    private fun createTask() {
        val task = Task("EMPTY", "EMPTY", "EMPTY", "EMPTY", TaskState.TODO, 0)
        binding.apply {
            task.title = binding.etTitleTask.text.toString()
            task.description = binding.etDescriptionTask.text.toString()
            task.date = binding.etDatePiker.text.toString()
            task.time = binding.etTimePiker.text.toString()
            task.state = state
            task.imageSrc = uriSrc
        }
        viewModel.addTask(task)
    }


    private fun selectImage() {
        getContent.launch("image/*")
    }

}