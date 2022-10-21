package com.tzt.custom.canvas.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.tzt.common.basedepency.BaseFragment
import com.tzt.custom.canvas.databinding.FragmentChartBinding
import com.tzt.custom.canvas.widget.ChartView
import kotlin.collections.ArrayList


/**
 * Description: 滑动的图表
 *
 * @author tangzhentao
 * @since 2020/5/27
 */
class ChartFragment: BaseFragment<FragmentChartBinding>() {
    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentChartBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.apply {
            tvDescrip.text = "数据量(${seekbar.progress})"
            cvChart.setBarInfoList(getList(100))
        }
    }

    override fun bindListener() {
        binding.apply {
            btnResetData.setOnClickListener {
                cvChart.setBarInfoList(getList(100))
            }

            btnStart.setOnClickListener {
                cvChart.start()
            }

            seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    tvDescrip.text = "数据量($progress)"
                    cvChart.setBarInfoList(getList(progress))
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        }
    }

    private fun getList(size: Int): ArrayList<ChartView.BarInfo> {
        val mlist = ArrayList<ChartView.BarInfo>()
        for (i in 0 until size) {
            mlist.add(ChartView.BarInfo("${i+1}日", (0..100).random().toDouble() / 100))
        }

        return mlist
    }
}