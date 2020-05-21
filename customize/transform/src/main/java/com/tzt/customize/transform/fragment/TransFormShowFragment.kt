package com.tzt.customize.transform.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.transform.R
import kotlinx.android.synthetic.main.fragment_transform_show.*

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/11
 */
class TransFormShowFragment: BaseFragment() {
    private lateinit var animatorSet: AnimatorSet

    override fun layoutResID(): Int {
        return R.layout.fragment_transform_show
    }

    override fun initData() {
        val rightAnimator = ObjectAnimator.ofFloat(fbavFlip, "degreeRightY", 0f, -45f)
        rightAnimator.duration = 1000
        val canvasAnimator = ObjectAnimator.ofFloat(fbavFlip, "degreeCanvas", 0f, 270f)
        canvasAnimator.duration = 800
        val leftAnimator = ObjectAnimator.ofFloat(fbavFlip, "degreeLeftY", 0f, 45f)
        leftAnimator.duration = 1000
        animatorSet = AnimatorSet().apply {
            interpolator = LinearInterpolator()
            playSequentially(rightAnimator, canvasAnimator, leftAnimator)
        }
    }

    override fun bindListener() {
        btnRotate.setOnClickListener {
            ravRotate.startAnimatior()
        }

        btnFlipBorad.setOnClickListener {
            fbavFlip.reset()
            animatorSet.start()
        }
    }
}