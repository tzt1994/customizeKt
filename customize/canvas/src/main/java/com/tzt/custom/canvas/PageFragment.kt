package com.tzt.custom.canvas

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.custom.canvas.databinding.FragmentCustomViewBinding
import kotlin.properties.Delegates


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class PageFragment: BaseFragment<FragmentCustomViewBinding>() {
    private var imageRes by Delegates.notNull<Int>()
    private var layoutRes by Delegates.notNull<Int>()

    companion object {
        fun newInstance(image: Int, layoutRes: Int): PageFragment {
            val pageFragment =
                PageFragment()
            val bundle = Bundle()
            bundle.putInt("image", image)
            bundle.putInt("layout", layoutRes)
            pageFragment.arguments = bundle
            return pageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageRes = arguments?.getInt("image", 0)?: 0
        layoutRes = arguments?.getInt("layout", 0)?: 0
    }

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCustomViewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutRes.let {
            if (it != 0) {
                binding.bootomLayout.layoutResource = layoutRes
                binding.bootomLayout.inflate()
            }
        }

        imageRes.let {
            if (it != 0) {
                binding.topIv.background = ColorDrawable(Color.TRANSPARENT)
                binding.topIv.setImageResource(imageRes)
            }
        }
    }
}