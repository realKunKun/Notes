package com.example.notes.ui


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.logic.Factory.DetailViewModelFactory
import com.example.notes.logic.ViewModel.DetailViewModel
import com.example.notes.logic.model.NoteModel
import com.example.notes.logic.model.TagModel


class DetailActivity : AppCompatActivity() {
    private lateinit var mInputLayout: View
    private lateinit var name:EditText
    private lateinit var startTime:EditText
    private lateinit var endTime:EditText
    private lateinit var num:EditText
    private lateinit var info:EditText
    private lateinit var url:EditText
    private lateinit var yesBtm:ImageView
    private lateinit var noBtm:ImageView
    private lateinit var vm:DetailViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
       // val json=Gson().toJson(GlobalValue.testClassList)
       // Log.d("JsonData:  ",json)
        //

        mInputLayout=findViewById(R.id.detail_layout)
        yesBtm=findViewById(R.id.detail_yes)
        noBtm=findViewById(R.id.detail_no)
        startTime=mInputLayout.findViewById(R.id.detail_startTime)
        endTime=mInputLayout.findViewById(R.id.detail_endTime)
        num=mInputLayout.findViewById(R.id.detail_num)
        info=mInputLayout.findViewById(R.id.detail_introduce)
        name=mInputLayout.findViewById(R.id.detail_name)
        url=mInputLayout.findViewById(R.id.detail_url)
        //
        vm =  ViewModelProvider(this,DetailViewModelFactory(this)).get(DetailViewModel::class.java)
        vm.SetTag(intent.getSerializableExtra("Information") as TagModel)
        //
        Toast.makeText(this,"每一行都不能为空哦",Toast.LENGTH_LONG).show()
        //
        yesBtm.setOnClickListener {
            vm.setDate(endTime = endTime.text.toString(), startTime = startTime.text.toString())
            vm.setNum(num.text.toString().toInt())
            vm.setInfo(info.text.toString())
            vm.setName(name.text.toString())
            vm.setUrl(url.text.toString())
            val str=vm.upload()
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
        }
        //
        noBtm.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

}