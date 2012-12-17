/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power and sample size calculations. 
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

// TODO: Auto-generated Javadoc
/**
 * The Class GestureFilter.
 * 
 * @version 1.0.0
 */
public class GestureFilter extends SimpleOnGestureListener {

    /** The Constant SWIPE_UP. */
    public static final int SWIPE_UP = 1;

    /** The Constant SWIPE_DOWN. */
    public static final int SWIPE_DOWN = 2;

    /** The Constant SWIPE_LEFT. */
    public static final int SWIPE_LEFT = 3;

    /** The Constant SWIPE_RIGHT. */
    public static final int SWIPE_RIGHT = 4;

    /** The Constant MODE_TRANSPARENT. */
    public static final int MODE_TRANSPARENT = 0;

    /** The Constant MODE_SOLID. */
    public static final int MODE_SOLID = 1;

    /** The Constant MODE_DYNAMIC. */
    public static final int MODE_DYNAMIC = 2;

    /** The Constant ACTION_FAKE. */
    private static final int ACTION_FAKE = -13;

    /** The swipe_min_distance. */
    private int swipe_min_distance = 100;

    /** The swipe_max_distance. */
    private int swipe_max_distance = 350;

    /** The swipe_min_velocity. */
    private int swipe_min_velocity = 100;

    /** The mode. */
    private int mode = MODE_DYNAMIC;

    /** The running. */
    private boolean running = true;

    /** The tap indicator. */
    private boolean tapIndicator = false;

    /** The context. */
    private Activity context;

    /** The detector. */
    private GestureDetector detector;

    /** The listener. */
    private SimpleGestureListener listener;

    /**
     * Instantiates a new gesture filter.
     * 
     * @param activity
     *            the activity
     * @param sgl
     *            the sgl
     */
    public GestureFilter(Activity activity, SimpleGestureListener sgl) {
        context = activity;
        detector = new GestureDetector(context, this);
        listener = sgl;
    }

    /**
     * On touch event.
     * 
     * @param event
     *            the event
     */
    public void onTouchEvent(MotionEvent event) {
        if (!this.running)
            return;

        boolean result = this.detector.onTouchEvent(event);

        if (this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {

            if (event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }

        }
    }

    /**
     * Sets the mode.
     * 
     * @param m
     *            the new mode
     */
    public void setMode(int m) {
        mode = m;
    }

    /**
     * Gets the mode.
     * 
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Sets the enabled.
     * 
     * @param status
     *            the new enabled
     */
    public void setEnabled(boolean status) {
        running = status;
    }

    /**
     * Sets the swipe max distance.
     * 
     * @param distance
     *            the new swipe max distance
     */
    public void setSwipeMaxDistance(int distance) {
        swipe_max_distance = distance;
    }

    /**
     * Sets the swipe min distance.
     * 
     * @param distance
     *            the new swipe min distance
     */
    public void setSwipeMinDistance(int distance) {
        swipe_min_distance = distance;
    }

    /**
     * Sets the swipe min velocity.
     * 
     * @param distance
     *            the new swipe min velocity
     */
    public void setSwipeMinVelocity(int distance) {
        swipe_min_velocity = distance;
    }

    /**
     * Gets the swipe max distance.
     * 
     * @return the swipe max distance
     */
    public int getSwipeMaxDistance() {
        return swipe_max_distance;
    }

    /**
     * Gets the swipe min distance.
     * 
     * @return the swipe min distance
     */
    public int getSwipeMinDistance() {
        return swipe_min_distance;
    }

    /**
     * Gets the swipe min velocity.
     * 
     * @return the swipe min velocity
     */
    public int getSwipeMinVelocity() {
        return swipe_min_velocity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.view.GestureDetector.SimpleOnGestureListener#onFling(android.
     * view.MotionEvent, android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        if (xDistance > swipe_max_distance || yDistance > swipe_max_distance) {            
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

    /**
     * On single tap.
     * 
     * @param e
     *            the e
     * @return true, if successful
     */
    public boolean onSingleTap(MotionEvent e) {
        tapIndicator = true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android
     * .view.MotionEvent)
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        listener.onDoubleTap();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.view.GestureDetector.SimpleOnGestureListener#onDoubleTapEvent
     * (android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.view.GestureDetector.SimpleOnGestureListener#onSingleTapConfirmed
     * (android.view.MotionEvent)
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (mode == MODE_DYNAMIC) {
            e.setAction(ACTION_FAKE);
            context.dispatchTouchEvent(e);
        }
        return false;
    }

    /**
     * The listener interface for receiving simpleGesture events. The class that
     * is interested in processing a simpleGesture event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's
     * <code>addSimpleGestureListener<code> method. When
     * the simpleGesture event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see SimpleGestureEvent
     */
    public static interface SimpleGestureListener {

        /**
         * On swipe.
         * 
         * @param direction
         *            the direction
         */
        void onSwipe(int direction);

        /**
         * On double tap.
         */
        void onDoubleTap();
    }
}
