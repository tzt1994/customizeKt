package com.tzt.customizekt

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tzt.common.basedepency.dpToPx
import com.tzt.common.basedepency.screenWidth
import kotlinx.android.synthetic.main.dialog_tips.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/23
 */
class TipsDialog: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_tips, container, false)
    }

    override fun onStart() {
        dialog?.window?.attributes?.width = screenWidth() - dpToPx(60).toInt()
        dialog?.window?.setGravity(Gravity.CENTER)
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnIKnow.setOnClickListener {
            dismiss()
        }
    }
}