package com.pashacabu.testapp.ui.movieslist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pashacabu.testapp.data.model.ApiField
import com.pashacabu.testapp.domain.repository.MoviesListRepository
import com.pashacabu.testapp.extensions.asState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesListRepository: MoviesListRepository
) : ViewModel() {

    val movies = moviesListRepository.movies
    val refreshOnly = derivedStateOf { movies.value.isEmpty() }

    private var _loading = mutableStateOf(false)
    val loading = _loading.asState()

    private val _searchEnabled = mutableStateOf(false)
    val searchEnabled = _searchEnabled.asState()
    private val _searchField = MutableStateFlow("")
    val searchField = _searchField.asStateFlow()
    private var searchJob: Job? = null

    fun loadMovies(initialLoad: Boolean = false, reload: Boolean = false) {
        if (initialLoad && movies.value.isEmpty() && !_searchEnabled.value) {
            viewModelScope.launch {
                _loading.value = true
                moviesListRepository.loadMovies(reload)
                _loading.value = false
            }
        }
        if (!initialLoad){
            viewModelScope.launch {
                _loading.value = true
                moviesListRepository.loadMovies(reload)
                _loading.value = false
            }
        }
    }

    fun updateSearchField(value: String) {
        _searchField.value = value
    }

    fun enableSearch(enable: Boolean) {
        if (enable) {
            _searchEnabled.value = true
            startSearching(true)
        } else {
            if (_searchField.value.isNotEmpty()) {
                _searchField.value = ""
            } else {
                _searchEnabled.value = false
                startSearching(false)
            }
        }

    }

    @OptIn(FlowPreview::class)
    private fun startSearching(enable: Boolean) {
        if (enable) {
            searchJob = viewModelScope.launch {
                _searchField
                    .debounce(500)
                    .filter { it.isNotEmpty() }
                    .collectLatest {
//                        Repo search(_searchField.value)
                        _loading.value = true
                        moviesListRepository.searchMovies(field = ApiField.NAME, cond = it)
                        _loading.value = false
                    }
            }
        } else {
            searchJob?.cancel()
            searchJob = null
            moviesListRepository.disableSearch()
        }
    }

}