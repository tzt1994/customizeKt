package com.tzt.customize.transform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.PaintModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.transform.databinding.ActivityClipTransformBinding
import com.tzt.customize.transform.fragment.ClipTransFormFragment
import com.tzt.customize.transform.fragment.TransFormShowFragment


/**
 * Description: 裁剪和几何变换
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ClipTransFormActivity: BaseActivity<ActivityClipTransformBinding>() {
    private var paintModels = ArrayList<PaintModel>()

    override fun layoutBinding() = ActivityClipTransformBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "裁剪和几何变换",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hencoder.com/ui-1-4/")))
        }
    )

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

        mBinding.VpBezier.adapter = BezierAdapter(supportFragmentManager)
        mBinding.tabBezier.setupWithViewPager(mBinding.VpBezier)
    }

    inner class BezierAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getPageTitle(position: Int): CharSequence? {
            return paintModels[position].title
        }

        override fun getItem(position: Int) = paintModels[position].fragment

        override fun getCount() = paintModels.size
    }
}