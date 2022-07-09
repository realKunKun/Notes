package com.example.notes.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.logic.model.NoteModel
import com.example.notes.R

class MainRecAdapter(val list:MutableList<NoteModel>)  : RecyclerView.Adapter<MainRecAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name:TextView=itemView.findViewById(R.id.nameMain)
    val time:TextView=itemView.findViewById(R.id.timeMain)
    val state:countView=itemView.findViewById(R.id.stateMain)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rec_main_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainRecAdapter.ViewHolder, position: Int) {
        holder.name.text=list[position].name
        holder.time.text= list[position].state.toString()
        holder.state.setmMaxStep(5000)
        holder.state.setmCurrentStep(list[position].state.toFloat())
        holder.name.setOnClickListener {
            itemClickListenner?.onItemClike(position)
        }
        holder.state.setOnClickListener {
            itemClickListenner?.onItemClike(position)
        }
        holder.time.setOnClickListener {
            itemClickListenner?.onItemClike(position)
        }
    }

    override fun getItemCount() = list.size

    //增添点击事件变量
    private var itemClickListenner: OnItemClickListenner? = null
    //构造点击事件
    fun setOnItemClickeListener(itemClickListenner: OnItemClickListenner) {
        this.itemClickListenner = itemClickListenner;
    }
    //将点击事件定义为接口
    public interface OnItemClickListenner {
        fun onItemClike(position: Int)
    }

}