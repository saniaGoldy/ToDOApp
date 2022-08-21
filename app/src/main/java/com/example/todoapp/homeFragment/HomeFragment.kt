package com.example.todoapp.homeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.todoapp.SharedViewModel
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.entities.ToDoItemEntity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter = ToDoListAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {

        binding.toDoList.adapter = adapter
        viewModel.isYellowThemeSelected.observe(this) {
            Log.d("MyApp", "colorObserver: $it")
            adapter.updateTextColor(it)
        }

        viewModel.toDoItemsList.observe(
            this
        ) { adapter.update(it) }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}