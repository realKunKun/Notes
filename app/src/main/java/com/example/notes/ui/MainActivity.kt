package com.example.notes.ui


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.logic.Factory.MainViewModelFactory
import com.example.notes.logic.ViewModel.MainViewModel
import com.example.notes.logic.model.GlobalValue
import com.example.notes.ui.component.MainRecAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var mRV:RecyclerView
    private lateinit var vm : MainViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var mtext:TextView
    private lateinit var mAddNote:ImageView
    private lateinit var mBtm:Button
    private lateinit var mPC:ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        if (!GlobalValue.accessState){
            startActivity(Intent(this,LogInActivity::class.java))
        }else{
            Toast.makeText(this,"Access Successfully", Toast.LENGTH_SHORT).show()
        }
        //Toolbar
        toolbar=findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        //绑定
        mtext=findViewById(R.id.textMain)
        mAddNote=findViewById(R.id.main_pet)
        mBtm=findViewById(R.id.main_LogOut)
        mPC=findViewById(R.id.main_User)
        mtext.text="点击我创建新物品"
        //添加
        mAddNote.setOnClickListener {
            startActivity(Intent(this,TagsActivity::class.java))
        }
        //跳转个人中心
        mPC.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }
        //ViewModel
        mRV=findViewById(R.id.mainRV)
        val layoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        mRV.layoutManager = layoutManager
        vm = ViewModelProvider(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        Thread.sleep(2000)
        val adapter= MainRecAdapter(vm.noteList)
        mRV.adapter=adapter
        adapter.setOnItemClickeListener(object : MainRecAdapter.OnItemClickListenner{
            override fun onItemClike(position: Int) {
                var intent = Intent(this@MainActivity, DisplayActivity::class.java)
                intent.putExtra("Information", vm.noteList[position])
                startActivityForResult(intent,0)
            }
        })
        //退出
        mBtm.setOnClickListener {
            var responseCode=vm.logOut()
                if (responseCode==200)Toast.makeText(this,"成功退出",Toast.LENGTH_SHORT).show()
                else Toast.makeText(this,"退出失败,请检查网络",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon -> {
                startActivity(Intent(this,TagsActivity::class.java))
            }
            R.id.modeSwitch ->{
                startActivity(Intent(this,NavActivity::class.java))
            }
        }
        return true
    }
}