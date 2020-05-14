package com.tzt.customize.paint.fragment.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tzt.customize.paint.R

/**
 * Description:画笔颜色
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color, container, false)
    }
}