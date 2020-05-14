package com.tzt.customize.path.bezier.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tzt.customize.path.R
import kotlinx.android.synthetic.main.fragment_ship_bezier.*


/**
 * Description: 乘风破浪的小船
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class ShipBezierFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ship_bezier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStart.setOnClickListener{
            shipView.startAnimator()
        }

        btnStop.setOnClickListener {
            shipView.stopAnimator()
        }
    }
}