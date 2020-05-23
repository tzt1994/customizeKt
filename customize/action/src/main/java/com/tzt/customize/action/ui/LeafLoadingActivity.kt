package com.tzt.customize.action.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R
import kotlinx.android.synthetic.main.activity_leaf_loading.*

/**
 * Description: 风扇loading效果
 *
 * @author tangzhentao
 * @since 2020/5/13
 */

@SuppressLint("SetTextI18n")
class LeafLoadingActivity: BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private val REFRESH_INT = 0x10

    private var mProgress = 0

    private lateinit var mHandler: Handler

    override fun layoutResID(): Int {
        return R.layout.activity_leaf_loading
    }

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "风扇loading效果",
            createOriginalIcon {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.csdn.net/tianjian4592/article/details/44538605")))
            }
        )
    }

    override fun initData() {
        setPorgress()
        seekBarAmpair.progress = lllLoading.mMiddleAmplitude
        tvAmpair.text = "中等振幅: ${lllLoading.mMiddleAmplitude}"
        seekBarDisparity.progress = lllLoading.mAmplitudeDisparity
        tvDisparity.text = "振幅差距: ${lllLoading.mAmplitudeDisparity}"
        seekBarFloatTime.progress = lllLoading.mLeafFloatTime.toInt()
        tvFloatTime.text = "移动时间: ${lllLoading.mLeafFloatTime}"
        seekBarRotateTime.progress = lllLoading.mLeafRotateTime.toInt()
        tvRotateTime.text = "旋转时间: ${lllLoading.mLeafRotateTime}"

        mHandler = Handler{
            when(it.what) {
                REFRESH_INT -> {
                    if (mProgress < 40) {
                        mProgress += 1

                        mHandler.sendEmptyMessageDelayed(REFRESH_INT, (0..400L).random())
                    } else if (mProgress < 100) {
                        mProgress += 1

                        mHandler.sendEmptyMessageDelayed(REFRESH_INT, (0..800L).random())

                    }

                    lllLoading.mProgress = mProgress
                    setPorgress()
                }
            }

            false
        }

        mHandler.sendEmptyMessageDelayed(REFRESH_INT, 1000)
    }

    override fun bindListener() {

        btnResetProgress.setOnClickListener(this)
        seekBarAmpair.setOnSeekBarChangeListener(this)
        seekBarDisparity.setOnSeekBarChangeListener(this)
        seekBarFloatTime.setOnSeekBarChangeListener(this)
        seekBarRotateTime.setOnSeekBarChangeListener(this)
    }

    private fun setPorgress() {
        tvProgress.text = "当前进度: ${lllLoading.mProgress}"
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnResetProgress -> {
                lllLoading.mProgress = 0
                mProgress = 0
                mHandler.sendEmptyMessageDelayed(REFRESH_INT, 1000)
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar) {
            seekBarAmpair -> {
                lllLoading.mMiddleAmplitude = progress
                tvAmpair.text = "中等振幅: ${lllLoading.mMiddleAmplitude}"
            }
            seekBarDisparity -> {
                lllLoading.mAmplitudeDisparity = progress
                tvDisparity.text = "振幅差距: ${lllLoading.mAmplitudeDisparity}"
            }
            seekBarFloatTime -> {
                lllLoading.mLeafFloatTime = progress.toLong()
                tvFloatTime.text = "移动时间: ${lllLoading.mLeafFloatTime}"
            }
            seekBarRotateTime -> {
                lllLoading.mLeafRotateTime = progress.toLong()
                tvRotateTime.text = "旋转时间: ${lllLoading.mLeafRotateTime}"
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}