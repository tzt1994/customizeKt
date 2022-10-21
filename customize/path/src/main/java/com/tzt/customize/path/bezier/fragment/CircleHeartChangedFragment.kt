package com.tzt.customize.path.bezier.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.path.databinding.FragmentCircleHeartBezierBinding

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class CircleHeartChangedFragment: BaseFragment<FragmentCircleHeartBezierBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCircleHeartBezierBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartAnimator.setOnClickListener {
            binding.CHChangeView.startAnimator()
        }
    }
}