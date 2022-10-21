package com.tzt.customizekt

import android.view.View
import android.widget.Toast
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.Toobar
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customizekt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutBinding() = ActivityMainBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        object :Toobar.TooBarAction() {
            override fun getImageResource() = R.mipmap.back_arrow

            override fun click(view: View) {
                Toast.makeText(context, "首页", Toast.LENGTH_SHORT).show()
            }

        },
        title = "首页"
    )

    override fun initData() {
        showPage(PageState.ERROR)
    }

    override fun bindListener() {

    }

}
