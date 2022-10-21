package com.tzt.customize.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.order.databinding.ActivityDrawOrderBinding
import com.tzt.customize.order.fragment.DrawFragment
import com.tzt.customize.order.fragment.DrawOrderFragment
import com.tzt.customize.order.fragment.DrawProcessFragment


/**
 * Description: 绘制顺序
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class DrawOrderActivity: BaseActivity<ActivityDrawOrderBinding>() {
    private var paintModels = ArrayList<PaintModel>()

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "绘制顺序",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hencoder.com/ui-1-5/")))
        }
    )

    override fun layoutBinding() = ActivityDrawOrderBinding.inflate(layoutInflater, null, false)
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

        mBinding.VpBezier.adapter = BezierAdapter(supportFragmentManager)
        mBinding.tabBezier.setupWithViewPager(mBinding.VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return paintModels[position].title
        }

        override fun getItem(position: Int) = paintModels[position].fragment

        override fun getCount() = paintModels.size
    }
}