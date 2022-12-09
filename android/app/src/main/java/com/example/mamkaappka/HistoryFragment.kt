package com.example.mamkaappka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mamkaappka.databinding.FragmentHistoryBinding
import com.example.mamkaappka.databinding.OneDayPhotoBinding

class HistoryFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHistoryBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // setup the recyclerView & assign to its layout manager
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext() , 2) // two columns

        //assign the adapter property
        val daysPhotosAdapter = DaysPhotosAdapter(DaysPhotosAdapter.DaysPhotosListener { dayPhoto ->
            viewModel.setDetailDayPhoto(dayPhoto)
            Log.d("MAMKAPP","Clicked on ${dayPhoto.id}")
            viewModel.cleanTheErrorMessage()
            findNavController().navigate(R.id.action_historyFragment_to_todayFragment)

        })
        recyclerView.adapter = daysPhotosAdapter

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.allDayPhotos.observe(viewLifecycleOwner) { daysPhotos ->
            // Update the cached copy of the cities in the adapter.
            daysPhotos.let { daysPhotosAdapter.submitList(it) }
        }

        binding.sharedViewModel = viewModel             // this is necessary so we can use viewModel variables inside this fragment..
        viewModel.fetchAllDayPhotos()   // can I do it like this?
        binding.lifecycleOwner = this                   // + THIS ALSO!!! :) :) or = lifecycleOwner  (like in dashboard fragment -WHY?)
        binding.historyFragment = this@HistoryFragment  // and this!
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToToday(){
        viewModel.fetchTodayPhoto()
        findNavController().navigate(R.id.action_historyFragment_to_todayFragment)
    }

    //-------------
/*
    fun searchForBooks(){
        Log.d("Books","search")
        viewModel.getBooksByAuthor()
    }

 */
}