/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power, sample size calculations. 
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureFilter extends SimpleOnGestureListener{
    public static final int SWIPE_UP = 1;
    public static final int SWIPE_DOWN = 2;
    public static final int SWIPE_LEFT = 3;
    public static final int SWIPE_RIGHT = 4;
    
    public static final int MODE_TRANSPARENT = 0;
    public static final int MODE_SOLID = 1;
    public static final int MODE_DYNAMIC = 2;
    
    private static final int ACTION_FAKE = -13;
    private int swipe_min_distance = 100;
    private int swipe_max_distance = 350;
    private int swipe_min_velocity = 100;
    
    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;
    
    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;
    
    public GestureFilter(Activity activity, SimpleGestureListener sgl) {
      context = activity;
      detector = new GestureDetector(context, this);
      listener = sgl;
    }
    
    public void onTouchEvent(MotionEvent event) {        
        if(!this.running)
            return;  
            
             boolean result = this.detector.onTouchEvent(event); 
            
             if(this.mode == MODE_SOLID)
              event.setAction(MotionEvent.ACTION_CANCEL);
             else if (this.mode == MODE_DYNAMIC) {
            
               if(event.getAction() == ACTION_FAKE) 
                 event.setAction(MotionEvent.ACTION_UP);
               else if (result)
                 event.setAction(MotionEvent.ACTION_CANCEL); 
               else if(this.tapIndicator){
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
               }
            
             }
             //else just do nothing, it's Transparent
    }
    
    public void setMode(int m) {
      mode = m;
    }
    
    public int getMode() {
      return mode;
    }
    
    public void setEnabled(boolean status) {
      running = status;
    }
    
    public void setSwipeMaxDistance(int distance) {
      swipe_max_distance = distance;
    }
    
    public void setSwipeMinDistance(int distance) {
      swipe_min_distance = distance;
    }
    
    public void setSwipeMinVelocity(int distance) {
      swipe_min_velocity = distance;
    }
    
    public int getSwipeMaxDistance() {
      return swipe_max_distance;
    }
    
    public int getSwipeMinDistance() {
      return swipe_min_distance;
    }
    
    public int getSwipeMinVelocity() {
      return swipe_min_velocity;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, 
        float velocityX, float velocityY) {
        //System.out.println("onFling");
      final float xDistance = Math.abs(e1.getX() - e2.getX());
      final float yDistance = Math.abs(e1.getY() - e2.getY());
      
      if (xDistance > swipe_max_distance || yDistance > swipe_max_distance){
          System.out.println("returning false");
        return false;
      }
      
      velocityX = Math.abs(velocityX);
      velocityY = Math.abs(velocityY);
      boolean result = false;
      
      if (velocityX > swipe_min_velocity && xDistance > swipe_min_distance) {
        if (e1.getX() > e2.getX())
          listener.onSwipe(SWIPE_LEFT);
        else
          listener.onSwipe(SWIPE_RIGHT);
        
        result = true;
      }
      
      return result;
    }
    
    
    public boolean onSingleTap(MotionEvent e) {
      tapIndicator = true;
      return false;
    }
    
    @Override
    public boolean onDoubleTap(MotionEvent e) {
      listener.onDoubleTap();
      return true;
    }
    
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
      return true;
    }
    
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      if (mode == MODE_DYNAMIC) {
        e.setAction(ACTION_FAKE);
        context.dispatchTouchEvent(e);
      }
      return false;
    }
    
    public static interface SimpleGestureListener {
      void onSwipe(int direction);
      void onDoubleTap();
    }
}
