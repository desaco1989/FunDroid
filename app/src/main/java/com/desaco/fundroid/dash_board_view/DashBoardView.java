package com.desaco.fundroid.dash_board_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.desaco.fundroid.R;


/**
 * @author dengwen
 * @date 2019/12/23.
 * 引用 https://www.jianshu.com/p/f36bb920e6b3
 */
public class DashBoardView extends View {

    // 1d953f 绿色；刻度起始位置颜色； ffffff
    private static final int DEFAULT_COLOR_LOWER = Color.parseColor("#ffffff");
    // 228fbd 蓝色；指针和平均速度的色值； 刻度中间位置色值
//    private static final int DEFAULT_COLOR_MIDDLE = Color.parseColor("#ffffff");
    // Color.RED； 刻度结束位置色值； Color.parseColor("#ffffff")
//    private static final int DEFAULT_COLOR_HIGH = Color.parseColor("#ffffff");
    // Color.BLACK 这个仪表盘做什么用的提示 完成率温度等 text_title_dial
    private static final int DEAFAULT_COLOR_TITLE = Color.BLACK;

    private static final int DEFAULT_TEXT_SIZE_DIAL = 12; //  仪表盘上刻度，size=11
    private static final int DEFAULT_STROKE_WIDTH = 1; // 弧形宽度 WIDTH = 8
    private static final int DEFAULT_RADIUS_DIAL = 144; // 圆弧默认半径， 默认是128
    private static final int DEAFAULT_TITLE_SIZE = 16; //
    private static final int DEFAULT_VALUE_SIZE = 16; // 仪表盘中字体大小  2MB/S ,size=28;
    private static final int DEFAULT_ANIM_PLAY_TIME = 100; // 动画展示时间

    private int colorDialLower;
//    private int colorDialMiddle;
//    private int colorDialHigh;
    private int textSizeDial;
    private int strokeWidthDial;
    private String titleDial;
    private int titleDialSize;
    private int titleDialColor;
    private int valueTextSize;
    private int animPlayTime;

    private int radiusDial;
    private int mRealRadius;
    private float currentValue;

    private Paint arcPaint;
    private RectF mRect;
    private Paint pointerPaint;
    private Paint.FontMetrics fontMetrics;
    private Paint titlePaint;
    private Path pointerPath;

    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        colorDialLower = attributes.getColor(R.styleable.ClockView_color_dial_lower, DEFAULT_COLOR_LOWER);
//        colorDialMiddle = attributes.getColor(R.styleable.ClockView_color_dial_middle, DEFAULT_COLOR_MIDDLE);
//        colorDialHigh = attributes.getColor(R.styleable.ClockView_color_dial_high, DEFAULT_COLOR_HIGH);

