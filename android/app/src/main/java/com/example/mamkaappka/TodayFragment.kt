package com.example.mamkaappka

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mamkaappka.databinding.FragmentHistoryBinding
import com.example.mamkaappka.databinding.FragmentTodayBinding
import com.example.mamkaappka.databinding.OneDayPhotoBinding
import java.math.RoundingMode.valueOf
import java.sql.Date.valueOf
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TodayFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentTodayBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.sharedViewModel = viewModel             // this is necessary so we can use viewModel variables inside this fragment..
        binding.lifecycleOwner = this                   // + THIS ALSO!!! :) :) or = lifecycleOwner  (like in dashboard fragment -WHY?)
        binding.todayFragment = this@TodayFragment      // and this!

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToHistory(){
        findNavController().navigate(R.id.action_todayFragment_to_historyFragment)
    }




    //-------------
/*
    fun searchForBooks(){
        Log.d("Books","search")
        viewModel.getBooksByAuthor()
    }

 */
}