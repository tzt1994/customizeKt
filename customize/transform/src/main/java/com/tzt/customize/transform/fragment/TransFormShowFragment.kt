package com.tzt.customize.transform.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.transform.databinding.FragmentTransformShowBinding

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/11
 */
class TransFormShowFragment: BaseFragment<FragmentTransformShowBinding>() {
    private lateinit var animatorSet: AnimatorSet

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTransformShowBinding.inflate(inflater, container, false)

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun initData() {
        val rightAnimator = ObjectAnimator.ofFloat(binding.fbavFlip, "degreeRightY", 0f, -45f)
        rightAnimator.duration = 1000
        val canvasAnimator = ObjectAnimator.ofFloat(binding.fbavFlip, "degreeCanvas", 0f, 270f)
        canvasAnimator.duration = 800
        val leftAnimator = ObjectAnimator.ofFloat(binding.fbavFlip, "degreeLeftY", 0f, 45f)
        leftAnimator.duration = 1000
        animatorSet = AnimatorSet().apply {
            interpolator = LinearInterpolator()
            playSequentially(rightAnimator, canvasAnimator, leftAnimator)
        }
    }

    override fun bindListener() {
        binding.btnRotate.setOnClickListener {
            binding.ravRotate.startAnimatior()
        }

        binding.btnFlipBorad.setOnClickListener {
            binding.fbavFlip.reset()
            animatorSet.start()
        }
    }
}