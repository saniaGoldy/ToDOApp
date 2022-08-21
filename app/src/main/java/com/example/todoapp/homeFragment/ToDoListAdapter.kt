package com.example.todoapp.homeFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.entities.ToDoItemEntity
import com.example.todoapp.databinding.TodoListItemBinding

class ToDoListAdapter(private var itemsData: MutableList<ToDoItemEntity>, private var isAlternativeThemeSelected: Boolean = false) :
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        TodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemsData[position], position)

    override fun getItemCount(): Int = itemsData.size

    fun update(itemsData: MutableList<ToDoItemEntity>) {
        this.itemsData = itemsData
        notifyDataSetChanged()
    }

    fun updateTextColor(altThemeSelected: Boolean){
        isAlternativeThemeSelected = altThemeSelected
        Log.d("MyApp", "updateTextColor:$altThemeSelected")
        update(itemsData)
    }

    private fun deleteItem(position: Int) {
        itemsData.removeAt(position)
        this.notifyItemRemoved(position)
    }

    inner class ViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val itemContent = binding.toDoItemContent

        fun bind(item: ToDoItemEntity, position: Int) {
            with(itemContent) {
                text = item.content
                isChecked = item.isChecked

                setOnClickListener {
                    if (isChecked) {
                        isChecked = !isChecked
                        setCheckMarkDrawable(R.drawable.ic_baseline_radio_button_unchecked_24)
                    }else{
                        isChecked = !isChecked
                        setCheckMarkDrawable(R.drawable.ic_baseline_check_24)
                    }

                }

                if(isAlternativeThemeSelected){
                    setTextColor(resources.getColor(R.color.yellow, null))
                }
            }

            binding.deleteButton.setOnClickListener { deleteItem(position) }
        }
    }
}