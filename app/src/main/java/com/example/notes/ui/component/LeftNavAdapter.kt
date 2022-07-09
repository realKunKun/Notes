package com.example.notes.ui.component

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.logic.model.TagModel
import com.example.notes.ui.NavActivity

class LeftNavAdapter(val nav: NavActivity, private val tagList: List<TagModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListenner: OnItemClickListenner? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goodsTypeItemHolder = holder as TagItemHolder
        goodsTypeItemHolder.bindData(tagList.get(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(nav).inflate(R.layout.rec_nav_left, parent, false)
        return TagItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    var selectPosition = 0 //选中的位置
    inner class TagItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val tvType: TextView
        var mPosition: Int = 0
        lateinit var goodsTypeInfo: TagModel

        init {
            tvType = item.findViewById(R.id.type)
            item.setOnClickListener {
                selectPosition = mPosition
                notifyDataSetChanged()
                //step2:右侧列表跳转到该类型中第一个商品
                val typeId = goodsTypeInfo.id
                //遍历所有商品，找到此position
                val position = nav.getGoodsPositionByTypeId(typeId)
                nav.slhlv.setSelection(position)
            }
        }

        fun bindData(tag: TagModel, position: Int) {
            mPosition = position
            this.goodsTypeInfo = tag
            if (position == selectPosition) {
                //选中的为白底加粗黑字，
                item.setBackgroundColor(Color.WHITE)
                tvType.setTextColor(Color.BLACK)
                tvType.setTypeface(Typeface.DEFAULT_BOLD)
            } else {
                //未选中是灰色背景 普通字体
                item.setBackgroundColor(Color.parseColor("#b9dedcdc"))
                tvType.setTextColor(Color.GRAY)
                tvType.setTypeface(Typeface.DEFAULT)
            }
            tvType.text = tag.tag
        }
    }

    fun setOnItemClickeListener(itemClickListenner: OnItemClickListenner) {
        this.itemClickListenner = itemClickListenner;
    }
    //将点击事件定义为接口
    public interface OnItemClickListenner {
        fun onItemClike(position: Int)
    }

}