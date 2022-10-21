package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.customize.action.databinding.ActivityMultiFingerlBinding

class MultiFingerlActivity : BaseActivity<ActivityMultiFingerlBinding>() {
    override fun layoutBinding() =  ActivityMultiFingerlBinding.inflate(layoutInflater, null, false)
}