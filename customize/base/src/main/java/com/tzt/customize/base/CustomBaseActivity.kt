package com.tzt.customize.base

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tzt.common.basedepency.BaseModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.base.fragment.CoordinateFragment
import kotlinx.android.synthetic.main.activity_custom_base.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/21
 */
class CustomBaseActivity: BaseActivity() {
    private val modelList = ArrayList<BaseModel>()

    override fun layoutResID(): Int {
        return R.layout.activity_custom_base
    }

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "自定义View基础"
        )
    }

    override fun initData() {
        modelList.apply {
            add(BaseModel("坐标系", CoordinateFragment()))
            add(BaseModel("角度弧度", CoordinateFragment()))
            add(BaseModel("颜色", CoordinateFragment()))
        }

        vpBase.adapter = BaseAdapter(supportFragmentManager)
        tabBase.setupWithViewPager(vpBase)
    }

    inner class BaseAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int)= modelList[position].fragment

        override fun getPageTitle(position: Int)= modelList[position].title

        override fun getCount() = modelList.size

    }
}