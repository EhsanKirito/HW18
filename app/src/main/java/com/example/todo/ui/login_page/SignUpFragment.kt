package com.example.todo.ui.login_page

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
import com.example.todo.databinding.FragmentSignUpBinding
import com.example.todo.ui.login_page.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        navController = findNavController()
        setViewModel()
        binding.btnSingUp2.setOnClickListener {
            signUp()
        }
        return binding.root
    }

    private fun setViewModel() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }


    private fun signUp() {
        if (viewModel.checkEntryValid()) {
            viewModel.addNewUser()
            goToLogin()
        } else {
            Toast.makeText(requireContext(), "please enter your valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToLogin() {
        navController.navigate(R.id.action_signUpFragment_to_loginFragment)
    }
}