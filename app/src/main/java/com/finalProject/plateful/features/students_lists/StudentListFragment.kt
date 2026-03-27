package com.example.classworkactivity.features.students_lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.classworkactivity.databinding.FragmentStudentListBinding
import com.example.classworkactivity.models.Student

class StudentListFragment : Fragment() {
    private var binding: FragmentStudentListBinding? = null
    private val viewModel: StudentsListViewModel by viewModels()
    private var adapter: StudentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        refreshStudents()
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layout

        binding?.recyclerView?.setHasFixedSize(true)

         adapter = StudentsAdapter(viewModel.data.value)

        adapter?.listener = object: OnItemClickListener {

            override fun onStudentItemClick(student: Student) {
                navigateToPinkFragment(student)
            }
        }

        binding?.recyclerView?.adapter = adapter

        binding?.swipeRefresh?.setOnRefreshListener {
            binding?.swipeRefresh?.isRefreshing = true
            refreshStudents()
        }

        observeStudents()

    }

    private fun observeStudents() {
//        fetchMovies()
        viewModel.data.observe(viewLifecycleOwner) {
            adapter?.students = it
            adapter?.notifyDataSetChanged()
            binding?.swipeRefresh?.isRefreshing = false

        }
    }

    private fun fetchMovies() {
        viewModel.getMovies { movies ->
            adapter?.students = movies.results?.map { movie ->
                Student(
                    name = movie.title ?: "Unknown",
                    id = movie.id.toString(),
                    isPresent = false,
                    avatarUrlString = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    lastUpdated = System.currentTimeMillis()
                )
            }?.toMutableList()

            adapter?.notifyDataSetChanged()
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    private fun refreshStudents() {
        viewModel.refreshStudents()
    }

    private fun navigateToPinkFragment(student: Student){

        view?.let {
            val action = StudentListFragmentDirections.actionStudentListFragmentToBlueFragment(student.name)
            Navigation.findNavController(it).navigate(action)
        }

    }
}