        textSizeDial = (int) attributes.getDimension(R.styleable.ClockView_text_size_dial, sp2px(DEFAULT_TEXT_SIZE_DIAL));
        strokeWidthDial = (int) attributes.getDimension(R.styleable.ClockView_stroke_width_dial, dp2px(DEFAULT_STROKE_WIDTH));
        radiusDial = (int) attributes.getDimension(R.styleable.ClockView_radius_circle_dial, dp2px(DEFAULT_RADIUS_DIAL));
        titleDial = attributes.getString(R.styleable.ClockView_text_title_dial);
        titleDialSize = (int) attributes.getDimension(R.styleable.ClockView_text_title_size, dp2px(DEAFAULT_TITLE_SIZE));
        titleDialColor = attributes.getColor(R.styleable.ClockView_text_title_color, DEAFAULT_COLOR_TITLE);
        valueTextSize = (int) attributes.getDimension(R.styleable.ClockView_text_size_value, dp2px(DEFAULT_VALUE_SIZE));
        animPlayTime = attributes.getInt(R.styleable.ClockView_animator_play_time, DEFAULT_ANIM_PLAY_TIME);
    }

    private void initPaint() {
        // 弧形即半圆的设置
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE); // STROKE
        arcPaint.setStrokeWidth(strokeWidthDial);
        // 长指针和短指针的设置
        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setTextSize(textSizeDial);
        pointerPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = pointerPaint.getFontMetrics();
        // 仪表盘的其他设置
        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setFakeBoldText(true);

        pointerPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth, mHeight;
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = getPaddingLeft() + radiusDial * 2 + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(mWidth, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = getPaddingTop() + radiusDial * 2 + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(mHeight, heightSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);

        radiusDial = Math.min((getMeasuredWidth() - getPaddingLeft() - getPaddingRight()),
                (getMeasuredHeight() - getPaddingTop() - getPaddingBottom())) / 2;
        mRealRadius = radiusDial - strokeWidthDial / 2 - 8; // 弧形半圆长 radiusDial - strokeWidthDial / 2 ；
        mRect = new RectF(-mRealRadius, -mRealRadius, mRealRadius, mRealRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawPointerLine(canvas);
        drawTitleDial(canvas);
        drawPointer(canvas);
    }

    private void drawArc(Canvas canvas) { // 绘制弧形 圆圈
        arcPaint.setColor(colorDialLower);

        canvas.translate(getPaddingLeft() + radiusDial, getPaddingTop() + radiusDial);
//        arcPaint.setColor(colorDialLower);
        canvas.drawArc(mRect, 135, 54, false, arcPaint);
//        arcPaint.setColor(colorDialMiddle);
        canvas.drawArc(mRect, 189, 162, false, arcPaint);
//        arcPaint.setColor(colorDialHigh);
        canvas.drawArc(mRect, 351, 54, false, arcPaint);
    }

    private void drawPointerLine(Canvas canvas) { // 短指针，中指针的设置
        canvas.rotate(135);
        for (int i = 0; i < 101; i++) { // 一共需要绘制101个表针
            pointerPaint.setColor(colorDialLower); // TODO ，10/20/30等色值设置和刻度的设置

            if (i % 10 == 0) { // 长表针 内指针
                pointerPaint.setStrokeWidth(2); // Width=6, 指针的宽度
                canvas.drawLine(radiusDial, 0, radiusDial - strokeWidthDial - 15, 0, pointerPaint);
                drawPointerText(canvas, i);
            } else { // 短表针 外指针
                pointerPaint.setStrokeWidth(2); // Width=3, 指针的宽度
                canvas.drawLine(radiusDial, 0, radiusDial + strokeWidthDial - dp2px(9), 0, pointerPaint);
            }
            canvas.rotate(2.7f); // 2.7f
        }
    }

    /**
     * 当前网速刻度渐变
     */
    public void speedColorGradient() {
//        canvas.drawLine(100, 50, 100, 400, paint);
//        LinearGradient linearGradient = new LinearGradient(150, 50, 150, 300, new int[]{
//                Color.rgb(255, 189, 22),
//                Color.rgb(221, 43, 6),
//                Color.rgb(0, 25, 233),
//                Color.rgb(0, 232, 210)},
//                new float[]{0, .3F, .6F, .9F}, Shader.TileMode.CLAMP);
//        // new float[]{},中的数据表示相对位置，将150,50,150,300，划分10个单位，.3，.6，.9表示它的绝对位置。300到400，将直接画出rgb（0,232,210）
//        paint.setShader(linearGradient);
//        canvas.drawLine(150, 50, 150, 400, paint);
    }

    private void drawPointerText(Canvas canvas, int i) { // 10/20/30等色值设置
//        pointerPaint.setColor(Color.parseColor("#FF0066"));
        canvas.save();
        int currentCenterX = (int) (radiusDial - strokeWidthDial - dp2px(21) - pointerPaint.measureText(String.valueOf(i)) / 2);
        canvas.translate(currentCenterX, 0);
        canvas.rotate(360 - 135 - 2.7f * i); // 坐标系总旋转角度为360度

        int textBaseLine = (int) (0 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        canvas.drawText(String.valueOf(i), 0, textBaseLine, pointerPaint);
        canvas.restore();
    }

    private void drawTitleDial(Canvas canvas) { // 绘制仪表盘上的指针和平均速度的色值
        // 仪表盘的作用 如网络速度仪表盘等
        titlePaint.setColor(colorDialLower);
        titlePaint.setTextSize(titleDialSize);
        canvas.rotate(-47.7f); // 恢复坐标系为起始中心位置
        canvas.drawText(titleDial, 0, -radiusDial / 3, titlePaint);
        // 网速平均速度的色值
        titlePaint.setColor(colorDialLower);
        titlePaint.setTextSize(valueTextSize);
        canvas.drawText(currentValue + " MB/S", 0, radiusDial * 2 / 3, titlePaint);
    }

    private void drawPointer(Canvas canvas) { // 最长指针颜色
//        titlePaint.setColor(Color.parseColor("#FF0066")); // TODO
        int currentDegree = (int) (currentValue * 2.7 + 135);
        canvas.rotate(currentDegree);

        pointerPath.moveTo(radiusDial - strokeWidthDial - dp2px(12), 0);
        pointerPath.lineTo(0, -dp2px(5));
        pointerPath.lineTo(-12, 0);
        pointerPath.lineTo(0, dp2px(5));
        pointerPath.close();
        canvas.drawPath(pointerPath, titlePaint);
    }

    public void setCompleteDegree(float degree) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, degree);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) (Math.round((float) animation.getAnimatedValue() * 100)) / 100;
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(animPlayTime);
        animator.start();
    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }
}
