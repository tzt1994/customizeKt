package com.tzt.customizekt

import android.view.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.customizekt.fragment.ActionFragment
import com.tzt.customizekt.fragment.CustomizeFragment
import com.tzt.customizekt.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_custom.*


/**
 * Description:自定义view
 *
 * @author tangzhentao
 * @since 2020/4/29
 */
class CustomActivity: BaseActivity() {
    private val fragmengList = ArrayList<Fragment>()

    override fun layoutResID(): Int {
        return R.layout.activity_custom
    }

    override fun initData() {
        tvTitle.text = "UI效果"

        fragmengList.apply {
            add(ActionFragment())
            add(CustomizeFragment())
            add(MyFragment())
        }

        customVp.adapter = PageAdapter(supportFragmentManager)

        TipsDialog().show(supportFragmentManager, "TipsDialog")
    }

    override fun bindListener() {
        customVp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tvTitle.text = if (position == 0) "UI效果" else if (position == 1)"自定义View详解" else "关于我"
                bnvTab.selectedItemId = if (position == 0) R.id.action else if (position == 1) R.id.customize else R.id.my
            }

        })

        bnvTab.setOnNavigationItemSelectedListener { item: MenuItem ->
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

    inner class PageAdapter(private val fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = fragmengList[position]

        override fun getCount() = fragmengList.size
    }
}