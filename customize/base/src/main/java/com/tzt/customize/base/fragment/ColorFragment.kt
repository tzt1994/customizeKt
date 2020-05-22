package com.tzt.customize.base.fragment

import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.base.R
import kotlinx.android.synthetic.main.fragment_angle.*
import kotlinx.android.synthetic.main.fragment_color.*


/**
 * Description: 颜色
 *
 * @author tangzhentao
 * @since 2020/5/21
 */
class ColorFragment: BaseFragment() {
    override fun layoutResID(): Int {
        return R.layout.fragment_color
    }

    override fun initData() {
        val html = "<h2 id=\"一简单介绍颜色\">一.简单介绍颜色</h2>\n" +
                "\n" +
                "<p>安卓支持的颜色模式：</p>\n" +
                "\n" +
                "<table>\n" +
                "  <thead>\n" +
                "    <tr>\n" +
                "      <th style=\"text-align: left\">颜色模式</th>\n" +
                "      <th>备注</th>\n" +
                "    </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">ARGB8888</td>\n" +
                "      <td>四通道高精度(32位)</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">ARGB4444</td>\n" +
                "      <td>四通道低精度(16位)</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">RGB565</td>\n" +
                "      <td><strong>屏幕默认模式</strong>(16位)</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">Alpha8</td>\n" +
                "      <td>仅有透明通道(8位)</td>\n" +
                "    </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<p><em>PS：其中字母表示通道类型，数值表示该类型用多少位二进制来描述。如ARGB8888则表示有四个通道(ARGB),每个对应的通道均用8位来描述。</em></p>\n" +
                "\n" +
                "<p><em>注意：我们常用的是ARGB8888和ARGB4444，而在所有的安卓设备屏幕上默认的模式都是RGB565,请留意这一点。</em></p>\n" +
                "\n" +
                "<p><strong>以ARGB8888为例介绍颜色定义:</strong></p>\n" +
                "\n" +
                "<table>\n" +
                "  <thead>\n" +
                "    <tr>\n" +
                "      <th style=\"text-align: left\">类型</th>\n" +
                "      <th style=\"text-align: left\">解释</th>\n" +
                "      <th style=\"text-align: left\">0(0x00)</th>\n" +
                "      <th style=\"text-align: left\">255(0xff)</th>\n" +
                "    </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">A(Alpha)</td>\n" +
                "      <td style=\"text-align: left\">透明度</td>\n" +
                "      <td style=\"text-align: left\">透明</td>\n" +
                "      <td style=\"text-align: left\">不透明</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">R(Red)</td>\n" +
                "      <td style=\"text-align: left\">红色</td>\n" +
                "      <td style=\"text-align: left\">无色</td>\n" +
                "      <td style=\"text-align: left\">红色</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">G(Green)</td>\n" +
                "      <td style=\"text-align: left\">绿色</td>\n" +
                "      <td style=\"text-align: left\">无色</td>\n" +
                "      <td style=\"text-align: left\">绿色</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: left\">B(Blue)</td>\n" +
                "      <td style=\"text-align: left\">蓝色</td>\n" +
                "      <td style=\"text-align: left\">无色</td>\n" +
                "      <td style=\"text-align: left\">蓝色</td>\n" +
                "    </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<p><em>其中 A R G B 的取值范围均为0~255(即16进制的0x00~0xff)</em></p>\n" +
                "\n" +
                "<p>A 从0x00到0xff表示从透明到不透明。</p>\n" +
                "\n" +
                "<p>RGB 从0x00到0xff表示颜色从浅到深。</p>\n" +
                "\n" +
                "<p><strong>当RGB全取最小值(0或0x000000)时颜色为黑色，全取最大值(255或0xffffff)时颜色为白色</strong></p>\n" +
                "\n"
        wvColor.loadData(html, "text/html", "UTF-8")
    }
}