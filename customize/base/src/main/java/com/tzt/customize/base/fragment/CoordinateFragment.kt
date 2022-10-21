package com.tzt.customize.base.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.base.databinding.FragmentCoordinateBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/21
 */
class CoordinateFragment: BaseFragment<FragmentCoordinateBinding>() {
    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCoordinateBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        binding.apply {
            tvCoordinate.text = "一.屏幕坐标系和数学坐标系的区别"
            tvSubCoordinate.text = "由于移动设备一般定义屏幕左上角为坐标原点，向右为x轴增大方向(正)，向下为y轴增大方向(正)，" +
                    " 所以在手机屏幕上的坐标系与数学中常见的坐标系是稍微有点差别的"

            tvViewCoordinate.text = "二.View的坐标系"
            tvSubViewCoordinate.text = "注意：View的坐标系统是相对于父控件而言的.\n" +
                    "\tgetTop();       获取子View左上角距父View顶部的距离\n" +
                    "\tgetLeft();      获取子View左上角距父View左侧的距离\n" +
                    "\tgetBottom();    获取子View右下角距父View顶部的距离\n" +
                    "\tgetRight();     获取子View右下角距父View左侧的距离"

            tvRaW.text = "MotionEvent中 get 和 getRaw 的区别"
            tvSubRaw.text = "\tevent.getX();       触摸点相对于其所在组件坐标系的坐标\n" +
                    "\tevent.getY();\n" +
                    "\n" +
                    "\tevent.getRawX();    触摸点相对于屏幕默认坐标系的坐标\n" +
                    "\tevent.getRawY();"

            tvImportant.text = "核心要点"
            tvSubImportant.text = "1. 在数学中常见的坐标系与屏幕默认坐标系的差别\n" +
                    "2. View的坐标系是相对于父控件而言的\n" +
                    "3. MotionEvent中get和getRaw的区别"
        }
    }
}