package com.example.picturebrowser.View;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;

import com.example.picturebrowser.Adapter.MainListAdapter;
import com.example.picturebrowser.Application.MyApplication;
import com.example.picturebrowser.MyClass.Photo;
import com.example.picturebrowser.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 铖哥 on 2017/5/23.
 */

public class MyRecyclerView extends RecyclerView implements MainListAdapter.OnItemLongClickListener {

    MainListAdapter adapter;
    GridLayoutManager manager;
    int clickingViewPos;
    View clickingView;
    List<View> viewHolder;
    int MODE_NORMAL = 0;
    int MODE_MODIFY = 1;
    int MODIFY_CHECK = 2;
    int MODIFY_UNCHECK = 3;
    int DRAG_X = 4;
    int DRAG_Y = 5;
    int dragMode = DRAG_Y;
    int mode = MODE_NORMAL;
    int modifyMode = MODIFY_CHECK;
    static final float FLIP_DISTANCE = 50;
    boolean isScrolled = false;
    List<Photo> photos;


    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setAdapter(MainListAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = ((MainListAdapter) getAdapter());
        this.adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (layout instanceof GridLayoutManager) {
            manager = (GridLayoutManager) layout;
        }

    }

    @Override
    public void onItemLongClick(View v, int postion) {
        if (mode != MODE_MODIFY) {
            photos = ((MainListAdapter)getAdapter()).getResource();
            clickingViewPos = postion;
            clickingView = v;
            List<View> views = getVisibileItems();
            for (int i = 0; i < views.size(); i++) {
                startAnim(v, views.get(i), i + 5);
                CheckBox checkBox = (CheckBox) views.get(i).findViewById(R.id.checkBox);
                checkBox.setVisibility(VISIBLE);
            }
            ((CheckBox) v.findViewById(R.id.checkBox)).setChecked(true);
            photos.get(postion).setChecked(true);
            mode = MODE_MODIFY;
        }
    }


    Point start;
    VelocityTracker velocityTracker = VelocityTracker.obtain();

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {


        velocityTracker.addMovement(e);
        velocityTracker.computeCurrentVelocity(100);


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = new Point((int) e.getX(), (int) e.getY());
                if (mode == MODE_MODIFY) {
                    View v = getView(start, viewHolder);
                    if (v != null) {
                        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                        modifyMode = checkBox.isChecked() ? MODIFY_UNCHECK : MODIFY_CHECK;
                    }
                }
                Log.e("sdasd", "asd控件改变愧古高合金钢吉哥还吉哥控件华工科技华工科技换个卡讲话稿讲话稿控件华工科技华工科技华工科技好工具考核过可狡猾asd");
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScrolled) {
                    if (Math.abs(e.getX() - start.x) > FLIP_DISTANCE) {
                        dragMode = DRAG_X;
                    }
                }
//                if ( ((e.getY() - start.y) / (e.getX() - start.x) > Math.sqrt(3) / 3 || (e.getY() - start.y) / (e.getX() - start.x) < -Math.sqrt(3) / 3)) {
//                }else{
//
//                }

//                if(velocityTracker.getXVelocity() > velocityTracker.getYVelocity()){
//                    dragMode = DRAG_X;
//                }

                Log.e("mode", dragMode + "");
                if (mode == MODE_MODIFY) {

                    if (dragMode == DRAG_X) {

                        View v = getView(new Point((int) e.getX(), (int) e.getY()), viewHolder);
                        if (v != null) {
                            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                            checkBox.setChecked(modifyMode == MODIFY_CHECK);
                            photos.get(manager.getPosition(v)).setChecked(checkBox.isChecked());
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                dragMode = DRAG_Y;
                start = null;
                break;

        }

        if (dragMode == DRAG_Y) {
            return super.dispatchTouchEvent(e);
        } else {
            return true;
        }

    }


    private Point getViewLayoutCenter(View v) {
        Point center = new Point();
        center.set((int) (v.getX() + v.getWidth() / 2), (int) (v.getY() + v.getHeight() / 2));
        return center;
    }


    private void startAnim(View target, View origin, int delta) {
        Point originC = getViewLayoutCenter(origin);
        Point targetC = getViewLayoutCenter(target);
        TranslateAnimation animator;

//        ObjectAnimator animator = ObjectAnimator.ofFloat(origin,TRANSLATION_X,TRANSLATION_Y,path);
        if (targetC.x - originC.x != 0 && targetC.y - originC.y != 0) {
            animator = new TranslateAnimation(0, targetC.x - originC.x > 0 ? delta : -delta, 0, targetC.y - originC.y > 0 ? delta : -delta);
        } else if (targetC.x - originC.x == 0 && targetC.y - originC.y != 0) {
            animator = new TranslateAnimation(0, 0, 0, targetC.y - originC.y > 0 ? delta : -delta);
        } else if (targetC.x - originC.x != 0 && targetC.y - originC.y == 0) {
            animator = new TranslateAnimation(0, targetC.x - originC.x > 0 ? delta : -delta, 0, 0);
        } else {
            animator = new TranslateAnimation(0, 0, 0, 0);
        }

        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(100);
        origin.startAnimation(animator);
    }

    private List<View> getVisibileItems() {
        int top = manager.findFirstVisibleItemPosition();
        int bottom = manager.findLastVisibleItemPosition();
        Log.e("top,bpttom,pos", top + " " + " " + bottom + " " + clickingViewPos);
        List<View> views = new ArrayList<>();
        for (int i = 0; i < manager.getChildCount(); i++) {
            //if(i != clickingViewPos)
            views.add(getChildAt(i));
        }

        Collections.sort(views, new Comparator<View>() {
            @Override
            public int compare(View o1, View o2) {
                Point p1 = getViewLayoutCenter(o1);
                Point p2 = getViewLayoutCenter(o2);

                return -(distanceSquare(getViewLayoutCenter(clickingView), p1) - distanceSquare(getViewLayoutCenter(clickingView), p2));
            }
        });
        viewHolder = views;
        return views;
    }

    private int distanceSquare(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    private View getView(Point p, List<View> views) {

        for (View view : views) {
            if (view.getLeft() < p.x && view.getRight() > p.x && view.getTop() < p.y && view.getBottom() > p.y) {
                return view;
            }
        }
        return null;
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == 1) {
            isScrolled = true;
        }
        if (state == 0) {
            isScrolled = false;
        }
        Log.e("asdasda", "   " + state);
        super.onScrollStateChanged(state);
    }
}

