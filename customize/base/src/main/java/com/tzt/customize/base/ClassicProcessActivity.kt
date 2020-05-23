package com.tzt.customize.base

import android.content.Intent
import android.net.Uri
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import kotlinx.android.synthetic.main.activity_classic_process.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/22
 */
class ClassicProcessActivity: BaseActivity() {

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "分类与流程",
            createOriginalIcon {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gcssloop.com/customview/CustomViewProcess")))
            }
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_classic_process
    }

    override fun initData() {
        tvClassicProcess.text = "自定义View绘制流程函数调用链"

        tvViewClassic.text = "一.自定义View分类"
        tvSubViewClassic.text = "1.自定义ViewGroup\n" +
                "自定义ViewGroup一般是利用现有的组件根据特定的布局方式来组成新的组件，大多继承自ViewGroup或各种Layout，包含有子View。\n" +
                "\n" +
                "例如：应用底部导航条中的条目，一般都是上面图标(ImageView)，下面文字(TextView)，那么这两个就可以用自定义ViewGroup组合成为一个Veiw，提供两个属性分别用来设置文字和图片，使用起来会更加方便。\n" +
                "\n" +
                "2.自定义View\n" +
                "在没有现成的View，需要自己实现的时候，就使用自定义View，一般继承自View，SurfaceView或其他的View，不包含子View。\n" +
                "\n" +
                "例如：制作一个支持自动加载网络图片的ImageView，制作图表等。"

        tvFunction.text = "二.几个重要的函数"
        tvSubFunction1.text = "1.构造函数\n" +
                "构造函数是View的入口，可以用于初始化一些的内容，和获取自定义属性。\n" +
                "\n" +
                "View的构造函数有四种重载分别如下:\n" +
                "有四个参数的构造函数在API21的时候才添加上，暂不考虑。"
        tvSubFunction2.text = "2.测量View大小(onMeasure)\n" +
                "View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量。\n" +
                "\n" +
                "测量View大小使用的是onMeasure函数，我们可以从onMeasure的两个参数中取出宽高的相关数据"
        tvSubFunction3.text = "从上面可以看出 onMeasure 函数中有 widthMeasureSpec 和 heightMeasureSpec 这两个 int 类型的参数， 毫无疑问他们是和宽高相关的， 但它们其实不是宽和高， 而是由宽、高和各自方向上对应的测量模式来合成的一个值：\n" +
                "\n" +
                "测量模式一共有三种， 被定义在 Android 中的 View 类的一个内部类View.MeasureSpec中："
        tvSubFunction4.text = "PS: 实际上关于上面的东西了解即可，在实际运用之中只需要记住有三种模式，用 MeasureSpec 的 getSize是获取数值， getMode是获取模式即可。\n" +
                "\n" +
                "注意：\n" +
                "如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec); 要调用 setMeasuredDimension( widthsize, heightsize); 这个函数。\n\n" +
                "3.确定View大小(onSizeChanged)\n" +
                "这个函数在视图大小发生改变时调用。\n" +
                "因为View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。\n" +
                "\n" +
                "onSizeChanged如下：\n" +
                "\n" +
                "@Override\n" +
                "protected void onSizeChanged(int w, int h, int oldw, int oldh) {\n" +
                "    super.onSizeChanged(w, h, oldw, oldh);\n" +
                "}\n\n" +
                "4.确定子View布局位置(onLayout)\n" +
                "确定布局的函数是onLayout，它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。\n" +
                "\n" +
                "在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。\n" +
                "\n" +
                "child.layout(l, t, r, b);"

        tvSubFunction5.text = "5.绘制内容(onDraw)\n" +
                "onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。\n" +
                "\n" +
                "@Override\n" +
                "protected void onDraw(Canvas canvas) {\n" +
                "    super.onDraw(canvas);\n" +
                "}\n\n\n" +
                "自定义View流程："
    }
}