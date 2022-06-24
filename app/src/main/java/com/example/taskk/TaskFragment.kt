package com.example.taskk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskk.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private lateinit var taskFragmentViewModel: ViewModelTaskFragment
    private lateinit var taskVmFactory: FactoryTaskFViewModel


    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    //We need to modify the code in TaskFragment,the Adapter class and the ViewHolder code in the Adapter class
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater)
        val view = binding.root


        //?????? know more about this
        val application = requireNotNull(this.activity).application
        val dao = TaskDataBase.getInstance(application).taskDao

        //creating an instance of ViewModel
        taskVmFactory = FactoryTaskFViewModel(dao)
        taskFragmentViewModel =
            ViewModelProvider(this, taskVmFactory).get(ViewModelTaskFragment::class.java)

        //connecting Recycler View with the Adapter.
        val adapterForRVAndTaskDataItems = AdapterForRVAndTaskDataItems() { taskId ->
            taskFragmentViewModel.onTaskClicked(taskId)
            //Toast.makeText(context, "**Item clicked**: $taskId", Toast.LENGTH_SHORT).show()
        }
        binding.rvForTaskList.adapter = adapterForRVAndTaskDataItems



        //for data binding.
        binding.dataVarTaskFVM = taskFragmentViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //observing tasks property in TaskFragmentViewModel which holds list of Task data from DataBase
        //and is being retrieved for displaying.
        taskFragmentViewModel.tasks.observe(viewLifecycleOwner, Observer { listOfTasks ->
            adapterForRVAndTaskDataItems.submitList(listOfTasks)
            if (listOfTasks.isEmpty()){
                binding.linearLayoutHoldingRVId.visibility = View.INVISIBLE
                binding.emptyViewLinearLid.visibility = View.VISIBLE
            }else{
                binding.linearLayoutHoldingRVId.visibility = View.VISIBLE
                binding.emptyViewLinearLid.visibility = View.INVISIBLE

            }
        })


        /*  binding.saveTaskButton.setOnClickListener {
              taskFragmentViewModel.addTask()
              binding.taskInputAreaId.text = null
              //navController.navigate(R.id.action_taskFragment_to_editTaskFragment)
          }*/
        taskFragmentViewModel.taskIdOnNavigation.observe(
            viewLifecycleOwner,
            Observer { taskId ->
                //let will only allow the block to run if taskId is not null
                taskId?.let {
                    val action =
                    //if we dont use .let{} then the following code gives an error:
                        //Type mismatch: inferred type is Long? but Long was expected
                        TaskFragmentDirections.actionTaskFragmentToEditTaskFragment(taskId)
                    this.findNavController().navigate(action)
                    taskFragmentViewModel.onTaskNavigated()
                }

            })

        binding.floatingActionButton.setOnClickListener {
            val action = TaskFragmentDirections.actionTaskFragmentToAddTaskFragment()
            this.findNavController().navigate(action)
        }


        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}