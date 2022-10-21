package com.tzt.custom.canvas.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.custom.canvas.R
import com.tzt.custom.canvas.databinding.FragmentScrollerBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/27
 */
class ScrollerFragment: BaseFragment<FragmentScrollerBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScrollerBinding.inflate(inflater, container, false)
}