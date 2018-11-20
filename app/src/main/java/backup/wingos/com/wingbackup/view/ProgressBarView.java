package backup.wingos.com.wingbackup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import backup.wingos.com.wingbackup.R;

/**
 * Created by xiongbo on 2018/11/15.
 */

public class ProgressBarView extends View {

    private static final String TAG ="ProgressBarView" ;
<<<<<<< HEAD

    private int startColor;

    private int endColor;

    private int shadowColor;
=======
    /**
     * 渐变色的起始颜色
     */
    private int startColor;
    /**
     * 渐变色的结束时候的颜色
     */
    private int endColor;
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
    /**
     * 没发数据时候  进度条默认圆弧的颜色
     */
    private int defaultColor;
    /**
     * 内圆弧的半径
     */
    private float insideCircleRadius;
    /**
     * 外圆弧的半径
     */
    private float outsideCircleRadius;
    /**
     * 圆弧与圆弧之间的距离
     */
    private float circleWidth;
    /**
     * 进度条的圆心的横坐标
     */
    private float centerX;
    private float centerY;
    /**
     * 当前进度
     */
    private int progress;

    private int startAngle;

    private Paint defaultCirclePaint;
    private Paint circlePaint;

<<<<<<< HEAD
    private String text;

    private SweepGradient sweepGradient;
=======
    private LinearGradient linearGradient;

>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e

