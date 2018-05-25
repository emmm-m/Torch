package com.bucai.torch.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.R
import com.bucai.torch.util.SharedPreferencesUtils
import com.bucai.torch.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_evaluate_prepare.*

class EvaluatePrepareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_prepare)
        btn_start_evaluate.setOnClickListener {
            SharedPreferencesUtils.setParam(this, "hasEvaluated", true)
            val intent = Intent(this@EvaluatePrepareActivity, MainActivity::class.java)
            intent.putExtra("count", 2)
            startActivity(intent)
            finish()
        }
    }
}
