package com.example.notes.ui

import android.animation.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.logic.Factory.LogInViewModelFactory
import com.example.notes.logic.Factory.RegisterVIewModelFactory
import com.example.notes.logic.ViewModel.LogInViewModel
import com.example.notes.logic.ViewModel.RegisterViewModel
import com.example.notes.logic.model.GlobalValue
import com.example.notes.logic.model.UserModel
import com.example.notes.ui.component.JellyInterpolator
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBtnLogin:TextView
    private lateinit var progress: View
    private lateinit var mInputLayout:View
    private lateinit var mTitle:View
    private var mWidth:Float = 0.0f
    private var mHeight:Float = 0.0f
    private lateinit var mName: LinearLayout
    private lateinit var mPsw: LinearLayout
    private lateinit var vm : RegisterViewModel
    private lateinit var name: EditText
    private lateinit var password: EditText
    private  var isClick:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
       // requestWindowFeature(Window.FEATURE_NO_TITLE)
        initView()
    }
    fun initView(){
        vm = ViewModelProvider(this, RegisterVIewModelFactory(this)).get(RegisterViewModel::class.java)
        mBtnLogin = findViewById(R.id.main_btn_login)
        progress = findViewById(R.id.layout_progress)
        mInputLayout = findViewById(R.id.input_layout2)
        mName = findViewById(R.id.input_layout_name)
        mPsw = findViewById(R.id.input_layout_psw)
        name=mInputLayout.findViewById(R.id.register_name)
        password=mInputLayout.findViewById(R.id.register_password)
        mBtnLogin.setOnClickListener(this)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v:View){
        if (!isClick){
            //set VM USER
            vm.setUser(UserModel(name.text.toString(),password.text.toString()),this)
            // 计算出控件的高与宽
            mWidth = mBtnLogin.measuredWidth.toFloat()
            mHeight = mBtnLogin.measuredHeight.toFloat()
            // 隐藏输入框
            mName.visibility = View.INVISIBLE
            mPsw.visibility = View.INVISIBLE
            inputAnimator(mInputLayout, mWidth, mHeight)
            isClick=true
            Toast.makeText(this,vm.state, Toast.LENGTH_SHORT).show()
        }else{
            recovery()
            isClick=false
        }
        thread {
            Thread.sleep(2000)
            if (GlobalValue.accessState&&isClick){
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }
    private fun inputAnimator(view:View, w:Float, h:Float ){
        val set = AnimatorSet()
        val animator = ValueAnimator.ofFloat(0F, w)

        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener() {

            @Override
            fun onAnimationUpdate(animation: ValueAnimator) {
                val value:Float = animation.getAnimatedValue() as Float
                val params: ViewGroup.MarginLayoutParams = view.getLayoutParams() as ViewGroup.MarginLayoutParams
                params.leftMargin = value.toInt()
                params.rightMargin = value.toInt()
                view.setLayoutParams(params)
            }
        })

        val  animator2: ObjectAnimator = ObjectAnimator.ofFloat(mInputLayout,"scaleX", 1f, 0.5f)
        set.setDuration(1000)
        set.setInterpolator(AccelerateDecelerateInterpolator())
        set.playTogether(animator, animator2)
        set.start()
        set.addListener(
            object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }
                override fun onAnimationRepeat(animation: Animator) {

                }
                override fun onAnimationEnd(animation: Animator) {
                    progress.setVisibility(View.VISIBLE)
                    progressAnimator(progress)
                    mInputLayout.setVisibility(View.INVISIBLE)

                }
                override fun onAnimationCancel(animation: Animator) {

                }
            }
        )
    }
    private fun progressAnimator(view:View) {
        val animator: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleX",0.5f, 1f)
        val animator2: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY",0.5f, 1f)
        val animator3: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(view,
            animator, animator2)
        animator3.setDuration(1000)
        animator3.setInterpolator(JellyInterpolator())
        animator3.start()
    }
    //加载失败时恢复初始状态
    private fun recovery(){
        progress.setVisibility(View.GONE)
        mInputLayout.setVisibility(View.VISIBLE)
        mName.setVisibility(View.VISIBLE)
        mPsw.setVisibility(View.VISIBLE)

        val params: ViewGroup.MarginLayoutParams = mInputLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.leftMargin = 0
        params.rightMargin = 0
        mInputLayout.setLayoutParams(params)


        val animator2: ObjectAnimator = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f )
        animator2.duration = 500
        animator2.interpolator = AccelerateDecelerateInterpolator()
        animator2.start()
    }
}