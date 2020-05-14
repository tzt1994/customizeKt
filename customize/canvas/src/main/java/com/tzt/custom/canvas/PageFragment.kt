package com.tzt.custom.canvas

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_custom_view.*
import kotlin.properties.Delegates


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class PageFragment: Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutRes.let {
            if (it != 0) {
                bootomLayout.layoutResource = layoutRes
                bootomLayout.inflate()
            }
        }

        imageRes.let {
            if (it != 0) {
                topIv.background = ColorDrawable(Color.TRANSPARENT)
                topIv.setImageResource(imageRes)
            }
        }
    }
}