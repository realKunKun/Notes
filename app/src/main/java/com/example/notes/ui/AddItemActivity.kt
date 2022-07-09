package com.example.notes.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.logic.ViewModel.AddItemViewModel
import com.example.notes.logic.ViewModel.MainViewModel
import com.example.notes.logic.model.GlobalValue
import java.io.File

class AddItemActivity : AppCompatActivity() {
    private val takePhoto = 1
    private val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    lateinit var takePhotoBtn:ImageView
    private lateinit var itemImage:ImageView
    lateinit var placeImage:ImageView
    private var itemTake=false
    lateinit var yesBtm:ImageView
    lateinit var noBtm:ImageView
    private lateinit var vm:AddItemViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        这个页面是用于上传识别的，发送图片给后端来反馈
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        //
        vm = ViewModelProvider(this).get(AddItemViewModel::class.java)
        //
            takePhotoBtn=findViewById(R.id.add_camera)
            itemImage=findViewById(R.id.add_item)
            placeImage=findViewById(R.id.add_place)
            yesBtm=findViewById(R.id.add_yes)
            noBtm=findViewById(R.id.add_no)
        //
        takePhotoBtn.setOnClickListener {
            // 创建File对象，用于存储拍照后的图片
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, "com.example.notes.fileprovider", outputImage)
            } else {
                Uri.fromFile(outputImage)
            }
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
            }
        //
        itemImage.setOnClickListener {
            // 打开文件选择器
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 指定只显示图片
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }
        //
        placeImage.setOnClickListener {
                // 打开文件选择器
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                // 指定只显示图片
                intent.type = "image/*"
                startActivityForResult(intent, fromAlbum)
            }
        //
        yesBtm.setOnClickListener {
            vm.uploadImage(this)
            Thread.sleep(1000)
            startActivity(Intent(this,DetailActivity::class.java))
        }
        noBtm.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(contentResolver.
                    openInputStream(imageUri))
                    GlobalValue.uri=imageUri
                    if (!itemTake){
                        itemImage.setImageBitmap(rotateIfRequired(bitmap))
                        itemTake=true
                    }
                    else{
                        placeImage.setImageBitmap(rotateIfRequired(bitmap))
                    }
                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的图片显示
                        val bitmap = getBitmapFromUri(uri)
                        if (!itemTake){
                            itemImage.setImageBitmap(bitmap)
                            itemTake=true
                        }
                        else {
                            placeImage.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }
    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true)
        bitmap.recycle() // 将不再需要的Bitmap对象回收
        return rotatedBitmap
    }
    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor) }
}