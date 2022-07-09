package com.example.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.logic.Factory.MainViewModelFactory
import com.example.notes.logic.Factory.NavViewHolderFactory
import com.example.notes.logic.ViewModel.MainViewModel
import com.example.notes.logic.ViewModel.NavViewModel
import com.example.notes.logic.model.NoteModel
import com.example.notes.ui.component.LeftNavAdapter
import com.example.notes.ui.component.RightNavAdapter
import se.emilsjolander.stickylistheaders.StickyListHeadersListView

class NavActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    lateinit var mRv: RecyclerView
    lateinit var slhlv: StickyListHeadersListView
    lateinit var notesAdapter: RightNavAdapter
    lateinit var tagsAdapter : LeftNavAdapter
    private lateinit var vm : NavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        //Toolbar
        toolbar=findViewById(R.id.nav_toolbar)
        setSupportActionBar(toolbar)

        mRv = findViewById(R.id.rv_goods_type)
        slhlv = findViewById(R.id.slhlv)
        vm = ViewModelProvider(this, NavViewHolderFactory(this)).get(NavViewModel::class.java)
        vm.liveTagData.observe(this, Observer {
            tagsAdapter = LeftNavAdapter(this, it)
            mRv.adapter = tagsAdapter
        })
            notesAdapter = RightNavAdapter(this, vm.noteList)
            slhlv.adapter = notesAdapter
            mRv.layoutManager = LinearLayoutManager(this)

        slhlv.setOnScrollListener(object  : AbsListView.OnScrollListener{

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                //先找出旧的类别
                val oldPosition = tagsAdapter.selectPosition

                val newTypeId = vm.noteList[firstVisibleItem].tagId
                //val newTypeId = noteList.get(firstVisibleItem).tagId

                //把新的id找到它对应的position
                val newPositon = getTypePositionByTypeId(newTypeId)
                //当newPositon与旧的不同时，证明需要切换类别了
                if(newPositon!=oldPosition){
                    tagsAdapter.selectPosition = newPositon
                    tagsAdapter.notifyDataSetChanged()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

                }
            })

    }


    //根据类别id找到其在左侧列表中的position
    fun getTypePositionByTypeId(newTypeId: Int):Int {
        var position = -1 //-1表示未找到
        vm.liveTagData.observe(this, Observer {
            for(i in 0 until  it.size){
                val tag = it[i]
                if(tag.id == newTypeId){
                    position = i
                    break
                }
            }
        })
        return position
    }

    //根据商品类别id找到此类别第一个商品的位置
    fun getGoodsPositionByTypeId(typeId: Int): Int {
        var position = -1 //-1表示未找到
            for(j in 0 until  vm.noteList.size) {
                val note = vm.noteList[j]
                if (note.tagId == typeId) {
                    position = j
                    break
                }
            }
        return position
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.modeSwitch ->{
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
        return true
    }
}