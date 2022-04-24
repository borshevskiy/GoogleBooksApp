package com.borshevskiy.googlebooksapp.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.borshevskiy.googlebooksapp.R
import com.borshevskiy.googlebooksapp.databinding.FragmentSearchBinding
import com.borshevskiy.googlebooksapp.presentation.adapters.BookListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var savedTextQuery = EMPTY_TEXT_FIELD
    private var saveState = EMPTY_TEXT_FIELD

    private val args by navArgs<SearchFragmentArgs>()

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { BookListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setHasOptionsMenu(true)
        savedInstanceState?.let { saveState = savedInstanceState.getString(SEARCH_KEY)!! }
        savedTextQuery = if (savedInstanceState == null) args.queryFromSettings else savedInstanceState.getString(SEARCH_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvBookList.adapter = mAdapter
        if (savedTextQuery != saveState) {
            searchApiData(args.queryFromSettings)
        } else if (savedTextQuery == saveState || savedTextQuery == args.queryFromSettings) showBookList()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_KEY, savedTextQuery)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)

        if (args.query.queryPosition != DEFAULT_POSITION) menu
            .findItem(R.id.menu_settings).setIcon(R.drawable.ic_applied_query)

        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        with(searchView) {
            setIconifiedByDefault(false)
            queryHint = context.getString(R.string.search_text)
            setQuery(savedTextQuery, true) }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    newText?.let {
                        delay(500)
                        if (it.isNotEmpty()) {
                            searchApiData(it)
                            savedTextQuery = it
                        } else {
                            savedTextQuery = EMPTY_TEXT_FIELD
                            showNothing()
                        }
                    }
                }
                return false
            }
        })
    }

    private fun showNothing() {
        mAdapter.submitList(emptyList())
        binding.textViewPlaceholder.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                findNavController().navigate(SearchFragmentDirections
                    .actionSearchFragmentToSettingsFragment(args.query.queryPosition, savedTextQuery))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBookList() {
        mainViewModel.searchBookList.observe(viewLifecycleOwner) {
            binding.textViewPlaceholder.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            mAdapter.submitList(it)
        }
    }

    private fun searchApiData(searchQuery: String) {
        mainViewModel.applySearchQuery(args.query)
        mainViewModel.searchBooks(searchQuery)
        showBookList()
    }

    companion object {
        private const val EMPTY_TEXT_FIELD = ""
        private const val DEFAULT_POSITION = 0
        private const val SEARCH_KEY = "search"
    }
}