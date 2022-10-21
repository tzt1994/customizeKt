package com.tzt.customizekt

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tzt.common.basedepency.dpToPx
import com.tzt.common.basedepency.screenWidth
import com.tzt.customizekt.databinding.DialogTipsBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/23
 */
class TipsDialog: DialogFragment() {
    private lateinit var binding: DialogTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomProgressDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        dialog?.window?.attributes?.width = screenWidth() - dpToPx(60).toInt()
        dialog?.window?.setGravity(Gravity.CENTER)
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnIKnow.setOnClickListener {
            dismiss()
        }
    }
}