package com.example.classworkactivity.features.students_lists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classworkactivity.data.repositories.movies.RemoteMoviesRepository
import com.example.classworkactivity.models.Student
import com.example.classworkactivity.data.repositories.students.StudentsRepository
import com.example.classworkactivity.models.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentsListViewModel: ViewModel() {
    var data: LiveData<MutableList<Student>> = StudentsRepository.shared.getAllStudents()

    fun refreshStudents() {
        StudentsRepository.shared.refreshStudents()
    }

    fun getMovies (callback: (movies: Movies) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = RemoteMoviesRepository.shared.getTopRatedMovies()
             Log.i("TAG", "Movies: ${movies.results?.size}")

            withContext(Dispatchers.Main) {
                callback(movies)
            }
        }

    }
}