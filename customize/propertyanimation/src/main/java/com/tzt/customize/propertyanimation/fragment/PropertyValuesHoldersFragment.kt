package com.tzt.customize.propertyanimation.fragment

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.databinding.FragmentPropertyValuesHoldersBinding

/**
 * Description: 属性拆分
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class PropertyValuesHoldersFragment: BaseFragment<FragmentPropertyValuesHoldersBinding>() {
    private lateinit var holderAnimator: ObjectAnimator
    private var mProgress = 0

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPropertyValuesHoldersBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        mProgress = binding.seekBarProgress.progress
        binding.tvProgress.text = "进度值：$mProgress"
        holderAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.hpvProgress, getPropertyValuesHolder()).apply {
            duration = 1500
        }

        holderAnimator.start()
    }

    override fun bindListener() {
        binding.seekBarProgress.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mProgress = progress
                binding.tvProgress.text = "进度值：$mProgress"
                holderAnimator.setValues(getPropertyValuesHolder())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.btnHAnimate.setOnClickListener {
            holderAnimator.start()
        }
    }

    fun getPropertyValuesHolder(): PropertyValuesHolder {
        val keyframe1 = Keyframe.ofInt(0f, 0)
        val keyframe2 = Keyframe.ofInt(0.5f, 100)
        val keyframe3 = Keyframe.ofInt(1f, mProgress)
        return PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3)
    }
}