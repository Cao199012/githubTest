package com.example.caojian.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by caojian on 2016/11/9.
 */
public class SimpleImageView extends ImageView{
    private Paint mPaint; //画笔
    private Drawable mDrawable; //图片
    private int mWith,mHeight;
    public SimpleImageView(Context context) {
        super(context,null);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if(attrs != null){
            TypedArray typedArray = null;
            typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.SimpleImageView);
            //根据id获取图片
            mDrawable = typedArray.getDrawable(R.styleable.SimpleImageView_src);
            if(mDrawable != null){
                mWith = mDrawable.getIntrinsicWidth();
                mHeight = mDrawable.getIntrinsicHeight();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWith,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(((BitmapDrawable)mDrawable).getBitmap(),getLeft(),getTop(),mPaint);
    }
}
