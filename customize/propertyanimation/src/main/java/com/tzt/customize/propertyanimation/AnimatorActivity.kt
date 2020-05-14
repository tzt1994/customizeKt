package com.tzt.customize.propertyanimation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.BaseActivity
import com.tzt.common.basedepency.PaintModel
import com.tzt.customize.propertyanimation.fragment.DurationFragment
import com.tzt.customize.propertyanimation.fragment.InterpolatorFragment
import com.tzt.customize.propertyanimation.fragment.PropertyValuesHoldersFragment
import com.tzt.customize.propertyanimation.fragment.TypeEvaluatorFragment
import com.tzt.customize.propertyanimation.fragment.ViewPropertyFragment
import kotlinx.android.synthetic.main.activity_animation.*


/**
 * Description: 属性动画1
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class AnimatorActivity: BaseActivity() {
    private var paintModels = ArrayList<PaintModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        paintModels.apply {
            // 绘制文字方式
            add(PaintModel("View.animate()", ViewPropertyFragment()))
            add(PaintModel("setDuration()", DurationFragment()))
            add(PaintModel("interpolator", InterpolatorFragment()))
            add(PaintModel("typeEvaluator", TypeEvaluatorFragment()))
            add(PaintModel("PropertyValuesHolders.ofKeyframe()", PropertyValuesHoldersFragment()))
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