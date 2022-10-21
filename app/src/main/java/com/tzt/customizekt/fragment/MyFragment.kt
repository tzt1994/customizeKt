package com.tzt.customizekt.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customizekt.R
import com.tzt.customizekt.databinding.FragmentMyBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/23
 */
class MyFragment: BaseFragment<FragmentMyBinding>() {

    override fun layoutBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentMyBinding.inflate(inflater, container, false)
}