package com.tzt.customize.paint.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.paint.R
import com.tzt.customize.paint.fragment.text.PaintTextFragment
import kotlinx.android.synthetic.main.activity_common.*


/**
 * Description: 文字
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class TextActivity: BaseActivity() {
    private var paintModels = ArrayList<PaintModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "Paint 绘制文字"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_common
    }

    override fun initData() {
        paintModels.apply {
            // 绘制文字方式
            val styleFragment = PaintTextFragment()
            val styleBundle = Bundle()
            styleBundle.putInt("text_type", PaintTextFragment.TEXT_STYLE)
            styleFragment.arguments = styleBundle
            add(PaintModel("绘制文字方式", styleFragment))
            // 文字显示效果类
            val effectFragment = PaintTextFragment()
            val effectBundle = Bundle()
            effectBundle.putInt("text_type", PaintTextFragment.TEXT_EFFECT)
            effectFragment.arguments = effectBundle
            add(PaintModel("文字显示效果类", effectFragment))
            // 测量文字尺寸类
            val fontFragment = PaintTextFragment()
            val fontBundle = Bundle()
            fontBundle.putInt("text_type", PaintTextFragment.TEXT_FONT)
            fontFragment.arguments = fontBundle
            add(PaintModel("测量文字尺寸类", fontFragment))
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