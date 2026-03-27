package com.example.classworkactivity.features.students_lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.classworkactivity.databinding.StudentRowLayoutBinding
import com.example.classworkactivity.models.Student

interface OnItemClickListener {
    fun onStudentItemClick(student: Student)
}

class StudentsAdapter (
     var students: MutableList<Student>?
): RecyclerView.Adapter<StudentRowViewHolder>() {

    var listener: OnItemClickListener? = null
    override fun getItemCount(): Int = students?.size ?: 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentRowViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = StudentRowLayoutBinding.inflate(inflator, parent, false)

        return StudentRowViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        holder: StudentRowViewHolder,
        position: Int
    ) {
        students?.let {
            holder.bind(it[position], position)
        }
    }



}