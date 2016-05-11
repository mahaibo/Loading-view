package com.example.mahaibo.loadinganim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mahaibo on 16/5/9.
 */
public class LoadingView extends View {
    private static final int COLOR_OK=0x41be5f;
    private static final int COLOR_GRAY=0xb2b2b2;
    private static final int COLOR_DEFAULT=0xf4f4f4;
    private static final int REDIUS=80;

    Paint mCirclePaint=new Paint();
    private int circleRadius=50;
    private int center;
    private int center1;
    private RectF rectF;

    private int leftStartX;
    private int leftStartY;

    private int rightStartX;
    private int rightStartY;

    private int mProgress=0;

    public LoadingView(Context context){
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public LoadingView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = circleRadius + this.getPaddingLeft() + this.getPaddingRight();
        int viewHeight = circleRadius + this.getPaddingTop() + this.getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(viewWidth, widthSize);
        } else {
            //Be whatever you want
            width = viewWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(viewHeight, heightSize);
        } else {
            //Be whatever you want
            height = viewHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPaint();
        invalidate();
    }

    private void setPaint(){

        mCirclePaint.setColor(getResources().getColor(R.color.Ok));
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        center=getWidth()/2;
        center1=center-circleRadius+8;
        rectF=new RectF(center-circleRadius-1,center-circleRadius-1,center+circleRadius+1,center+circleRadius+1);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mProgress++;

        canvas.drawArc(rectF,-90,-360*mProgress/100,false,mCirclePaint);


        //画钩
        if (leftStartX<circleRadius/3){
            leftStartX++;
            leftStartY++;
        }
        canvas.drawLine(center1,center,center1+leftStartX,center+leftStartY,mCirclePaint);

        if (leftStartX==circleRadius/3){
            rightStartX=leftStartX;
            rightStartY=leftStartX;
            leftStartX++;
            leftStartY++;

        }

        if (leftStartX>=circleRadius/3 && rightStartX<=circleRadius){
            rightStartX++;
            rightStartY--;
        }
        canvas.drawLine(center1+leftStartX-1,center+leftStartY,center1+rightStartX,
                center+rightStartY,mCirclePaint);
        postInvalidateDelayed(10);
    }
}
