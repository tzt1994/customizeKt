package com.tzt.customize.base

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tzt.common.basedepency.BaseModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.base.databinding.ActivityCustomBaseBinding
import com.tzt.customize.base.fragment.AngleFragment
import com.tzt.customize.base.fragment.ColorFragment
import com.tzt.customize.base.fragment.CoordinateFragment


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/21
 */
class CustomBaseActivity: BaseActivity<ActivityCustomBaseBinding>() {
    private val modelList = ArrayList<BaseModel>()

    override fun layoutBinding() = ActivityCustomBaseBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "自定义View基础",
        createOriginalIcon {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                when (modelList[mBinding.vpBase.currentItem].fragment) {
                    is CoordinateFragment -> "http://www.gcssloop.com/customview/CoordinateSystem"
                    is AngleFragment -> "http://www.gcssloop.com/customview/AngleAndRadian"
                    is ColorFragment -> "http://www.gcssloop.com/customview/Color"
                    else -> ""
                }
            )))
        }
    )

    override fun initData() {
        modelList.apply {
            add(BaseModel("坐标系", CoordinateFragment()))
            add(BaseModel("角度弧度", AngleFragment()))
            add(BaseModel("颜色", ColorFragment()))
        }

        mBinding.vpBase.adapter = BaseAdapter(supportFragmentManager)
        mBinding.tabBase.setupWithViewPager(mBinding.vpBase)
    }

    inner class BaseAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int)= modelList[position].fragment

        override fun getPageTitle(position: Int)= modelList[position].title

        override fun getCount() = modelList.size

    }
}