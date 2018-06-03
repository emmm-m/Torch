package com.bucai.torch.view.main.measurement


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bucai.torch.R
import kotlinx.android.synthetic.main.fragment_measure.*

/**
 * 测评
 *
 */
class MeasureFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_measure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        measure_button.setOnClickListener { startActivity(Intent(context, MeasureDetailActivity::class.java)) }
    }
}
