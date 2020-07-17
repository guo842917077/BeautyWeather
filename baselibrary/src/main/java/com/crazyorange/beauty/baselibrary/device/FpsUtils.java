package com.crazyorange.beauty.baselibrary.device;

import android.view.Choreographer;

import com.crazyorange.beauty.baselibrary.log.ALog;

import java.util.ArrayList;

public class FpsUtils {
    private long startTime;
    private int count = 0;

    private static final long INTERVAL = 160 * 1000 * 1000;

    public void getFps() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (startTime == 0) {
                    startTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - startTime;
                if (interval > INTERVAL) {
                    double fps = (((double) (count * 1000 * 1000)) / interval) * 1000L;
                    ALog.d("FpsUtils", "fps = " + fps);
                    startTime = 0;
                    count = 0;
                } else {
                    count++;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

}
