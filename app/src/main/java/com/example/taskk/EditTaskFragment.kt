package com.example.taskk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskk.databinding.FragmentEditTaskBinding


class EditTaskFragment : Fragment() {
    private lateinit var viewModelEditTaskFrag: ViewModelEditTaskFrag
    private lateinit var factoryEditTaskFrVM: FactoryEditTaskFrVM

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(inflater)
        val view = binding.root

        val argumentFromTaskFragmentSafeArgs =
            EditTaskFragmentArgs.fromBundle(requireArguments()).taskID

        //?????? know more about this
        val application = requireNotNull(this.activity).application
        val dao = TaskDataBase.getInstance(application).taskDao

        //creating an instance of EditTaskFragViewModel
        factoryEditTaskFrVM = FactoryEditTaskFrVM(dao, argumentFromTaskFragmentSafeArgs)
        viewModelEditTaskFrag =
            ViewModelProvider(this, factoryEditTaskFrVM).get(ViewModelEditTaskFrag::class.java)

        binding.dataVarEditTskVM = viewModelEditTaskFrag
        binding.lifecycleOwner = viewLifecycleOwner


        viewModelEditTaskFrag.navigateBackToTaskF.observe(viewLifecycleOwner, Observer{
            this.findNavController().navigate(R.id.taskFragment)
            //??? why this is not working. The app is freezing in this case
           //editTaskFragViewModel.onNavigationBackToTaskFCompleted()
        })


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}