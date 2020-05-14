package com.tzt.customize.path.bezier.util

import android.graphics.PointF

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
object BezierUtils {
    /**
     * 计算出所有低阶控制点列表
     *
     * @param u 比例
     * @param controllPointList 控制点
     */
    fun calculateAllListPoint(u: Float, controllPointList: List<PointF>): List<List<PointF>> {
        val allLowControllList = ArrayList<List<PointF>>()

        val lowControList = calculateLowListPoint(u, controllPointList)
        allLowControllList.add(lowControList)
        // 二阶以上的贝塞尔曲线继续获取所有低阶控制点列表
        if (lowControList.size > 2) {
            allLowControllList.addAll(calculateAllListPoint(u, lowControList))
        }

        return allLowControllList;
    }

    /**
     * 计算出低一阶控制点列表
     *
     * @param u 比例
     * @param controllPointList 控制点
     */
    fun calculateLowListPoint(u: Float, controllPointList: List<PointF>) : List<PointF> {
        val lowControList = ArrayList<PointF>()

        var p0 = controllPointList[0]
        // 遍历从下标1开始的子集合
        for (p1 in controllPointList.subList(1, controllPointList.size)) {
            lowControList.add(PointF((1 - u) * p0.x + u * p1.x, (1 - u) * p0.y + u * p1.y))

            p0 = p1
        }

        return lowControList;
    }

    /**
     * 构建高阶贝塞尔曲线，具体点数由参数frame决定
     *
     * @param controllPointList 控制点坐标
     * @param frame 帧数
     */
    fun buildBezier(controllPointList: List<PointF>, frame: Int): List<PointF> {
        val pointList = ArrayList<PointF>()

        val delta = 1f / frame

        // 阶数 = 控制点 - 1
        val order = controllPointList.size - 1

        // 循环递增
        var u = 0f
        do {
            pointList.add(
                PointF(
                    calculatePointCoordinate(ValueType.X_TYPE, u, order, 0, controllPointList),
                    calculatePointCoordinate(ValueType.Y_TYPE, u, order, 0, controllPointList)
                )
            )

            u+= delta
        }while (u <= 1f)

        return pointList
    }

    /**
     * 计算坐标 [贝塞尔曲线的核心关键]
     *
     * @param type @link{ValueType.X_TYPE} 表示x轴的坐标， @link{ValueType.Y_TYPE} 表示y轴的坐标
     * @param u 当前比例
     * @param k 阶数
     * @param p 当前点的下标
     * @param controllPointList
     */
    fun calculatePointCoordinate(
        type: ValueType,
        u: Float,
        k: Int,
        p: Int,
        controllPointList: List<PointF>
    ): Float {
        /**
         * 公式解说：（p表示坐标点，后面的数字只是区分）
         * 场景：有一条线p1到p2，p0在中间，求p0的坐标
         *      p1◉--------○----------------◉p2
         *            u    p0
         *
         * 公式：p0 = p1+u*(p2-p1) 整理得出 p0 = (1-u)*p1+u*p2
         */
        // 一阶贝塞尔，直接返回
        if (k == 1) {
            var p1 = 0f
            var p2 = 0f

            if (type == ValueType.X_TYPE) {
                p1 = controllPointList[p].x
                p2 = controllPointList[p + 1].x
            } else {
                p1 = controllPointList[p].y
                p2 = controllPointList[p + 1].y
            }

            return (1 - u) * p1 + u * p2
        } else{
            /**
             * 这里应用了递归的思想：
             * 1阶贝塞尔曲线的端点 依赖于 2阶贝塞尔曲线
             * 2阶贝塞尔曲线的端点 依赖于 3阶贝塞尔曲线
             * ....
             * n-1阶贝塞尔曲线的端点 依赖于 n阶贝塞尔曲线
             *
             * 1阶贝塞尔曲线 则为 真正的贝塞尔曲线存在的点
             */

            return (1 - u) * calculatePointCoordinate(type, u, k - 1, p ,controllPointList) + u * calculatePointCoordinate(type, u, k - 1, p + 1, controllPointList)
        }
    }
}