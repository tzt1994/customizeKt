package com.tzt.customize.path.bezier.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tzt.customize.path.R
import kotlinx.android.synthetic.main.fragment_circle_heart_bezier.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class CircleHeartChangedFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_circle_heart_bezier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartAnimator.setOnClickListener {
            CHChangeView.startAnimator()
        }
    }
}