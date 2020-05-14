package com.tzt.common.basedepency

import android.content.Context
import androidx.fragment.app.Fragment


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
open class BaseFragment: Fragment() {
    open lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}