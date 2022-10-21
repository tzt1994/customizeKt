package com.tzt.customize.propertyanimation

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.propertyanimation.databinding.ActivityAnimationBinding
import com.tzt.customize.propertyanimation.fragment.DurationFragment
import com.tzt.customize.propertyanimation.fragment.InterpolatorFragment
import com.tzt.customize.propertyanimation.fragment.PropertyValuesHoldersFragment
import com.tzt.customize.propertyanimation.fragment.TypeEvaluatorFragment
import com.tzt.customize.propertyanimation.fragment.ViewPropertyFragment


/**
 * Description: 属性动画1
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class AnimatorActivity: BaseActivity<ActivityAnimationBinding>() {
    private var paintModels = ArrayList<PaintModel>()

    override fun layoutBinding() = ActivityAnimationBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "属性动画",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hencoder.com/ui-1-6/")))
        }
    )

    override fun initData() {
        paintModels.apply {
            // 绘制文字方式
            add(PaintModel("View.animate()", ViewPropertyFragment()))
            add(PaintModel("setDuration()", DurationFragment()))
            add(PaintModel("interpolator", InterpolatorFragment()))
            add(PaintModel("typeEvaluator", TypeEvaluatorFragment()))
            add(PaintModel("PropertyValuesHolders.ofKeyframe()", PropertyValuesHoldersFragment()))
        }

        mBinding.VpBezier.adapter = BezierAdapter(supportFragmentManager)
        mBinding.tabBezier.setupWithViewPager(mBinding.VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence {
            return paintModels[position].title
        }

        override fun getItem(position: Int) = paintModels[position].fragment

        override fun getCount() = paintModels.size
    }
}