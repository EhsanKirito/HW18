package com.example.todo.ui.login_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.localdata.entity.User
import com.example.todo.databinding.FragmentLoginBinding
import com.example.todo.ui.login_page.viewmodel.LoginViewModel
import com.example.todo.ui.todo_page.ToDoActivity
import com.example.todo.ui.AdminActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private var user: User? = null

    companion object {
        const val USER_ID = "USER_ID"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        setViewModel()
        navController = findNavController()
        viewModel.userName.observe(viewLifecycleOwner) {
            viewModel.oldUser(it).observe(viewLifecycleOwner) { user ->
                this.user = user
            }
        }
        getSharePreferences()
        binding.btnLogin.setOnClickListener {
            logIn()
        }
        binding.btnSingUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        return binding.root
    }

    private fun toGoTaskPage() {
        val intent = Intent(requireContext(), ToDoActivity::class.java)
        intent.putExtra(ToDoActivity.USER_ID, user!!.id)
        requireContext().startActivity(intent)
        activity?.finish()
    }

    private fun toGoAdminPage() {
        val intent = Intent(requireContext(), AdminActivity::class.java)
        requireContext().startActivity(intent)
        activity?.finish()
    }

    private fun setViewModel() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }


    private fun logIn() {
        if (binding.checkBox.isChecked) {
            if (viewModel.userName.value == viewModel.admin.userName && viewModel.password.value == viewModel.admin.password) {
                setSharedPreferencesAdmin()
                toGoAdminPage()
            }
        } else {
            user.let {
                if (user!!.password == viewModel.password.value) {
                    setSharedPreferences()
                    toGoTaskPage()
                } else {
                    Toast.makeText(requireContext(), "please signup now!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setSharedPreferencesAdmin() {
        requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
            .putBoolean(MainActivity.REGISTER_ADMIN, true).apply()
    }

    private fun setSharedPreferences() {
        requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
            .putBoolean(MainActivity.REGISTER, true).putLong(USER_ID, user!!.id).apply()
    }

    private fun getSharePreferences() {
        val sharePref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (sharePref.getBoolean(MainActivity.REGISTER_ADMIN, false)) {
            toGoAdminPage()
        }
        if (sharePref.getBoolean(MainActivity.REGISTER, false)) {
            user = User("", "", "", sharePref.getLong(USER_ID, 0))
            toGoTaskPage()
        }
    }

}