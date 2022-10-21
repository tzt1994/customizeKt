package com.tzt.customize.path.bezier

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.BezierModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.path.R
import com.tzt.customize.path.bezier.fragment.*
import com.tzt.customize.path.databinding.ActivityBezierBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/27
 */
class BezierActivity: BaseActivity<ActivityBezierBinding>() {
    private var bezierModels = ArrayList<BezierModel>()

    override fun layoutBinding() = ActivityBezierBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "贝塞尔曲线",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://juejin.im/post/5c3988516fb9a049d1325c83")))
        }
    )

    override fun initData() {
        bezierModels.apply {
            add(BezierModel(title = "一阶贝塞尔", fragment = OneBezierFragment()))
            add(BezierModel(title = "二阶贝塞尔", fragment = TwoBezierFragment()))
            add(BezierModel(title = "三阶贝塞尔", fragment = ThreeBezierFragment()))
            add(BezierModel(title = "n阶贝塞尔", fragment = MultipleBezierFragment()))
            add(BezierModel(title = "圆形贝塞尔实现", fragment = CircleBezierFragment()))
            add(BezierModel(title = "圆变心贝塞尔实现", fragment = CircleHeartChangedFragment()))
            add(BezierModel(title = "乘风破浪的小船", fragment = ShipBezierFragment()))
        }

        mBinding.VpBezier.adapter = BezierAdapter(supportFragmentManager)
        mBinding.tabBezier.setupWithViewPager(mBinding.VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return bezierModels[position].title
        }

        override fun getItem(position: Int) = bezierModels[position].fragment

        override fun getCount() = bezierModels.size
    }
}