package com.tzt.customize.propertyanimation.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.propertyanimation.R
import kotlinx.android.synthetic.main.fragment_interpolator.*


/**
 * Description: 插值器
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class InterpolatorFragment: BaseFragment() {
    private lateinit var animator: ObjectAnimator

    private val list = arrayListOf<Interpolator>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interpolator, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.apply {
            add(LinearInterpolator())
            add(AccelerateDecelerateInterpolator())
            add(AccelerateInterpolator())
            add(DecelerateInterpolator())
            add(AnticipateInterpolator())
            add(OvershootInterpolator())
            add(AnticipateOvershootInterpolator())
            add(BounceInterpolator())
            add(CycleInterpolator(0.5f))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val path = Path()
                path.lineTo(0.25f, 0.25f)
                path.moveTo(0.25f, 1.5f)
                path.lineTo(1f, 1f)
                add(PathInterpolator(path))
            }
            add(FastOutLinearInInterpolator())
            add(FastOutSlowInInterpolator())
            add(LinearOutSlowInInterpolator())
        }
        animator = ObjectAnimator.ofFloat(ivDuration, "translationX", 0f, 600f).apply {
            repeatCount = 0
            duration = 1000
            interpolator = list[0]
        }
        btnInterpolator.text = animator.interpolator.javaClass.simpleName

        btnAnimate.setOnClickListener {
            animator.start()
        }

        btnInterpolator.setOnClickListener {
            showInterpolatorView(it)
        }
    }

    private fun showInterpolatorView(target: View) {
        val popupWindow = PopupWindow(mContext)
        // 列表
        val view = RecyclerView(mContext)
        view.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val adapter = InterpolatorAdapter(list)
        adapter.onItemClickListener =  {
            popupWindow.dismiss()
            animator.interpolator = it
            btnInterpolator.text = it.javaClass.simpleName
        }
        view.adapter = adapter
        popupWindow.contentView = view
        popupWindow.width = dpToPx(240).toInt()
        popupWindow.height = dpToPx(400).toInt()
        popupWindow.showAsDropDown(target, 0, dpToPx(-20).toInt())
    }

    inner class InterpolatorAdapter(var list: ArrayList<Interpolator> = ArrayList()):
        RecyclerView.Adapter<InterpolatorAdapter.InterpolatorViewHolder>() {

        var onItemClickListener: ((Interpolator) -> Unit)? = null

        inner class InterpolatorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val interpolatorName: TextView = itemView.findViewById(R.id.tvInterpolatorName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InterpolatorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_iten_interpolator, parent, false)
        )

        override fun getItemCount()= list.size

        override fun onBindViewHolder(holder: InterpolatorViewHolder, position: Int) {
            val interpolator = list[position]

            holder.interpolatorName.text = interpolator.javaClass.simpleName
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it1 -> it1(interpolator) }
            }
        }
    }
}