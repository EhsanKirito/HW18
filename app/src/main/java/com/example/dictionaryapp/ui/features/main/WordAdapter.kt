package com.example.dictionaryapp.ui.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.ItemReBinding
import com.example.dictionaryapp.data.model.ui.Word
import com.example.dictionaryapp.ui.features.main.enums.LanguageState

typealias Click = (word: Word) -> Unit

class WordAdapter(
    private val stateLanguage: LiveData<LanguageState>,
    private val shear: Click,
    private val click: Click
) : ListAdapter<Word, WordAdapter.WordViewHolder>(diffCallback) {

    inner class WordViewHolder(private val binding: ItemReBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                tvPersian.text = word.farsi
                tvFrance.text = word.french
                tvArabic.text = word.arabic
                tvEngland.text = word.english
                bindLanguage(word)
            }
        }


        private fun bindLanguage(word: Word) {
            with(binding) {
                val wordMap = mapOf(
                    LanguageState.PERSIAN to word.farsi,
                    LanguageState.ENGLISH to word.english,
                    LanguageState.FRENCH to word.french,
                    LanguageState.ARABIC to word.arabic
                )
                val selectedWord = wordMap[stateLanguage.value] ?: word.english
                tvMainWord.text = selectedWord
                tvPersianGroup.isVisible = stateLanguage.value != LanguageState.PERSIAN
                tvArabicGroup.isVisible = stateLanguage.value != LanguageState.ARABIC
                tvEnglandGroup.isVisible = stateLanguage.value != LanguageState.ENGLISH
                tvFranceGroup.isVisible = stateLanguage.value != LanguageState.FRENCH
            }
        }

        init {
            binding.root.setOnClickListener {
                click(getItem(adapterPosition))
            }
            binding.btnShear.setOnClickListener {
                shear(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemReBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

val diffCallback = object : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem

}