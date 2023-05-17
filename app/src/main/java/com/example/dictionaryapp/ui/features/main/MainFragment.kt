package com.example.dictionaryapp.ui.features.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.os.LocaleListCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.FragmentMainBinding
import com.example.dictionaryapp.data.model.ui.Word
import com.example.dictionaryapp.ui.features.dialogs.AddDialog
import com.example.dictionaryapp.ui.features.main.enums.LanguageState
import com.example.dictionaryapp.ui.features.main.enums.ThemeLanguage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: WordAdapter
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        adapter = WordAdapter(viewModel.languageState, { word ->
            shareText(word)
        }) { word ->
            showBottomSheetDialogEdit(word.id)
        }
        binding.recyclerView.adapter = adapter
        navController = findNavController()
        setActionBar()
        setAdapter()
        binding.floatingActionButton.setOnClickListener {
            showBottomSheetDialogAdd()
        }
        return binding.root
    }


    private fun setAdapter() {
        viewModel.wordListLive.observe(viewLifecycleOwner) { words ->
            adapter.submitList(words)
            binding.countText.text = words.size.toString()
        }

        viewModel.languageState.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }


    private fun setActionBar() {
        val toolbar = binding.materialToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setOptionActionBar()
    }

    private fun setOptionActionBar() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.my_menu, menu)
                val searchItem = menu.findItem(R.id.actionMainSearch)
                val searchView = searchItem.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.getWordsBySearch(it) }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_pr_language -> changeLanguage(LanguageState.PERSIAN)
                    R.id.action_en_language -> changeLanguage(LanguageState.ENGLISH)
                    R.id.action_ar_language -> changeLanguage(LanguageState.ARABIC)
                    R.id.action_fr_language -> changeLanguage(LanguageState.FRENCH)
                    R.id.action_pr_language_app -> setLocale(ThemeLanguage.PERSIAN)
                    R.id.action_en_language_app -> setLocale(ThemeLanguage.ENGLISH)
                }
                return true
            }
        })
    }


    private fun changeLanguage(languageState: LanguageState) {
        viewModel.changeLanguage(languageState)
    }

    private fun showBottomSheetDialogAdd() {
        val add = AddDialog()
        add.show(childFragmentManager, "ADD")
    }


    private fun showBottomSheetDialogEdit(id: Int) {
        navController.navigate(MainFragmentDirections.actionMainFragmentToEditBottomSheetDialog(id))
    }

    private fun shareText(word: Word) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, """english word is: ${word.english}
                    | persian word is: ${word.farsi} 
                    | french word is: ${word.french} 
                    | arabic word is: ${word.arabic}""".trimMargin()
            )
            type = "text/plain"
        }
        val intent = Intent.createChooser(sendIntent, null)
        startActivity(intent)
    }

    private fun setLocale(languageSystem: ThemeLanguage) {
        val language = when (languageSystem) {
            ThemeLanguage.PERSIAN -> {
                viewModel.changeLanguage(LanguageState.PERSIAN)
                "fa"
            }

            ThemeLanguage.ENGLISH -> {
                viewModel.changeLanguage(LanguageState.ENGLISH)
                "en"
            }
        }
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }


}