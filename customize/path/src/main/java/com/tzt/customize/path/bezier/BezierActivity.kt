package com.tzt.customize.path.bezier

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.BezierModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.path.R
import com.tzt.customize.path.bezier.fragment.*
import kotlinx.android.synthetic.main.activity_bezier.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/27
 */
class BezierActivity: BaseActivity() {
    private var bezierModels = ArrayList<BezierModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "贝塞尔曲线"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_bezier
    }

    override fun initData() {
        super.initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bezierModels.apply {
            add(BezierModel(title = "一阶贝塞尔", fragment = OneBezierFragment()))
            add(BezierModel(title = "二阶贝塞尔", fragment = TwoBezierFragment()))
            add(BezierModel(title = "三阶贝塞尔", fragment = ThreeBezierFragment()))
            add(BezierModel(title = "n阶贝塞尔", fragment = MultipleBezierFragment()))
            add(BezierModel(title = "圆形贝塞尔实现", fragment = CircleBezierFragment()))
            add(BezierModel(title = "圆变心贝塞尔实现", fragment = CircleHeartChangedFragment()))
            add(BezierModel(title = "乘风破浪的小船", fragment = ShipBezierFragment()))
        }

        VpBezier.adapter = BezierAdapter(supportFragmentManager)
        tabBezier.setupWithViewPager(VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return bezierModels[position].title
        }

        override fun getItem(position: Int) = bezierModels[position].fragment

        override fun getCount() = bezierModels.size
    }
}