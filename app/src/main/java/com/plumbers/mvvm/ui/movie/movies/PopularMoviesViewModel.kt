package com.plumbers.mvvm.ui.movie.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.data
import com.plumbers.mvvm.common.data.succeeded
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.domain.GetPopularMoviesResultUseCase
import com.plumbers.mvvm.domain.PopularMoviesParameter
import kotlinx.coroutines.launch

class PopularMoviesViewModel @ViewModelInject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesResultUseCase
) : ViewModel() {

    val moviesLiveData = MutableLiveData<Result<Any>>()
    var movieList = mutableListOf<MovieModel>()
    private var nextPage = 1

    fun getMovies(showLoading: Boolean = true) {
        if (showLoading) {
            moviesLiveData.value = Result.Loading
        }
        viewModelScope.launch {
            val params = PopularMoviesParameter(page = nextPage)
            getPopularMoviesUseCase(parameters = params).apply {
                moviesLiveData.value = this
                if (succeeded) {
                    movieList.addAll(data!!)
                    nextPage++
                }
            }
        }
    }
}
