package com.pashacabu.testapp.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pashacabu.testapp.domain.repository.MoviesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesListRepository: MoviesListRepository
): ViewModel() {

    val movies = moviesListRepository.movies

    fun loadMovies(){
        viewModelScope.launch {
            moviesListRepository.loadMovies()
        }
    }

}