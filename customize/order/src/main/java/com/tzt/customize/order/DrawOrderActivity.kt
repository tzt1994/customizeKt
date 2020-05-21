package com.tzt.customize.order

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.order.fragment.DrawFragment
import com.tzt.customize.order.fragment.DrawOrderFragment
import com.tzt.customize.order.fragment.DrawProcessFragment
import kotlinx.android.synthetic.main.activity_draw_order.*


/**
 * Description: 绘制顺序
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class DrawOrderActivity: BaseActivity() {
    private var paintModels = ArrayList<PaintModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "绘制顺序"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_draw_order
    }

    override fun initData() {
        paintModels.apply {
            // super.onDraw()
            val onDrawFragment = DrawOrderFragment()
            val onDrawBundle = Bundle()
            onDrawBundle.putInt("draw_order_type", DrawOrderFragment.SUPER_ONDRAW)
            onDrawFragment.arguments = onDrawBundle
            add(PaintModel("super.onDraw()", onDrawFragment))
            // dispatchDraw()
            val dispatchDrawFragment = DrawOrderFragment()
            val dispatchDrawBundle = Bundle()
            dispatchDrawBundle.putInt("draw_order_type", DrawOrderFragment.DISPATCH_DRAW)
            dispatchDrawFragment.arguments = dispatchDrawBundle
            add(PaintModel("dispatchDraw()", dispatchDrawFragment))
            // 绘制过程简述
            add(PaintModel("绘制过程简述", DrawProcessFragment()))
            // onDrawForeground()
            val onDrawForegroundFragment = DrawOrderFragment()
            val onDrawForegroundBundle = Bundle()
            onDrawForegroundBundle.putInt("draw_order_type", DrawOrderFragment.ONDRAW_FOREGROUND)
            onDrawForegroundFragment.arguments = onDrawForegroundBundle
            add(PaintModel("onDrawForeground()", onDrawForegroundFragment))
            // draw() 总调度方法
            add(PaintModel("draw()", DrawFragment()))
        }

        VpBezier.adapter = BezierAdapter(supportFragmentManager)
        tabBezier.setupWithViewPager(VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return paintModels[position].title
        }

        override fun getItem(position: Int) = paintModels[position].fragment

        override fun getCount() = paintModels.size
    }
}