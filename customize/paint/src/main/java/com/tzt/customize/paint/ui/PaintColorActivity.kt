package com.tzt.customize.paint.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.paint.R
import com.tzt.customize.paint.fragment.color.ColorFragment
import com.tzt.customize.paint.fragment.color.ColorMatrixFragment
import com.tzt.customize.paint.fragment.color.PaintColorFragment
import com.tzt.customize.paint.fragment.color.XfermodeFragment
import kotlinx.android.synthetic.main.activity_common.*


/**
 * Description: 画笔
 *
 * @author tangzhentao
 * @since 2020/4/27
 */
class PaintColorActivity: BaseActivity() {
    private var bezierModels = ArrayList<PaintModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "Paint 颜色的处理"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_common
    }

    override fun initData() {
        bezierModels.apply {
            add(PaintModel(title = "颜色Color", fragment = ColorFragment()))
            // 常用的shader
            val baseShaderFragment =
                PaintColorFragment()
            val baseBundle = Bundle()
            baseBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_BASE)
            baseShaderFragment.arguments = baseBundle
            add(PaintModel("着色器shader", baseShaderFragment))
            // 图片着色器
            val bitmapShaderFragment =
                PaintColorFragment()
            val bitmapBundle = Bundle()
            bitmapBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_BITMAP)
            bitmapShaderFragment.arguments = bitmapBundle
            add(PaintModel("BitmapShader", fragment = bitmapShaderFragment))
            // 混合着色器
            val comShaderFragment =
                PaintColorFragment()
            val comBundle = Bundle()
            comBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_COMPOSE)
            comShaderFragment.arguments = comBundle
            add(PaintModel(title = "ComposeShader", fragment = comShaderFragment))
            // 颜色过滤器
            val colorFilterFragment =
                PaintColorFragment()
            val colorFilterBundle = Bundle()
            colorFilterBundle.putInt("shader_type", PaintColorFragment.COLOR_FILTER)
            colorFilterFragment.arguments = colorFilterBundle
            add(PaintModel(title = "ColorFilter", fragment = colorFilterFragment))
            add(PaintModel(title = "ColorMatrixColorFilter", fragment = ColorMatrixFragment()))
            // Xfermode
            add(PaintModel(title = "Xfermode", fragment = XfermodeFragment()))
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