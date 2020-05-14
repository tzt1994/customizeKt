package com.tzt.customize.paint.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseActivity
import com.tzt.common.basedepency.screenWidth
import com.tzt.customize.paint.R
import com.tzt.customize.paint.data.ColorMatrixMode
import com.tzt.customize.paint.data.ColorMatrixModel
import kotlinx.android.synthetic.main.activity_color_matrix.*
import java.lang.Exception


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ColorMatrixColorFilterActivity: BaseActivity() {
    private val matrixList = ArrayList<ColorMatrixModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_matrix)

        llImg.layoutParams.height = screenWidth() / 2

        checkBoxAnimation.setOnCheckedChangeListener { _, isChecked ->
            etDuration.isFocusable = isChecked
            cMatrixView.enableAnimaiton = isChecked
        }

        etDuration.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    cMatrixView.animatorDuration = s.toString().toLong()
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })

        // 亮度变化
        seekBarBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cMatrixView.brightness = progress - 255
                tvBrightness.text = "Brightness(${cMatrixView.brightness})\n亮度"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 对比度变化
        seekBarContrast.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cMatrixView.contrast = progress / 100f
                tvContrast.text = "Contrast(${cMatrixView.contrast})\n对比度"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        matrixList.apply {
            add(ColorMatrixModel(ColorMatrixMode.GREY_SCALE))
            add(ColorMatrixModel(ColorMatrixMode.INVERT))
            add(ColorMatrixModel(ColorMatrixMode.RGB_TO_BGR))
            add(ColorMatrixModel(ColorMatrixMode.SEPIA))
            add(ColorMatrixModel(ColorMatrixMode.BLACK_AND_WHITE))
            add(ColorMatrixModel(ColorMatrixMode.BRIGHT))
            add(ColorMatrixModel(ColorMatrixMode.VINTAGE_PINHOLE))
            add(ColorMatrixModel(ColorMatrixMode.KODACHROME))
            add(ColorMatrixModel(ColorMatrixMode.TECHNICOLOR))
            add(ColorMatrixModel(ColorMatrixMode.SATURATION))
            add(ColorMatrixModel(ColorMatrixMode.NONE, true))
        }
        recyclerColorMatrix.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerColorMatrix.adapter = ColorMatrixAdapter()
    }

    inner class ColorMatrixAdapter: RecyclerView.Adapter<ColorMatrixAdapter.ColorMatrixViewHolder>() {
        inner class ColorMatrixViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val des: TextView = itemView.findViewById(R.id.tvDescrip)
            val seekBar: SeekBar = itemView.findViewById(R.id.seekBarSatution)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ColorMatrixViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_color_matrix, parent, false)
        )

        override fun getItemCount() = matrixList.size

        override fun onBindViewHolder(holder: ColorMatrixViewHolder, position: Int) {
            val model = matrixList[position]
            holder.itemView.setBackgroundColor(Color.parseColor(if (model.enableSelect) "#e0e0e0" else "#ffffff"))

            val des = "${ColorMatrixMode.getColorMatrixModeDesEnglish(model.mode)}(${ColorMatrixMode.getColorMatrixModeDes(model.mode)})"
            holder.des.text = des
            holder.seekBar.progress = (cMatrixView.saturation * 100f).toInt()
            holder.seekBar.visibility = if (model.mode == ColorMatrixMode.SATURATION) View.VISIBLE else View.GONE

            holder.itemView.setOnClickListener{
                if (!model.enableSelect) {
                    for (data in matrixList) {
                        data.enableSelect = matrixList.indexOf(data) == position
                    }
                    cMatrixView.model = model.mode
                    if (cMatrixView.model == ColorMatrixMode.NONE) {
                        // 无风格时设置默认值
                        seekBarBrightness.progress = 255
                        seekBarContrast.progress = 100
                    }

                    notifyDataSetChanged()
                }
            }

            holder.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (model.enableSelect) {
                            cMatrixView.saturation = progress / 100f
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        if (!model.enableSelect) {
                            for (data in matrixList) {
                                data.enableSelect = matrixList.indexOf(data) == position
                            }
                            notifyDataSetChanged()
                        }
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
        }


    }
}