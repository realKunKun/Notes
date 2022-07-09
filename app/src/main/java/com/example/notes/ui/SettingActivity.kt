package com.example.notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.notes.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import android.widget.RelativeLayout




class SettingActivity : AppCompatActivity() {
    private var relativeLayout: RelativeLayout? = null

    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_layout)
        //
        initViews()
        //
        val aboutPage = AboutPage(this)
        var v: View? =null
        aboutPage.isRTL(false)
            .setImage(R.drawable.user)//图片
            .setDescription("道理我都懂，可我就是不听啊")//介绍
            .addItem( Element().setTitle("Version 1.0"))
            .addGroup("与我联系")
            .addEmail("zhaoweihaochn@foxmail.com")//邮箱
            .addWebsite("http://zhaoweihao.me")//网站
            .addPlayStore("com.example.abouttest")//应用商店
            .addGitHub("zhaoweihaoChina")//github
            .create()
        v=aboutPage.create()
        //setContentView(v)
        relativeLayout?.addView(v);
    }
    private fun initViews() {
        relativeLayout = findViewById(R.id.relativeLayout);
        toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back -> {

            }
            R.id.modeSwitch ->{

            }
        }
        return true
    }

}