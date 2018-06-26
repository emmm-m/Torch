package com.bucai.torch.view.message

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMMessage
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage
import com.bucai.torch.R
import com.bucai.torch.util.PermissionUnit
import com.bucai.torch.util.RecordManager
import com.bucai.torch.util.SystemUtil
import com.bucai.torch.util.leancloud.MessageModel
import com.bucai.torch.view.base.BaseActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.File
import java.io.IOException


class ChatActivity : BaseActivity(), IChatActivity, MessageModel.MessageListener {
    private var user: String? = null
    private var id: String? = null
    private var leftUrl: String? = null
    private var rightUrl: String? = null
    private var presenter: IChatPresenter? = null
    private var adapter: ChatRecyclerAdapter? = null
    private var conversation: AVIMConversation? = null
    private var recordManager: RecordManager? = null
    private var mRecordPath: String? = null
    private var size: Int = 0

    override val contentViewId: Int = R.layout.activity_chat

    override fun initState() {
        //        super.initState();
    }

    override fun initData() {
        presenter = ChatPresenter(this)
        recordManager = RecordManager()
        setBar()
        getMessage()
        refreshMessage()
        sendPicMessage()
        conversation = presenter!!.getConversation(id)
        edit_chat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    send_btn_chat.setOnClickListener { sendMessage() }
                    send_btn_chat.setBackgroundResource(R.drawable.bg_tag_blue)
                } else {
                    send_btn_chat.setBackgroundResource(R.drawable.background_tag_grey)
                    send_btn_chat.setOnClickListener(null)
                }
            }
        })
        setAudio()
        img_audio.setOnClickListener {
            if (record_btn.visibility == View.VISIBLE) {
                record_btn.visibility = View.GONE
                edit_chat.visibility = View.VISIBLE
                img_audio.setImageResource(R.drawable.ic_voice_public)
            } else {
                record_btn.visibility = View.VISIBLE
                edit_chat.visibility = View.GONE
                img_audio.setImageResource(R.drawable.ic_keyboard)
            }
        }
    }

    override fun setBar() {
        val intent = intent
        user = intent.getStringExtra("username")
        id = intent.getStringExtra("id")
        val nickname = intent.getStringExtra("nickname")
        leftUrl = intent.getStringExtra("leftUrl")
        rightUrl = intent.getStringExtra("rightUrl")
        header_title.text = nickname
        back_header.visibility = View.VISIBLE
        back_header.setOnClickListener { onBackPressed() }
    }

    override fun sendMessage() {
        val tx = edit_chat.text.toString()
        edit_chat.setText("")
        val txMessage = AVIMTextMessage()
        txMessage.text = tx
        txMessage.from = AVUser.getCurrentUser().username
        size += 1
        txMessage.from = AVUser.getCurrentUser().username
        adapter!!.addMessage(txMessage)
        list_chat.smoothScrollToPosition(size)
        presenter!!.sendMessage(conversation, tx)
    }

    override fun sendPicMessage() {
        if (PermissionUnit.hasDiskPermission(this)) {
            img_picker.setOnClickListener {
                Matisse.from(this@ChatActivity)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                        .countable(true)
                        .maxSelectable(9)
                        .gridExpectedSize(400)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(GlideEngine())
                        .forResult(0)
            }
        } else {
            PermissionUnit.askForDiskPermission(this, 0)
        }
    }

    override fun sendAudioMessage(time: String) {
        if (mRecordPath != null) {
            presenter!!.sendAudioMessage(conversation, mRecordPath, time)
            var message: AVIMAudioMessage? = null
            try {
                message = AVIMAudioMessage(mRecordPath!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Log.d("??????", "sendAudioMessage: " + message!!)
            if (message == null) return
            message.text = time
            message.from = AVUser.getCurrentUser().username
            size += 1
            list_chat.smoothScrollToPosition(size)
            adapter!!.addMessage(message)
        }
    }

    override fun setAudio() {
        if (PermissionUnit.hasMicPermission(this)) {
            record_btn.setOnTouchListener(object : View.OnTouchListener {
                var startTime: Long = 0

                override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                    if (motionEvent.y < -80) {
                        record_btn.setTextColor(Color.RED)
                        record_btn.text = "取消发送"
                    } else {
                        record_btn.setTextColor(Color.parseColor("#999999"))
                        record_btn.text = "松开  发送"
                    }
                    Log.d("ffff", "onTouch: " + motionEvent.action + "  " + motionEvent.y)
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startTime = 0
                            record_btn.text = "松开  发送"
                            SystemUtil.vibrator(this@ChatActivity, longArrayOf(30, 20))
                            startTime = System.currentTimeMillis()
                            setVoicePath()
                            Log.d("zhuzhan", "onTouch: " + mRecordPath!!)
                            recordManager!!.startRecord(mRecordPath)
                        }
                        MotionEvent.ACTION_UP -> {
                            val endTime = System.currentTimeMillis()
                            if (endTime - startTime > 500 && motionEvent.y > -70) {
                                recordManager!!.stopRecord()
                                record_btn.text = "按住  录音"
                                record_btn.setTextColor(Color.parseColor("#999999"))
                                val time = SystemUtil.secToTime((endTime - startTime).toInt() / 1000)
                                Log.d("??????", "onTouch: $time")
                                sendAudioMessage(time)
                            } else if (endTime - startTime < 500) {
                                toast("时间太短，录音无效")
                                recordManager!!.stopRecord()
                                val file = File(mRecordPath!!)
                                if (file.exists()) {
                                    file.delete()
                                }
                                record_btn.text = "按住  录音"
                                record_btn.setTextColor(Color.parseColor("#999999"))
                                mRecordPath = null
                            } else {
                                recordManager!!.stopRecord()
                                val file = File(mRecordPath!!)
                                if (file.exists()) {
                                    file.delete()
                                }
                                record_btn.text = "按住  录音"
                                record_btn.setTextColor(Color.parseColor("#999999"))
                                mRecordPath = null
                            }
                        }
                    }
                    return false
                }
            })
        } else {
            PermissionUnit.askForMicPermission(this, 1)
        }
    }

    fun setVoicePath() {
        mRecordPath = Environment.getExternalStorageDirectory().absolutePath + "/Teller/audio/" + AVUser.getCurrentUser().username + System.currentTimeMillis() + ".pcm"
        val file = File(mRecordPath!!)
        if (!file.exists()) {
            file.mkdirs()
        }
        mRecordPath = file.path
    }

    override fun getMessage() {
        presenter!!.getMessage(id)
    }

    override fun setList(list: List<AVIMMessage>) {
        adapter = ChatRecyclerAdapter(this@ChatActivity, list, user, leftUrl, rightUrl)
        list_chat.layoutManager = LinearLayoutManager(this@ChatActivity)
        list_chat.adapter = adapter
        size = list.size - 1
        list_chat.scrollToPosition(size)
    }

    override fun refreshMessage() {
        presenter!!.refreshMessage(this)
    }

    override fun onMessage(message: AVIMMessage, conversation: AVIMConversation, client: AVIMClient) {
        if (message.from == user) {
            size += 1
            adapter!!.addMessage(message)
            list_chat.smoothScrollToPosition(size)
        } else {
            toast(message.from + "发来一条消息")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (data == null) return
                val uris = Matisse.obtainResult(data)
                for (uri in uris) {
                    val result: String
                    val cursor = this.contentResolver.query(uri,
                            arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
                    if (cursor == null)
                        result = uri.path
                    else {
                        cursor.moveToFirst()
                        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        result = cursor.getString(index)
                        cursor.close()
                    }
                    Log.d("!!!", "onActivityResult: $result")
                    var message: AVIMImageMessage? = null
                    try {
                        message = AVIMImageMessage(result)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    if (message == null)
                        toast("该图片不存在")
                    else {
                        size += 1
                        message.from = AVUser.getCurrentUser().username
                        adapter!!.addMessage(message)
                        list_chat.smoothScrollToPosition(size)
                        presenter!!.sendPicMessage(conversation, result)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendPicMessage()
            } else {
                toast("没有SD卡权限，请手动开启")
            }
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setAudio()
            } else {
                toast("没有麦克风权限，请手动开启")
            }
        }
    }
}
