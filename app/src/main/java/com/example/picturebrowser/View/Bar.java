package com.example.picturebrowser.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 铖哥 on 2017/5/21.
 */

public class Bar extends View {

    Path path = new Path();
    Paint paint = new Paint();
    ValueAnimator anim;
    float offsetX, offsetY;
    float posY;
    int width;
    int height;
    float progress;
    boolean isRunning = false;

    public Bar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width == 0) {
            width = getWidth();
            height = getHeight();
        }

        offsetY = height * progress;
        posY = height - offsetY;


        path.reset();
        path.moveTo(-offsetX, posY);

        path.cubicTo(-offsetX, posY,
                (width - 2 * offsetX) / 2, posY + 100,
                width - offsetX, posY);

        path.cubicTo(width - offsetX, posY,
                ((width * 3) - 2 * offsetX) / 2, posY - 100,
                width * 2 - offsetX, posY);

        path.cubicTo(width * 2 - offsetX, posY,
                (width * 5 - 2 * offsetX) / 2, posY + 100,
                width * 3 - offsetX, posY);



        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, posY);

//        int save = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

//        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(path, paint);
//
//        canvas.restoreToCount(save);
//        paint.setXfermode(null);


    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {


        anim = ValueAnimator.ofInt(0, width * 2);
        anim.setDuration(500);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });


        anim.start();
    }


}
