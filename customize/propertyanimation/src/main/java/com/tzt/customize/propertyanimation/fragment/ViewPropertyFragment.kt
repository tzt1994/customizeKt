package com.tzt.customize.propertyanimation.fragment

import android.annotation.SuppressLint
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.R
import kotlinx.android.synthetic.main.fragment_view_property.*


/**
 * Description: view的动画
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ViewPropertyFragment: BaseFragment() {

    override fun layoutResID(): Int {
        return R.layout.fragment_view_property
    }

    @SuppressLint("SetTextI18n")
    override fun bindListener() {
        btnViewPro.setOnClickListener {
            ivAnimator.animate().apply {
                translationXBy(500f)
                translationYBy(500f)
                scaleXBy(1f)
                scaleYBy(1f)
                alphaBy(0f)
                rotationBy(360f)
                duration = 1000
                withEndAction {
                    ivAnimator.animate().apply {
                        translationXBy(-500f)
                        translationYBy(-500f)
                        scaleXBy(-1f)
                        scaleYBy(-1f)
                        alphaBy(1f)
                        rotationBy(-360f)
                        duration = 1000
                    }
                }
            }
        }
    }
}