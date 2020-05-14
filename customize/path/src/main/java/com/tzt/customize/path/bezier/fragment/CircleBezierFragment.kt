package com.tzt.customize.path.bezier.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.tzt.customize.path.R
import kotlinx.android.synthetic.main.fragment_circle_bezier.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class CircleBezierFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_circle_bezier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jinduTv.text = CBView.getProportion().toString()
        seekBar.max = 1000
        seekBar.progress = (CBView.getProportion() * seekBar.max).toInt()
        seekBar.setOnSeekBarChangeListener(object :OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = p1.toFloat() / 1000f
                jinduTv.text = progress.toString()
                CBView.setProportion(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }
}