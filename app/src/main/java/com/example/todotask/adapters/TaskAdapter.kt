package com.example.todotask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.todotask.data.Task
import com.example.todotask.data.providers.TaskDAO
import com.example.todotask.databinding.ViewTaskItemBinding
import kotlinx.coroutines.delay


class TaskAdapter(private var taskList:List<Task>,private val onItemClickListener: (Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>() {


    fun setFilteredList(filterList: List<Task>) {
        this.taskList = filterList
        notifyDataSetChanged()

    }

    fun updateItems(items: List<Task>) {
        this.taskList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //De Context coge a la vista de su padre que lo ha creado.


        val binding =
            ViewTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun getItemCount() = taskList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.bind(taskList[position],position)

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }

    }
}
class ViewHolder(private val binding: ViewTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root)
{
        fun bind(task: Task, pos: Int) {

            binding.nameTextView.setText(task.name)
            binding.doneCheckBox.isChecked=task.done
            binding.hourTextView.setText(task.HourAlarm)

           // var checked =binding.doneCheckBox.isChecked

        }
        }


