package com.tzt.customize.path.bezier.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.path.databinding.FragmentShipBezierBinding


/**
 * Description: 乘风破浪的小船
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class ShipBezierFragment: BaseFragment<FragmentShipBezierBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShipBezierBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.apply {
            btnStart.setOnClickListener{
                shipView.startAnimator()
            }

            btnStop.setOnClickListener {
                shipView.stopAnimator()
            }
        }
    }
}