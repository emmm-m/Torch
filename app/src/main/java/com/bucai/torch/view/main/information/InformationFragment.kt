package com.bucai.torch.view.main.information


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bucai.torch.R
import kotlinx.android.synthetic.main.fragment_information.*

/**
 * 资讯
 *
 */
class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_fragment_news.adapter = InfoVpAdapter(fragmentManager!!)
        tab_fragment_news.tabMode = TabLayout.MODE_FIXED
        tab_fragment_news.setupWithViewPager(vp_fragment_news)
    }

}
