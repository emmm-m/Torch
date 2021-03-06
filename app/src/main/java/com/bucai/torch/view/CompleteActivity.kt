package com.bucai.torch.view

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.bucai.torch.R
import com.bucai.torch.util.leancloud.IUserModel
import com.bucai.torch.util.leancloud.UserModel
import com.bucai.torch.view.base.BaseActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_complete.*

class CompleteActivity : BaseActivity() {
    val model: IUserModel = UserModel()
    private val GET_IMAGE = 1
    private val GET_FIXED_IMAGE = 2
    private var image: Bitmap? = null
    private var dialog : ProgressDialog? = null
    override val contentViewId: Int = R.layout.activity_complete

    override fun initData() {
        Log.e("initData", AVUser.getCurrentUser().getObjectId())
        Log.e("initData", AVUser.getCurrentUser().getUsername())
        dialog = ProgressDialog(this)
        image = BitmapFactory.decodeResource(resources, R.drawable.ic_header_default)
        setHeader()
        btn_confirm.setOnClickListener {
            if (edit_nickname.text.toString() == "") {
                toast("昵称不能为空")
                return@setOnClickListener
            }
            if (edit_age.text.toString() == "") {
                toast("年龄不能为空")
                return@setOnClickListener
            }
            showDialog()
            model.setInfo(edit_nickname.text.toString(), edit_age.text.toString().toInt(), image, object : UserModel.UserListener {
                override fun onSuccess() {
                    hideDialog()
                    finish()
                }

                override fun onError(e: AVException?) {
                    hideDialog()
                    e?.printStackTrace()
                }

            })
        }
    }

    private fun setHeader() {
        header_complete.setOnClickListener {
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                    .countable(true)
                    .maxSelectable(1)
                    .gridExpectedSize(400)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .theme(R.style.Matisse_Dracula)
                    .imageEngine(GlideEngine())
                    .forResult(GET_IMAGE)
        }
    }

    /**
     * 请求裁剪图片
     */
    private fun sendGetFixedIntent(uri: Uri, code: Int) {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, proj, null, null, null)
        //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst()
        //imagePath = cursor.getString(column_index);
        val intent = Intent()
        intent.action = "com.android.camera.action.CROP"
        intent.setDataAndType(uri, "image/*")// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true")
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("aspectX", 1)// 裁剪框比例
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 300)// 输出图片大小
        intent.putExtra("outputY", 300)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GET_IMAGE -> if (data != null) {
                val uris = Matisse.obtainResult(data)
                val uri = uris[0]
                sendGetFixedIntent(uri, GET_FIXED_IMAGE)
            }
            GET_FIXED_IMAGE -> if (data != null) {
                var bitmap = data.getParcelableExtra<Bitmap>("data") ?: return
                header_complete.setImageBitmap(bitmap)
                image = bitmap
            }
        }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    private fun showDialog() {
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)// 设置进度条的形式为圆形转动的进度条
        dialog!!.setCancelable(true)// 设置是否可以通过点击Back键取消
        dialog!!.setCanceledOnTouchOutside(true)// 设置在点击Dialog外是否取消Dialog进度条
        dialog!!.setTitle("正在上传")
        dialog!!.setMessage("稍等")
        dialog!!.show()
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }
}
