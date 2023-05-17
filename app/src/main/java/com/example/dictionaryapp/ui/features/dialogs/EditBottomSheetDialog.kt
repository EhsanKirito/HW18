package com.example.dictionaryapp.ui.features.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.dictionaryapp.data.model.ui.Word
import com.example.dictionaryapp.databinding.DialogEditBinding
import com.example.dictionaryapp.ui.features.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogEditBinding
    private val viewModel: MainViewModel by viewModels()
    private val args: EditBottomSheetDialogArgs by navArgs()
    private lateinit var word: Word
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEditBinding.inflate(inflater)
        getWordAndSet(args.wordId)
        viewModel.word.observe(viewLifecycleOwner) { word ->
            setWordOnView(word)
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        binding.buttonEdit.setOnClickListener {
            editWord()
            viewModel.updateWord(word)
            dismiss()
        }
        binding.buttonDelete.setOnClickListener {
            viewModel.deleteWord(word)
            dismiss()
        }
        return binding.root
    }

    private fun getWordAndSet(id: Int) {
        viewModel.getWord(id)
    }

    private fun setWordOnView(word: Word) {
        this.word = word
        binding.apply {
            tvFrance.setText(word.french)
            tvArabic.setText(word.arabic)
            tvPersian.setText(word.farsi)
            tvEngland.setText(word.english)
        }
    }

    private fun editWord() {
        binding.apply {
            word.arabic = tvArabic.text.toString()
            word.french = tvFrance.text.toString()
            word.english = tvEngland.text.toString()
            word.farsi = tvPersian.text.toString()
        }
    }
}