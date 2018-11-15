package backup.wingos.com.wingbackup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
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

    /**
     * 获取进度条的种类
     */
    private int type;
    /**
     * 进度条发送数据时候圆弧的颜色
     */
    private int sendColor;
    /**
     * 进度接收数据时候圆弧的颜色
     */
    private int receiveColor;
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


    public ProgressBarView(Context context) {
        this(context,null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        type = typedArray.getInteger(R.styleable.ProgressBarView_progressbar_type,1);
        sendColor =typedArray.getColor(R.styleable.ProgressBarView_progressbar_send_color, Color.BLACK);
        receiveColor=typedArray.getColor(R.styleable.ProgressBarView_progressbar_receive_color,Color.WHITE);
        defaultColor=typedArray.getColor(R.styleable.ProgressBarView_progressbar_default_color,Color.WHITE);
        insideCircleRadius=typedArray.getDimension(R.styleable.ProgressBarView_progressbar_inside_circle_radius,50);
        outsideCircleRadius=typedArray.getDimension(R.styleable.ProgressBarView_progressbar_outside_circle_radius,50);
        circleWidth = typedArray.getDimension(R.styleable.ProgressBarView_progressbar_circle_width,40);
        progress = typedArray.getInteger(R.styleable.ProgressBarView_progressbar_progress,90);

        defaultCirclePaint = new Paint();
        defaultCirclePaint.setStyle(Paint.Style.FILL);
        defaultCirclePaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2;
        centerY=h/2;
        startAngle=-90;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(progress!=360){
            startAngle+=2;
            progress-=2;
        }
        //绘制默认的圆弧
        drawDefaultCircularArc(canvas);
//        当有进度的时候，根据类型来绘制不同的圆弧
        drawTypeCircularArc(canvas);

        drawSemicircle(canvas,startAngle,false);
        drawSemicircle(canvas,startAngle+progress,true);
    }

    /***
     * 用来设置当前的进度
     * @param progress
     */
    public void setProgress(int progress){
        this.progress=progress;
        invalidate();
    }

    public void setType(int type){
        this.type=type;
        invalidate();
    }


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
        LinearGradient linearGradient;
        if(type==1){
//            circlePaint.setColor(sendColor);
            linearGradient = new LinearGradient(centerX-outsideCircleRadius-circleWidth,
                    centerY-outsideCircleRadius-circleWidth,
                    centerX+outsideCircleRadius+circleWidth,
                    centerY+outsideCircleRadius+circleWidth
                    ,new int[]{Color.parseColor("#24c3FF"),Color.parseColor("#1991FF")},
                    null, Shader.TileMode.CLAMP);
            circlePaint.setShader(linearGradient);
        }else{
            circlePaint.setColor(receiveColor);
        }
        RectF outRectF = new RectF(centerX-outsideCircleRadius-circleWidth,centerY-outsideCircleRadius-circleWidth,
                centerX+outsideCircleRadius+circleWidth,centerY+outsideCircleRadius+circleWidth);
        RectF insideRectF = new RectF(centerX-insideCircleRadius+circleWidth,centerY-insideCircleRadius+circleWidth,
                centerX+insideCircleRadius-circleWidth,centerY+insideCircleRadius-circleWidth);
        int sc=canvas.saveLayer(outRectF,circlePaint);
        canvas.drawArc(insideRectF,startAngle,progress,true,circlePaint);
        circlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawArc(outRectF,startAngle,progress, true,circlePaint);
        circlePaint.setAlpha(0);
        canvas.drawCircle(centerX,centerY,insideCircleRadius-circleWidth,circlePaint);
        canvas.restoreToCount(sc);
    }

    /**
     * 绘制两端的小圆
     * @param canvas
     */
    private void drawSemicircle(Canvas canvas,int angle,boolean isClockwise){
        circlePaint.reset();
        if(type==1){
            circlePaint.setColor(sendColor);
        }else{
            circlePaint.setColor(receiveColor);
        }
        int sweepAngle= isClockwise? 180:-180;
        angle = isClockwise? angle-1:angle+1;
        double anglePi = Math.toRadians(angle);
        float radius = insideCircleRadius+(outsideCircleRadius-insideCircleRadius)/2;
        float circleRadius = radius-insideCircleRadius+circleWidth;
        float circleCenterY= (float) (Math.sin(anglePi)*radius+centerY);
        float circleCenterX = (float) (Math.cos(anglePi)*radius+centerX);
        Log.d(TAG,"circleCenterX:"+circleCenterX);
        Log.d(TAG,"centerX:"+centerX);
        RectF rectF = new RectF(circleCenterX-circleRadius,circleCenterY-circleRadius,
                circleCenterX+circleRadius,circleCenterY+circleRadius);
        canvas.drawArc(rectF,angle,sweepAngle,true,circlePaint);
    }



}
