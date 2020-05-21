package com.tzt.customize.transform

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.transform.fragment.ClipTransFormFragment
import com.tzt.customize.transform.fragment.TransFormShowFragment
import kotlinx.android.synthetic.main.activity_clip_transform.*


/**
 * Description: 裁剪和几何变换
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ClipTransFormActivity: BaseActivity() {
    private var paintModels = ArrayList<PaintModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "裁剪和几何变换"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_clip_transform
    }

    override fun initData() {
        paintModels.apply {
            // 范围裁剪
            val clipFragment = ClipTransFormFragment()
            val clipBundle = Bundle()
            clipBundle.putInt("clip_transform_type", ClipTransFormFragment.CLIP)
            clipFragment.arguments = clipBundle
            add(PaintModel("范围裁剪", clipFragment))
            // 画布几何变换
            val canvasTransFormFragment = ClipTransFormFragment()
            val canvasTransFormBundle = Bundle()
            canvasTransFormBundle.putInt("clip_transform_type", ClipTransFormFragment.CANVAS_TRANSFORM)
            canvasTransFormFragment.arguments = canvasTransFormBundle
            add(PaintModel("Canvas几何变换", canvasTransFormFragment))
            // Matrix几何变换
            val matrixTransFormFragment = ClipTransFormFragment()
            val matrixTransFormBundle = Bundle()
            matrixTransFormBundle.putInt("clip_transform_type", ClipTransFormFragment.MATRIX_TRANSFORM)
            matrixTransFormFragment.arguments = matrixTransFormBundle
            add(PaintModel("Matrix几何变换", matrixTransFormFragment))
            // Camera几何变换
            val cameraTransFormFragment = ClipTransFormFragment()
            val cameraTransFormBundle = Bundle()
            cameraTransFormBundle.putInt("clip_transform_type", ClipTransFormFragment.CAMERA_TRANSFORM)
            cameraTransFormFragment.arguments = cameraTransFormBundle
            add(PaintModel("Camera几何变换", cameraTransFormFragment))
            add(PaintModel("效果展示", TransFormShowFragment()))
        }

        VpBezier.adapter = BezierAdapter(supportFragmentManager)
        tabBezier.setupWithViewPager(VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return paintModels[position].title
        }

        override fun getItem(position: Int) = paintModels[position].fragment

        override fun getCount() = paintModels.size
    }
}