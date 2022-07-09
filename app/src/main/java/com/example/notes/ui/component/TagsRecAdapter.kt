package com.example.notes.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

import com.example.notes.logic.model.TagModel

class TagsRecAdapter(val list:MutableList<TagModel>)  : RecyclerView.Adapter<TagsRecAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView =itemView.findViewById(R.id.tags_text)
        val num:TextView=itemView.findViewById(R.id.tags_num)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsRecAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rec_tag_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsRecAdapter.ViewHolder, position: Int) {
        holder.num.text= list[position].id.toString()
        holder.name.text=list[position].tag
        holder.name.setOnClickListener {
            itemClickListenner?.onItemClike(position)
        }
    }

    override fun getItemCount()=list.size
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