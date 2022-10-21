package com.tzt.customize.propertyanimation.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.databinding.FragmentViewPropertyBinding


/**
 * Description: view的动画
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ViewPropertyFragment: BaseFragment<FragmentViewPropertyBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentViewPropertyBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun bindListener() {
        binding.btnViewPro.setOnClickListener {
            binding.ivAnimator.animate().apply {
                translationXBy(500f)
                translationYBy(500f)
                scaleXBy(1f)
                scaleYBy(1f)
                alphaBy(0f)
                rotationBy(360f)
                duration = 1000
                withEndAction {
                    binding.ivAnimator.animate().apply {
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