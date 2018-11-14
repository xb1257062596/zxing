package backup.wingos.com.wingbackup.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.journeyapps.barcodescanner.ViewfinderView;

import backup.wingos.com.wingbackup.R;

/**
 * Created by xiongbo on 2018/11/14.
 */

public class CustomViewfinderView extends ViewfinderView {

    private static  final String TAG = "CustomViewfinderView";

    /**
     * 画相机四个角落的值
     */
    private Paint customPaint;
    /**
     * 获取相机的区域
     */
    private Rect frame;
    /**
     * 修改这个值 可以修改角落绘制的颜色
     */
    private int color;
    /**
     * 四个角落的宽度
     */
    private int cornerWidth;
    /**
     * 四个角落的厚度
     */
    private int cornerHeight;
    /**
     * 你需要在相机下面显示的文字
     */
    private String text;
    /**
     * 中间横线竖直的位置
     */
    private int direction;
    /**
     * 代表了第一次绘制
     */
    private boolean isFirst;
    /**
     * 是否获取二维码的结果
     */
    private boolean isResult;
    /**
     * 是否退出线程
     */
    private boolean isExit;

    private Thread thread;

    public CustomViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customPaint = new Paint();
        customPaint.setStyle(Paint.Style.FILL);
        color=getResources().getColor(R.color.colorPrimary);
        customPaint.setAntiAlias(true);
        cornerWidth=50;
        cornerHeight=8;
        text="Scan the QR code from new phone";
        isFirst = true;
        isResult=false;
        isExit=false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isExit){
                    setDirection(direction+2);
                    try {
                        thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }
        frame = framingRect;
        if(isFirst){
            isFirst=false;
            direction=frame.top+10;
        }
        customPaint.setColor(color);
        customPaint.setStrokeWidth((float) 1.5);
        /**
         * 绘制四条边框
         */
        canvas.drawLine(frame.left,frame.top,frame.right,frame.top,customPaint);
        canvas.drawLine(frame.left,frame.top,frame.left,frame.bottom,customPaint);
        canvas.drawLine(frame.right,frame.top,frame.right,frame.bottom,customPaint);
        canvas.drawLine(frame.right,frame.bottom,frame.left,frame.bottom,customPaint);

        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
            isResult=true;
        } else {
            customPaint.setAlpha(100);
            Rect rect = new Rect(frame.left,frame.top,frame.right, direction);
            canvas.drawRect(rect,customPaint);
            customPaint.setAlpha(255);
            customPaint.setStrokeWidth(3);
            canvas.drawLine(frame.left,direction,frame.right,direction,customPaint);
            if(direction>=frame.bottom){
                direction=frame.top;
            }
        }
        if(isResult){
            isExit=true;
        }else{
            isExit=false;
        }


        drawCorner(1,canvas);
        drawCorner(2,canvas);
        drawCorner(3,canvas);
        drawCorner(4,canvas);
        drawText(text,canvas);
    }

    public void setColor(int color){
        this.color=color;
        invalidate();
    }

    /**
     * 绘制四个角落 type: 1--左上 2--右上 3--左下 4--右下
     */
    private void drawCorner(int type,Canvas canvas){
        Rect verticalRect=null;
        Rect horizontalRect =null;
        if(type==1){
            horizontalRect = new Rect(frame.left,frame.top,frame.left+cornerWidth,frame.top+cornerHeight);
            verticalRect = new Rect(frame.left,frame.top,frame.left+cornerHeight,frame.top+cornerWidth);
        }else if(type==2){
            horizontalRect = new Rect(frame.right-cornerWidth,frame.top,frame.right,frame.top+cornerHeight);
            verticalRect = new Rect(frame.right-cornerHeight,frame.top,frame.right,frame.top+cornerWidth);
        }else if(type==3){
            horizontalRect = new Rect(frame.left,frame.bottom-cornerWidth,frame.left+cornerHeight,frame.bottom);
            verticalRect = new Rect(frame.left,frame.bottom-cornerHeight,frame.left+cornerWidth,frame.bottom);
        }else{
            horizontalRect = new Rect(frame.right-cornerWidth,frame.bottom-cornerHeight,frame.right,frame.bottom);
            verticalRect = new Rect(frame.right-cornerHeight,frame.bottom-cornerWidth,frame.right,frame.bottom);
        }
        canvas.drawRect(horizontalRect,customPaint);
        canvas.drawRect(verticalRect,customPaint);
    }

    public void setText(String text){
        this.text=text;
    }

    private void drawText(String text,Canvas canvas){
        customPaint.setColor(getResources().getColor(R.color.colorWhite));
        customPaint.setTextSize(50);
        canvas.drawText(text,frame.left-30,frame.bottom+100,customPaint);
    }

    private void setDirection(int direction){
        this.direction=direction;
        invalidate();
    }

    public void stopThread(){
        isExit=true;
        if(thread!=null){
            thread=null;
        }
    }

}
