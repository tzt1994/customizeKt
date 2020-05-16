package com.tzt.customize.paint.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.paint.R
import com.tzt.customize.paint.fragment.effect.PaintEffectFragment
import com.tzt.customize.paint.fragment.effect.ShadowLayerFragment
import kotlinx.android.synthetic.main.activity_common.*


/**
 * Description:效果
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class EffectActivity: BaseActivity() {
    private var paintModels = ArrayList<PaintModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "Paint 效果"
        )
    }

    override var layoutResID= R.layout.activity_common

    override fun initData() {
        paintModels.apply {
            // 抗锯齿效果
            val antiAliasFragment =
                PaintEffectFragment()
            val antiAliasBundle = Bundle()
            antiAliasBundle.putInt("effect_type", PaintEffectFragment.ANTI_ALIAS)
            antiAliasFragment.arguments = antiAliasBundle
            add(PaintModel("抗锯齿", antiAliasFragment))
            // 样式
            val styleFragment =
                PaintEffectFragment()
            val styleBundle = Bundle()
            styleBundle.putInt("effect_type", PaintEffectFragment.STYLE)
            styleFragment.arguments = styleBundle
            add(PaintModel("Style", styleFragment))
            // 线条形状
            val lineShapeFragment =
                PaintEffectFragment()
            val lineShapeBundle = Bundle()
            lineShapeBundle.putInt("effect_type", PaintEffectFragment.LINE_SHAPE)
            lineShapeFragment.arguments = lineShapeBundle
            add(PaintModel("线条形状", lineShapeFragment))
            // 色彩优化
            val colorFragment =
                PaintEffectFragment()
            val colorBundle = Bundle()
            colorBundle.putInt("effect_type", PaintEffectFragment.COLOR_OPTIMIZATION)
            colorFragment.arguments = colorBundle
            add(PaintModel("色彩优化", colorFragment))
            // 图形轮廓
            val pathEffectFragment =
                PaintEffectFragment()
            val pathEffectBundle = Bundle()
            pathEffectBundle.putInt("effect_type", PaintEffectFragment.PATH_EFFECT)
            pathEffectFragment.arguments = pathEffectBundle
            add(PaintModel("PathEffect", pathEffectFragment))
            // 阴影
            add(PaintModel("ShadowLayer",
                ShadowLayerFragment()
            ))
            // 模糊效果
            val maskFilterFragment =
                PaintEffectFragment()
            val maskFilterBundle = Bundle()
            maskFilterBundle.putInt("effect_type", PaintEffectFragment.MASK_FILTER)
            maskFilterFragment.arguments = maskFilterBundle
            add(PaintModel("MaskFilter", maskFilterFragment))
            // 获取path
            val getPathFragment =
                PaintEffectFragment()
            val getPathBundle = Bundle()
            getPathBundle.putInt("effect_type", PaintEffectFragment.GET_PATH)
            getPathFragment.arguments = getPathBundle
            add(PaintModel("获取path", getPathFragment))
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