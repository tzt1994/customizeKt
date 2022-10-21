package com.tzt.customize.paint.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.paint.databinding.ActivityCommonBinding
import com.tzt.customize.paint.fragment.color.ColorFragment
import com.tzt.customize.paint.fragment.color.ColorMatrixFragment
import com.tzt.customize.paint.fragment.color.PaintColorFragment
import com.tzt.customize.paint.fragment.color.XfermodeFragment


/**
 * Description: 画笔
 *
 * @author tangzhentao
 * @since 2020/4/27
 */
class PaintColorActivity: BaseActivity<ActivityCommonBinding>() {
    private var bezierModels = ArrayList<PaintModel>()

    override fun layoutBinding() = ActivityCommonBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "Paint 颜色的处理",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hencoder.com/ui-1-2/")))
        }
    )

    override fun initData() {
        bezierModels.apply {
            add(PaintModel("颜色Color", ColorFragment()))
            // 常用的shader
            val baseShaderFragment = PaintColorFragment()
            val baseBundle = Bundle()
            baseBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_BASE)
            baseShaderFragment.arguments = baseBundle
            add(PaintModel("着色器shader", baseShaderFragment))
            // 图片着色器
            val bitmapShaderFragment = PaintColorFragment()
            val bitmapBundle = Bundle()
            bitmapBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_BITMAP)
            bitmapShaderFragment.arguments = bitmapBundle
            add(PaintModel("BitmapShader", bitmapShaderFragment))
            // 混合着色器
            val comShaderFragment = PaintColorFragment()
            val comBundle = Bundle()
            comBundle.putInt("shader_type", PaintColorFragment.SHADER_TYPE_COMPOSE)
            comShaderFragment.arguments = comBundle
            add(PaintModel(title = "ComposeShader", fragment = comShaderFragment))
            // 颜色过滤器
            val colorFilterFragment = PaintColorFragment()
            val colorFilterBundle = Bundle()
            colorFilterBundle.putInt("shader_type", PaintColorFragment.COLOR_FILTER)
            colorFilterFragment.arguments = colorFilterBundle
            add(PaintModel(title = "ColorFilter", fragment = colorFilterFragment))
            add(PaintModel(title = "ColorMatrixColorFilter", fragment = ColorMatrixFragment()))
            // Xfermode
            add(PaintModel(title = "Xfermode", fragment = XfermodeFragment()))
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