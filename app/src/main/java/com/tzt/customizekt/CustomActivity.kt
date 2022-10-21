package com.tzt.customizekt

import android.annotation.SuppressLint
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.customizekt.databinding.ActivityCustomBinding
import com.tzt.customizekt.fragment.ActionFragment
import com.tzt.customizekt.fragment.CustomizeFragment
import com.tzt.customizekt.fragment.MyFragment


/**
 * Description:自定义view
 *
 * @author tangzhentao
 * @since 2020/4/29
 */
class CustomActivity: BaseActivity<ActivityCustomBinding>() {
    private val fragmentList = ArrayList<Fragment>()

    private val callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mBinding.tvTitle.text = if (position == 0) "UI效果" else if (position == 1)"自定义View详解" else "关于我"
            mBinding.bnvTab.selectedItemId = if (position == 0) R.id.action else if (position == 1) R.id.customize else R.id.my
        }
    }

    override fun layoutBinding() = ActivityCustomBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        mBinding.tvTitle.text = "UI效果"

        fragmentList.apply {
            add(ActionFragment())
            add(CustomizeFragment())
            add(MyFragment())
        }

        mBinding.customVp.adapter = PageAdapter()

        TipsDialog().show(supportFragmentManager, "TipsDialog")
    }

    @SuppressLint("SetTextI18n")
    override fun bindListener() {
        mBinding.apply {
            customVp.registerOnPageChangeCallback(callback)

            bnvTab.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action -> {
                        tvTitle.text = "UI效果"
                        customVp.setCurrentItem(0, true)
                    }
                    R.id.customize -> {
                        tvTitle.text = "自定义View详解"
                        customVp.setCurrentItem(1, true)
                    }
                    R.id.my -> {
                        tvTitle.text = "关于我"
                        customVp.setCurrentItem(2, true)
                    }
                }
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.customVp.unregisterOnPageChangeCallback(callback)
    }

    inner class PageAdapter: FragmentStateAdapter(this) {
        override fun getItemCount() = fragmentList.size

        override fun createFragment(position: Int) = fragmentList[position]
    }
}