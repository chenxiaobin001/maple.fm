package com.example.maplefreemarket;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.interfaces.SwipeInterface;

public class ActivitySwipeDetector implements View.OnTouchListener {

    static final String logTag = "ActivitySwipeDetector";
    private SwipeInterface activity;
    static final int MIN_DISTANCE = 200;
    static final int MIN_DISTANCE_VERTICAL = 200;
    private float downX, downY, upX, upY;
    public int action = 0;
    
    
    public ActivitySwipeDetector(SwipeInterface activity){
        this.activity = activity;
    }

    public void onRightToLeftSwipe(View v){
        Log.i(logTag, "RightToLeftSwipe!");
        activity.right2left(v);
    }

    public void onLeftToRightSwipe(View v){
        Log.i(logTag, "LeftToRightSwipe!");
        activity.left2right(v);
    }

    
    @Override
    public boolean onTouch(View v, MotionEvent event) { 	
    	
        switch(event.getAction()){
        case MotionEvent.ACTION_DOWN: {
            // Add a user's movement to the tracker. 
            downX = event.getX();
            downY = event.getY();
            action = 0;
  //          v.getParent().requestDisallowInterceptTouchEvent(true);
            //do not return true here??? why???
            //True if the listener has consumed the event, false otherwise.
            //need to let onscrolllistener to get this event!
        }
        case MotionEvent.ACTION_UP: {
	
            upX = event.getX();
            upY = event.getY();
  //          v.getParent().requestDisallowInterceptTouchEvent(false);
            float deltaX = downX - upX;
            float deltaY = downY - upY;
            // swipe vertical?
            if (Math.abs(deltaY) > MIN_DISTANCE_VERTICAL){
            	return false;
            }
            
            // swipe horizontal?
            if(Math.abs(deltaX) > MIN_DISTANCE){
                // left or right
                if(deltaX < 0) { this.onLeftToRightSwipe(v); action = 1; ;}
                if(deltaX > 0) { this.onRightToLeftSwipe(v); action = 2; ;}
            }
        }
        }
        return false;
    }

}