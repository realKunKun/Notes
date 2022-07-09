package com.example.notes.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.logic.Factory.TagsViewModelFactory
import com.example.notes.logic.ViewModel.TagsViewModel
import com.example.notes.ui.component.MainRecAdapter
import com.example.notes.ui.component.TagsRecAdapter

class TagsActivity : AppCompatActivity() {
    private val toolbar:Toolbar by lazy { findViewById(R.id.tags_toolbar)}
    private lateinit var mRv:RecyclerView
    private lateinit var vm:TagsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)
        //Toolbar
        setSupportActionBar(toolbar)
        //
        vm= ViewModelProvider(this, TagsViewModelFactory(this)).get(TagsViewModel::class.java)
        //
        mRv=findViewById(R.id.tags_RV)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        mRv.layoutManager = layoutManager
        /*
        vm.liveTagData.observe(this, Observer {
            val adapter = TagsRecAdapter(it)
            mRv.adapter = adapter
        })
         */
        val adapter = TagsRecAdapter(vm.tag)
        mRv.adapter = adapter
        adapter.setOnItemClickeListener(object : TagsRecAdapter.OnItemClickListenner{
            override fun onItemClike(position: Int) {
                var intent = Intent(this@TagsActivity, DetailActivity::class.java)
                intent.putExtra("Information", vm.tag[position])
                startActivityForResult(intent,0)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view:View= View.inflate(this,R.layout.alertdialog_input,null)
        val tableName=view.findViewById<EditText>(R.id.alert_name)
        val tableId=view.findViewById<EditText>(R.id.alert_id)
        //
        val viewId:View=View.inflate(this,R.layout.alter_id_layout,null)
        val alterId=viewId.findViewById<EditText>(R.id.alter_id)
        //
        val viewName:View=View.inflate(this,R.layout.alter_name_layout,null)
        val alterName=viewName.findViewById<EditText>(R.id.alter_name)
        when (item.itemId) {
            R.id.back -> {
            startActivity(Intent(this,MainActivity::class.java))
            }
            R.id.new_one -> {
                AlertDialog.Builder(this).apply {
                    setTitle("新建TAG")
                    setView(viewName)
                    setMessage("请输入TAG名")
                    setCancelable(true)
                    setPositiveButton("确认") { dialog, which ->
                        vm.newTag(alterName.text.toString())
                        mRv.adapter?.notifyDataSetChanged()
                    }
                    setNegativeButton("取消") { dialog, which ->

                    }
                    show()

                }
            }
            R.id.edit -> {
                AlertDialog.Builder(this).apply {
                    setTitle("编辑TAG")
                    setView(view)
                    setMessage("请输入新的TAG名")
                    setCancelable(true)
                    setPositiveButton("确认") { dialog, which ->
                        val str=vm.edit(tableName.text.toString(),tableId.text.toString())
                        Toast.makeText(this@TagsActivity,str,Toast.LENGTH_SHORT).show()
                        mRv.adapter?.notifyDataSetChanged()
                    }
                    setNegativeButton("取消") { dialog, which ->

                    }
                    show()
                }
            }
            R.id.delete ->{
                AlertDialog.Builder(this).apply {
                    setTitle("s删除TAG")
                    setView(viewId)
                    setMessage("请输入新的TAG名")
                    setCancelable(true)
                    setPositiveButton("确认") { dialog, which ->
                        val str=vm.delete(alterId.text.toString())
                        Toast.makeText(this@TagsActivity,str,Toast.LENGTH_SHORT).show()
                        mRv.adapter?.notifyDataSetChanged()
                    }
                    setNegativeButton("取消") { dialog, which ->

                    }
                    show()
                }

            }
        }
        return true
    }
}