    public ProgressBarView(Context context) {
        this(context,null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
<<<<<<< HEAD

=======
        startColor =typedArray.getColor(R.styleable.ProgressBarView_progressbar_start_color, Color.BLACK);
        endColor=typedArray.getColor(R.styleable.ProgressBarView_progressbar_end_color,Color.WHITE);
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
        defaultColor=typedArray.getColor(R.styleable.ProgressBarView_progressbar_default_color,Color.WHITE);
        startColor=typedArray.getColor(R.styleable.ProgressBarView_progressbar_color_start,Color.WHITE);
        endColor = typedArray.getColor(R.styleable.ProgressBarView_progressbar_color_end,Color.WHITE);
        shadowColor = typedArray.getColor(R.styleable.ProgressBarView_progressbar_shadow_color,Color.WHITE);
        insideCircleRadius=typedArray.getDimension(R.styleable.ProgressBarView_progressbar_inside_circle_radius,50);
        outsideCircleRadius=typedArray.getDimension(R.styleable.ProgressBarView_progressbar_outside_circle_radius,50);
        circleWidth = typedArray.getDimension(R.styleable.ProgressBarView_progressbar_circle_width,40);
        progress = typedArray.getInteger(R.styleable.ProgressBarView_progressbar_progress,350);

        defaultCirclePaint = new Paint();
        defaultCirclePaint.setStyle(Paint.Style.FILL);
        defaultCirclePaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
<<<<<<< HEAD
        text="60%";
=======


>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2;
        centerY=h/2;
<<<<<<< HEAD
        startAngle=0;
        if(progress!=360){
            startAngle+=10;
            progress-=10;
            sweepGradient = new SweepGradient(centerX,centerY,new int[]{startColor,endColor},new float[]{0,progress/360f});
        }else{
            sweepGradient = new SweepGradient(centerX,centerY,new int[]{startColor,endColor,startColor},new float[]{0,0.8f,1f});
        }
=======
        startAngle=-90;
        linearGradient = new LinearGradient(centerX-outsideCircleRadius-circleWidth,
                centerY-outsideCircleRadius-circleWidth,
                centerX+outsideCircleRadius+circleWidth,
                centerY+outsideCircleRadius+circleWidth
                ,new int[]{Color.parseColor("#24c3FF"),Color.parseColor("#137DFF")},
                null, Shader.TileMode.CLAMP);
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        drawShadow(canvas);
        //绘制默认的圆弧
        drawDefaultCircularArc(canvas);
//        当有进度的时候，根据类型来绘制不同的圆弧
        drawTypeCircularArc(canvas);
//
        drawSemicircle(canvas,startAngle,false);
        drawSemicircle(canvas,startAngle+progress,true);

        drawText(canvas,text);
    }

    /***
     * 用来设置当前的进度
     * @param progress
     */
    public void setProgress(int progress){
        this.progress=progress;
        text=String.valueOf(progress/100)+"%";
        if(progress!=360){
            startAngle+=10;
            progress-=10;
            sweepGradient = new SweepGradient(centerX,centerY,new int[]{startColor,endColor},new float[]{0,progress/360f});
        }else{
            sweepGradient = new SweepGradient(centerX,centerY,new int[]{startColor,endColor,startColor},new float[]{0,0.8f,1f});
        }
        invalidate();
    }

<<<<<<< HEAD


=======
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawDefaultCircularArc(Canvas canvas){
        defaultCirclePaint.reset();
        defaultCirclePaint.setColor(Color.WHITE);
        int sc=canvas.saveLayer(new RectF(centerX-outsideCircleRadius,centerY-outsideCircleRadius,centerX+outsideCircleRadius,centerY+outsideCircleRadius), defaultCirclePaint);
        canvas.drawCircle(centerX,centerY,insideCircleRadius, defaultCirclePaint);
        defaultCirclePaint.setColor(defaultColor);
        defaultCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawCircle(centerX,centerY,outsideCircleRadius, defaultCirclePaint);
        canvas.restoreToCount(sc);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawTypeCircularArc(Canvas canvas){
<<<<<<< HEAD
        circlePaint.setShader(sweepGradient);
=======
        circlePaint.setShader(linearGradient);
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
        RectF outRectF = new RectF(centerX-outsideCircleRadius-circleWidth,centerY-outsideCircleRadius-circleWidth,
                centerX+outsideCircleRadius+circleWidth,centerY+outsideCircleRadius+circleWidth);
        RectF insideRectF = new RectF(centerX-insideCircleRadius+circleWidth,centerY-insideCircleRadius+circleWidth,
                centerX+insideCircleRadius-circleWidth,centerY+insideCircleRadius-circleWidth);
        int sc=canvas.saveLayer(outRectF,circlePaint);
        canvas.translate(centerX,centerY);
        canvas.rotate(-90);
        canvas.translate(-centerX,-centerY);
        canvas.drawArc(insideRectF,startAngle,progress,true,circlePaint);
        circlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawArc(outRectF,startAngle,progress, true,circlePaint);
        circlePaint.setAlpha(0);
        canvas.drawCircle(centerX,centerY,insideCircleRadius-circleWidth,circlePaint);
        canvas.restoreToCount(sc);
        circlePaint.reset();
        circlePaint.setShader(sweepGradient);
    }

    /**
     * 绘制两端的小圆
     * @param canvas
     */
<<<<<<< HEAD
    private void drawSemicircle(Canvas canvas,int startAngle,boolean isClockwise){
        canvas.save();
        canvas.translate(centerX,centerY);
        canvas.rotate(-90);
        canvas.translate(-centerX,-centerY);
        int sweepAngle= isClockwise? 180:-180;
        startAngle = isClockwise? startAngle-1:startAngle+1;
        double anglePi = Math.toRadians(startAngle);
=======
    private void drawSemicircle(Canvas canvas,int angle,boolean isClockwise){
        circlePaint.reset();
        int sweepAngle;
        circlePaint.setShader(linearGradient);
        if(isClockwise){
            sweepAngle=180;
            angle = angle-1;
        }else{
            sweepAngle=-180;
            angle+=1;
        }
        double anglePi = Math.toRadians(angle);
>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
        float radius = insideCircleRadius+(outsideCircleRadius-insideCircleRadius)/2;
        float circleRadius = radius-insideCircleRadius+circleWidth;
        float circleCenterY= (float) (Math.sin(anglePi)*radius+centerY);
        float circleCenterX = (float) (Math.cos(anglePi)*radius+centerX);
<<<<<<< HEAD
=======

>>>>>>> 43dedc115612f05daef2b845d5eb7363256d6d4e
        RectF rectF = new RectF(circleCenterX-circleRadius,circleCenterY-circleRadius,
                circleCenterX+circleRadius,circleCenterY+circleRadius);
        canvas.drawArc(rectF,startAngle,sweepAngle,true,circlePaint);
        canvas.restore();
    }


    private void drawText(Canvas canvas,String text){
        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        Paint.FontMetrics fp = textPaint.getFontMetrics();
        float length = textPaint.measureText(text);
        canvas.drawText(text,centerX-length/2,centerY-fp.top/2-fp.bottom/2,textPaint);
    }

    /**
     * 绘制阴影部分
     * @param canvas
     */
    private void drawShadow(Canvas canvas){
        canvas.save();
        canvas.translate(centerX,centerY);
        canvas.rotate(-90);
        canvas.translate(-centerX,-centerY);
        RadialGradient radialGradient = new RadialGradient(centerX,centerY,insideCircleRadius,new int[]{Color.TRANSPARENT,shadowColor},new float[]{0.8f,0.9f}, Shader.TileMode.CLAMP);
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(shadowColor);
        shadowPaint.setAlpha(180);
        shadowPaint.setShader(radialGradient);
        RectF insideRectF = new RectF(centerX-insideCircleRadius+circleWidth,centerY-insideCircleRadius+circleWidth,
                centerX+insideCircleRadius-circleWidth,centerY+insideCircleRadius-circleWidth);
        canvas.drawArc(insideRectF,startAngle,progress, true,shadowPaint);
        canvas.restore();

        ShadowDrawable shadowDrawable = new ShadowDrawable(shadowColor, 15,0,0,
                startAngle,progress,insideCircleRadius,outsideCircleRadius,circleWidth);
        shadowDrawable.setBounds(centerX-outsideCircleRadius-circleWidth,centerY-outsideCircleRadius-circleWidth,
                centerX+outsideCircleRadius+circleWidth,centerY+outsideCircleRadius+circleWidth);
        ShadowDrawable.setShadowDrawable(this,shadowDrawable);
    }


}
