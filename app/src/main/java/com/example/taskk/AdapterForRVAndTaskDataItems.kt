package com.example.taskk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.taskk.databinding.RvLayoutForItemBinding

/*//1.
class AdapterForRVAndTaskDataItems :
//our adapter needs a ViewHolder, so we have to define a ViewHolder, we have defined here as inner class
    RecyclerView.Adapter<AdapterForRVAndTaskDataItems.ViewHolderForTaskDataItemInRV>() {*/

//changing the code than earlier (1.) because we are going to use ListAdapter which is of type
//RecyclerView.Adapter in order to use DiffUtil.ItemCallBack<Task>()
class AdapterForRVAndTaskDataItems(private val clickListener: (taskId: Long) -> Unit) :
    ListAdapter<Task, AdapterForRVAndTaskDataItems.ViewHolderForTaskDataItemInRV>(
        DiffUtilCallBackForTaskItem()
    ) {

    //3.
    //We are adding a data property and a custom setter that is internally calling a method
    //which will tell the recyclerview to redraw itself if the data has changed.
    //****UNDERSTAND THE WORKING OF CUSTOM SETTER******

    //This is was needed when RecyclerView>Adapter was being extended. Now When The
    //ListAdapter is being extended it provides its own implementation for getting the data
    /* var data = listOf<Task>()
         set(value) {
             field = value
             //earlier an error was getting formed but after creating a ViewHolder
             //and typecasting with the Adapter constructor the error got resolved
             //this is part of RecyclerView.Adapter
             notifyDataSetChanged()
         }*/

    //earlier an error was getting formed but after creating a ViewHolder
    //and typecasting with the Adapter constructor the error got resolved
    //this actually tells our adapter that how many data items are there in the data set
    //so the adapter can tell the RecyclerView that how many data it has to display.
    //This is was needed when RecyclerView.Adapter was being extended. Now When The
    //ListAdapter is being extended it provides its own implementation for getting the data
    // override fun getItemCount() = data.size

    //2.
    //ViewHolder getting defined as an Inner class
    class ViewHolderForTaskDataItemInRV(private val binding: RvLayoutForItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            //parent refers to the recyclerView
            //this method inflates the layout and then create a viewHolder with the inflated view and returns it.
            fun inflateFrom(parent: ViewGroup): ViewHolderForTaskDataItemInRV {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RvLayoutForItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolderForTaskDataItemInRV(binding)
            }
        }

        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            binding.dataVarForTask = item
            binding.root.setOnClickListener { iteMsVieWsRoot ->
                clickListener(item.taskId)
                /* Toast.makeText(
                     binding.root.context,
                     "Clicked item: ${item.taskName}",
                     Toast.LENGTH_SHORT
                 ).show()
 */
            }
        }
    }

    //this is called when recyclerView and adapter need a set of ViewHolders. This does
    //two things: 1. to inflate layouts for each ViewHolder it creates and then it returns viewHolder.
    //this method is called repeatedly for how many view holders it needs.
    //parent refers to The RecyclerView
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderForTaskDataItemInRV = ViewHolderForTaskDataItemInRV.inflateFrom(parent)

    override fun onBindViewHolder(holder: ViewHolderForTaskDataItemInRV, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    //override fun getItemCount() =
}