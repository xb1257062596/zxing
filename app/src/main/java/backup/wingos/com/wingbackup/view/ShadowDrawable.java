package backup.wingos.com.wingbackup.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;

public class ShadowDrawable extends Drawable {
    //阴影画笔
	private Paint mShadowPaint;
	//背景画笔
	private int mOffsetX;
	private int mOffsetY;
	private RectF mRect;

	private int startAngle;
	private int progress;
	private float insideCircleRadius;
	private float outsideCircleRadius;
	private float circleWidth;


	public ShadowDrawable(int shadowColor, int shadowRadius, int offsetX, int offsetY,int startAngle,
						  int progress,float insideCircleRadius,float outsideCircleRadius,float circleWidth) {
		this.mOffsetX = offsetX;
		this.mOffsetY = offsetY;
		this.startAngle=startAngle;
		this.progress = progress;
		this.insideCircleRadius=insideCircleRadius;
		this.outsideCircleRadius=outsideCircleRadius;
		this.circleWidth=circleWidth;
		mShadowPaint = new Paint();
		mShadowPaint.setColor(Color.TRANSPARENT);
		mShadowPaint.setAntiAlias(true);
		mShadowPaint.setShadowLayer(shadowRadius, offsetX, offsetY, shadowColor);
		mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
	}

	public void setBounds(float left, float top, float right, float bottom) {
		super.setBounds((int) left, (int)top, (int)right, (int) bottom);
		mRect = new RectF(left  - mOffsetX, top  - mOffsetY, right  - mOffsetX,
				bottom - mOffsetY);
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
		canvas.save();
		canvas.translate(mRect.centerX(),mRect.centerY());
		canvas.rotate(-90);
		canvas.translate(-mRect.centerX(),-mRect.centerY());
		canvas.drawArc(mRect,startAngle,progress, false, mShadowPaint);
		drawSemicircle(canvas,startAngle,false);
		drawSemicircle(canvas,startAngle+progress,true);
		mShadowPaint.reset();
		mShadowPaint.setColor(Color.TRANSPARENT);
		mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
		canvas.drawCircle(mRect.centerX(),mRect.centerY(),insideCircleRadius-20,mShadowPaint);
		canvas.restore();
	}

	private void drawSemicircle(Canvas canvas, int startAngle, boolean isClockwise) {
		int sweepAngle= isClockwise? 180:-180;
		startAngle = isClockwise? startAngle-1:startAngle+1;
		double anglePi = Math.toRadians(startAngle);
		float radius = insideCircleRadius+(outsideCircleRadius-insideCircleRadius)/2;
		float circleRadius = radius-insideCircleRadius+circleWidth;
		float circleCenterY= (float) (Math.sin(anglePi)*radius+mRect.centerX());
		float circleCenterX = (float) (Math.cos(anglePi)*radius+mRect.centerY());
		RectF rectF = new RectF(circleCenterX-circleRadius,circleCenterY-circleRadius,
				circleCenterX+circleRadius,circleCenterY+circleRadius);
		canvas.drawArc(rectF,startAngle,sweepAngle,true,mShadowPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mShadowPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
		mShadowPaint.setColorFilter(colorFilter);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	public static void setShadowDrawable(View view, Drawable drawable) {
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		ViewCompat.setBackground(view, drawable);
	}
}