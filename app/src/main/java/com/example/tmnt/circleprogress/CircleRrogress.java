package com.example.tmnt.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.text.DecimalFormat;

/**
 * Created by tmnt on 2016/5/8.
 */
public class CircleRrogress extends View {

    private int[] mColors;
    private Paint criclePaint;
    private Paint cricleProgressPaint;
    private Paint cricleDefualPaint;
    private float mSweepAnglePer;
    private RectF mColorWheelRectangle = new RectF();

    private float mPercent;
    private int stepnumber, stepnumbernow;
    private float pressExtraStrokeWidth;
    private BarAnimation anim;
    private int stepnumbermax = 12;// 默认最大时间
    private DecimalFormat fnum = new DecimalFormat("#.0");// 格式为保留小数点后一位

    private float circleStrokeWidth;

    private static final String TAG = "CircleRrogress";

    public CircleRrogress(Context context) {
        super(context);
        init();
    }

    public CircleRrogress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mColors = new int[]{0xFF6BB7ED, 0xFF47D9CE, 0xFF56CADC};
        SweepGradient sweepGradient = new SweepGradient(0, 0, mColors, null);
        criclePaint = new Paint();
        criclePaint.setShader(sweepGradient);
        criclePaint.setStyle(Paint.Style.STROKE);
        criclePaint.setAntiAlias(true);
        criclePaint.setStrokeCap(Paint.Cap.ROUND);

        cricleProgressPaint = new Paint();
        cricleProgressPaint.setAntiAlias(true);
        cricleProgressPaint.setColor(Color.rgb(214, 246, 256));
        cricleProgressPaint.setStyle(Paint.Style.STROKE);
        cricleProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        cricleDefualPaint = new Paint();
        cricleDefualPaint.setColor(Color.rgb(127, 127, 127));
        cricleDefualPaint.setAntiAlias(true);
        cricleDefualPaint.setStyle(Paint.Style.STROKE);
        cricleDefualPaint.setStrokeCap(Paint.Cap.ROUND);

        anim = new BarAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(mColorWheelRectangle, 0, 359, false, cricleDefualPaint);
        canvas.drawArc(mColorWheelRectangle, 0, 359, false, cricleProgressPaint);
        canvas.drawArc(mColorWheelRectangle, 0, mSweepAnglePer, false, criclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(height, width);
        setMeasuredDimension(min, min);

        circleStrokeWidth = Textscale(35, min);
        pressExtraStrokeWidth = Textscale(2, min);
        mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
                circleStrokeWidth + pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth);// 设置矩形
        criclePaint.setStrokeWidth(circleStrokeWidth - 5);
        cricleProgressPaint.setStrokeWidth(circleStrokeWidth + 5);
        cricleDefualPaint.setStrokeWidth(circleStrokeWidth - Textscale(2, min));
        cricleDefualPaint.setShadowLayer(Textscale(10, min), 0, 0, Color.rgb(127, 127, 127));

    }

    public float Textscale(float n, float m) {
        return n / 500 * m;
    }

    private class BarAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Log.i(TAG, "applyTransformation: " + interpolatedTime);
            if (interpolatedTime < 0.8f) {
                mPercent = Float.parseFloat(fnum.format(interpolatedTime
                        * stepnumber * 100f / stepnumbermax));// 将浮点值四舍五入保留一位小数
                mSweepAnglePer = interpolatedTime * stepnumber * 360
                        / stepnumbermax;
                stepnumbernow = (int) (interpolatedTime * stepnumber);
            } else {
                mPercent = Float.parseFloat(fnum.format(stepnumber * 100f
                        / stepnumbermax));// 将浮点值四舍五入保留一位小数
                mSweepAnglePer = stepnumber * 360 / stepnumbermax;
                stepnumbernow = stepnumber;

            }
            Log.i(TAG, "applyTransformation: mSweepAnglePer" + mSweepAnglePer);
            postInvalidate();

        }


    }

    public void update(int stepnumber, int time) {
        this.stepnumber = stepnumber;
        anim.setDuration(time);
        //setAnimationTime(time);
        this.startAnimation(anim);
    }

    /**
     * 设置每天的最大步数
     *
     * @param Maxstepnumber
     */
    public void setMaxstepnumber(int Maxstepnumber) {
        stepnumbermax = Maxstepnumber;
    }

    /**
     * 设置进度条颜色
     *
     * @param red
     * @param green
     * @param blue
     */
    public void setColor(int red, int green, int blue) {
        criclePaint.setColor(Color.rgb(red, green, blue));
    }

    /**
     * 设置动画时间
     *
     * @param time
     */
    public void setAnimationTime(int time) {
        anim.setDuration(time * stepnumber / stepnumbermax);// 按照比例设置动画执行时间
    }
}
