package com.example.dictionaryapp.ui.features.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.dictionaryapp.databinding.DailogAddBinding
import com.example.dictionaryapp.ui.features.main.MainViewModel
import com.example.dictionaryapp.util.checkWordsValid
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDialog : BottomSheetDialogFragment() {
    lateinit var binding: DailogAddBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DailogAddBinding.inflate(inflater)
        binding.apply {

            buttonCrate.setOnClickListener {
                if (checkWordsValid(
                        tvEngland.text.toString(),
                        tvPersian.text.toString(),
                        tvFrance.text.toString(),
                        tvArabic.text.toString()
                    )
                ) {
                    viewModel.addWord(
                        tvEngland.text.toString(),
                        tvPersian.text.toString(),
                        tvFrance.text.toString(),
                        tvArabic.text.toString()
                    )
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "please enter valid data", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            buttonCancel.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }
}