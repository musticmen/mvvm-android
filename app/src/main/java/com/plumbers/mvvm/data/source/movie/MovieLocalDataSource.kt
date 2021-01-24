package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.common.Constants
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject

class MovieLocalDataSource
@Inject constructor(private val movieDao: MovieDao): MovieDataSource {

    override suspend fun getPopularMovies(page: Int): List<MovieModel> {
        val offset = (page - 1) * Constants.Movie.PAGE_LIMIT
        return movieDao.getPopularMovies(offset = offset)
    }

    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieDao.add(movies = movies)
    }
}