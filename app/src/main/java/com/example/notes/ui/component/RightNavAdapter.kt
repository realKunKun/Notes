package com.example.notes.ui.component

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.notes.R
import com.example.notes.logic.model.NoteModel
import com.example.notes.ui.NavActivity
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

class RightNavAdapter(private val nav: NavActivity, private val noteList: List<NoteModel>): BaseAdapter(), StickyListHeadersAdapter {
    override fun getCount(): Int {
        return noteList.size
    }

    override fun getItem(p0: Int): Any {
        return noteList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var itemView: View
        val noteItemHolder: NoteItemHolder
        if (p1 == null) {
            itemView = LayoutInflater.from(nav).inflate(R.layout.rec_nav_right,p2,false)
            noteItemHolder = NoteItemHolder(itemView)
            itemView.tag = noteItemHolder
        } else {
            itemView = p1
            noteItemHolder = p1.tag as NoteItemHolder
        }
        noteItemHolder.bindData(noteList[p0])
        return itemView

    }

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val note: NoteModel = noteList[position]
        val typeName = note.tag
        val textView: TextView = LayoutInflater.from(nav).inflate(R.layout.item_type_header, parent, false) as TextView
        textView.text = typeName
        textView.setTextColor(Color.BLACK)
        return textView

    }

    override fun getHeaderId(position: Int): Long {
        val goodsInfo: NoteModel = noteList[position]
        return goodsInfo.tagId.toLong()
    }

    inner class NoteItemHolder(itemView: View) {
        val tvName: TextView
        val tvForm: TextView
        val tvCount: TextView
        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvForm = itemView.findViewById(R.id.tv_form)
            tvCount = itemView.findViewById(R.id.tv_count)
        }

        fun bindData(note: NoteModel) {
            tvName.text = note.name
            tvForm.text = note.date
            tvCount.text = note.info
        }
    }
}