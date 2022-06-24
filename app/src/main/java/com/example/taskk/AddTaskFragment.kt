package com.example.taskk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskk.databinding.FragmentAddTaskBinding


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private var viewModelAddTaskF: ViewModelAddTaskF? = null
    private var factoryEditTaskFrVM: FactoryAddTaskFVM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTaskBinding.inflate(inflater)
        val view = binding.root


        //this is for getting an instance of the database
        val application = requireNotNull(this.activity).application
        val dao = TaskDataBase.getInstance(application).taskDao
        //instance of ViewModelAddTaskF
        factoryEditTaskFrVM = FactoryAddTaskFVM(dao)
        viewModelAddTaskF =
            ViewModelProvider(this, factoryEditTaskFrVM!!).get(ViewModelAddTaskF::class.java)


        binding.dataVarAddTaskVM = viewModelAddTaskF
        binding.lifecycleOwner = viewLifecycleOwner

        binding.saveButt.setOnClickListener {
            viewModelAddTaskF!!.addTask()
        }


        viewModelAddTaskF!!.onSaveNavigateToTaskF.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
            //viewModelAddTaskF!!.onNavigationToTaskF()
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}