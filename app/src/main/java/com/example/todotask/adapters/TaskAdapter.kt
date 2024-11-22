package com.example.todotask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.example.todotask.R
import com.example.todotask.data.Task
import com.example.todotask.data.providers.TaskDAO
import com.example.todotask.databinding.ViewTaskItemBinding
import kotlinx.coroutines.delay
import java.util.Calendar


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

            val context = itemView.context

            binding.nameTextView.setText(task.name)
            binding.doneCheckBox.isChecked=task.done
            binding.hourTextView.setText(task.HourAlarm)

            //setColorCardViews(context.getColor(R.color.black),binding.cardTask,binding.cardHour)


            if(task.HourAlarm!="")
            {
                var restTimeTask = getHourRest(task)

                when {
                    restTimeTask > 20 ->  setColorCardViews(context.getColor(R.color.card_ExtraTime),binding.cardTask,binding.cardHour)
                    restTimeTask > 7  ->  setColorCardViews(context.getColor(R.color.card_HighTime),binding.cardTask,binding.cardHour)
                    restTimeTask > 3  ->  setColorCardViews(context.getColor(R.color.card_MiddleTime),binding.cardTask,binding.cardHour)
                    restTimeTask >= 0-> setColorCardViews(context.getColor(R.color.card_lowTime),binding.cardTask,binding.cardHour)
                    restTimeTask < 0 ->setColorCardViews(context.getColor(R.color.card_lowTime),binding.cardTask,binding.cardHour)
                    else->setColorCardViews(context.getColor(R.color.card_lowTime),binding.cardTask,binding.cardHour)
                }
            }


        }
        }

public fun setColorCardViews(color:Int, cardForTask:CardView,cardForHour:CardView)
{
    cardForTask.setCardBackgroundColor(color)
    cardForHour.setCardBackgroundColor(color)
}

public fun getHourRest(task:Task):Int
{
    var restTimeForTask=0
    val words = task.HourAlarm.split(":")

    var hourFirst=words[0].toInt()

    val calendar = Calendar.getInstance()

    calendar.get(Calendar.HOUR_OF_DAY)

    if(calendar.get(Calendar.HOUR_OF_DAY) >hourFirst) // En el caso de que la hora actual sea 19:00 y l alarma a las 2:00 necesitamos sumarle a las 2:00+24 para asi hacer la operacion bien
    {
        restTimeForTask=(hourFirst+24)-(calendar.get(Calendar.HOUR_OF_DAY))

    }
    else
    {
        restTimeForTask=hourFirst-calendar.get(Calendar.HOUR_OF_DAY)
    }

    return restTimeForTask
}


