package com.tzt.customize.propertyanimation.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.databinding.FragmentDurationBinding


/**
 * Description: 属性动画设置时长
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class DurationFragment: BaseFragment<FragmentDurationBinding>() {
    private lateinit var animator: ObjectAnimator

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDurationBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        animator = ObjectAnimator.ofFloat(binding.ivDuration, "rotation", 0f, 360f)
        binding.tvProgress.text = binding.seekbarDuration.progress.toString()
    }

    override fun bindListener() {
        binding.seekbarDuration.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvProgress.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.btnDurationAnimate.setOnClickListener {
            startAniamtion(binding.seekbarDuration.progress.toLong())
        }
    }

    private fun startAniamtion(time: Long) {
        animator.apply {
            duration = time
            start()
        }
    }
}