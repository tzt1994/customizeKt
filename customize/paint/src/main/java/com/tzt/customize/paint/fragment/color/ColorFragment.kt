package com.tzt.customize.paint.fragment.color

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.paint.databinding.FragmentPaintColorBinding

/**
 * Description:颜色color
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorFragment : BaseFragment<FragmentPaintColorBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaintColorBinding.inflate(inflater, container, false)
}