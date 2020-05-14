package com.tzt.customize.propertyanimation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.R
import kotlinx.android.synthetic.main.fragment_view_property.*


/**
 * Description: view的动画
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ViewPropertyFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_property, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnViewPro.setOnClickListener {
            ivAnimator.animate().apply {
                translationXBy(500f)
                translationYBy(500f)
                scaleXBy(1f)
                scaleYBy(1f)
                alphaBy(0f)
                rotationBy(360f)
                duration = 1000
                withEndAction {
                    ivAnimator.animate().apply {
                        translationXBy(-500f)
                        translationYBy(-500f)
                        scaleXBy(-1f)
                        scaleYBy(-1f)
                        alphaBy(1f)
                        rotationBy(-360f)
                        duration = 1000
                    }
                }
            }
        }
    }
}