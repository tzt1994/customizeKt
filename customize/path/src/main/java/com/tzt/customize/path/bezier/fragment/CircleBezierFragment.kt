package com.tzt.customize.path.bezier.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.path.R
import com.tzt.customize.path.databinding.FragmentCircleBezierBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class CircleBezierFragment: BaseFragment<FragmentCircleBezierBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCircleBezierBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.apply {
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
}