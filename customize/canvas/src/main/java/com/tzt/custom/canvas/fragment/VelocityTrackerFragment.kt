package com.tzt.custom.canvas.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.custom.canvas.R
import com.tzt.custom.canvas.databinding.FragmentVelocityTrackerBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/27
 */
class VelocityTrackerFragment: BaseFragment<FragmentVelocityTrackerBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVelocityTrackerBinding.inflate(inflater, container, false)
}