package com.tzt.custom.canvas.ui

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.tzt.common.basedepency.BaseModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.custom.canvas.R
import com.tzt.custom.canvas.fragment.ChartFragment
import com.tzt.custom.canvas.fragment.ScrollerFragment
import com.tzt.custom.canvas.fragment.VelocityTrackerFragment
import kotlinx.android.synthetic.main.activity_canvas_draw.*


/**
 * Description:让view滑动起来
 *
 * @author tangzhentao
 * @since 2020/5/27
 */
class ScrollerAcitvity: BaseActivity() {
    private val pageModelList = ArrayList<BaseModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "view的滑动",
            createOriginalIcon {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://juejin.im/post/5c7f4f0351882562ed516ab6#heading-2")))
            }
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_scroller
    }

    override fun initData() {
        pageModelList.apply {
            add(BaseModel("Scroller",
                ScrollerFragment()
            ))
            add(BaseModel("VelocityTracker",
                VelocityTrackerFragment()
            ))
            add(BaseModel("可滑动图表",
                ChartFragment()
            ))
        }

        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(viewPage)
        viewPage.adapter = PageAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    inner class PageAdapter(fragmentManager: FragmentManager, behavior: Int): FragmentPagerAdapter(fragmentManager, behavior) {
        override fun getItem(position: Int): Fragment {
            return pageModelList[position].fragment
        }

        override fun getCount() = pageModelList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return pageModelList[position].title
        }
    }
}