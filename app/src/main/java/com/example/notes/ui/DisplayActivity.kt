package com.example.notes.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.logic.Factory.DisplayViewModelFactory
import com.example.notes.logic.Factory.MainViewModelFactory
import com.example.notes.logic.ViewModel.DisplayViewModel
import com.example.notes.logic.ViewModel.MainViewModel
import com.example.notes.logic.model.NoteModel
import com.example.notes.ui.component.countView

class DisplayActivity : AppCompatActivity() {
    private val toolbar: Toolbar by lazy { findViewById(R.id.display_toolbar)}
    private lateinit var countView: countView
    private lateinit var name:TextView
    private lateinit var date:TextView
    private lateinit var state:TextView
    private lateinit var image:ImageView
    private lateinit var animator: ValueAnimator
    private lateinit var vm:DisplayViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        //
        vm= ViewModelProvider(this, DisplayViewModelFactory(this)).get(DisplayViewModel::class.java)
        countView= findViewById(R.id.count_View)
        name=findViewById(R.id.display_name)
        date=findViewById(R.id.display_num)
        state=findViewById(R.id.display_place)
        image=findViewById(R.id.display_image)
        //
        //Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {

        }
        //
        vm.setNote(intent.getSerializableExtra("Information") as NoteModel)
        countView.setmMaxStep(5000)
        countView.setmCurrentStep(vm.getNote().state.toFloat())
        animator = ObjectAnimator.ofFloat(100F)
        initAnimator()
        name.text=vm.getNote().name
        date.text=vm.getNote().date
        state.text=vm.getNote().state.toString()
        Glide.with(this).load(vm.getNote().image).into(image)
    }

    fun initAnimator(){
        animator.duration = 0
        animator.addUpdateListener { p0 ->
            val currentSteps: Float = p0?.animatedValue as Float
            countView.setmCurrentStep(currentSteps)
        }
        animator.start()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //
        val viewName: View = View.inflate(this,R.layout.alter_name_layout,null)
        val alterName=viewName.findViewById<EditText>(R.id.alter_name)
        when (item.itemId) {
            R.id.back -> {
                startActivity(Intent(this,MainActivity::class.java))
            }
            R.id.new_one -> {
                startActivity(Intent(this,TagsActivity::class.java))
            }
            R.id.edit -> {
                AlertDialog.Builder(this).apply {
                    setTitle("更改物品名字")
                    setView(viewName)
                    setMessage("输入新名称")
                    setCancelable(true)
                    setPositiveButton("是的") { dialog, which ->
                    val str=vm.edit(name = alterName.text.toString(),vm.getNote().id)
                    Toast.makeText(this@DisplayActivity,str,Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("取消") { dialog, which ->

                    }
                    show()
                }
            }
            R.id.delete ->{
                AlertDialog.Builder(this).apply {
                    setTitle("您确认要删除吗？")
                    setMessage("再次点击确认删除")
                    setCancelable(true)
                    setPositiveButton("是的") { dialog, which ->
                        val str=vm.delete(vm.getNote().id)
                        Toast.makeText(this@DisplayActivity,str,Toast.LENGTH_SHORT).show()
                        if (str=="SUCCESS"){
                            startActivity(Intent(this@DisplayActivity,MainActivity::class.java))
                        }
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