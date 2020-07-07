package com.crazyorange.beauty.baselibrary.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * author  Guojinlong
 * email  v_guojinlong@baidu.com
 * Date   2018/11/28下午8:39
 * 获取内存信息
 */
public class MemoryUtils {
    private static String LOG_TAG = "MemoryUtils";
    public static final int MIN_AVAILABLE_SIZE = 10;

    public static long getTotalMemory() {
        String memInfoPath = "/proc/meminfo";
        String readTemp = "";
        String memTotal = "";
        long memory = 0;
        try {
            FileReader fr = new FileReader(memInfoPath);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((readTemp = localBufferedReader.readLine()) != null) {
                if (readTemp.contains("MemTotal")) {
                    String[] total = readTemp.split(":");
                    memTotal = total[1].trim();
                }
            }
            localBufferedReader.close();
            String[] memKb = memTotal.split(" ");
            memTotal = memKb[0].trim();
            memory = Long.parseLong(memTotal);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.getMessage());
        }
        return memory;
    }

    /**
     * get free memory.
     *
     * @return free memory of device
     */
    public static long getFreeMemorySize(Context context) {
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(outInfo);
        long avaliMem = outInfo.availMem;
        return avaliMem / 1024;
    }

    /**
     * get the memory of process with certain pid.
     *
     * @param pid     pid of process
     * @param context context of certain activity
     * @return memory usage of certain process
     */
    public static int getPidMemorySize(int pid, Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int[] myMempid = new int[]{pid};
        Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
        memoryInfo[0].getTotalSharedDirty();
        int memSize = memoryInfo[0].getTotalPss();
        return memSize;
    }

    /**
     * 获取所有空闲内存
     *
     * @return long 空闲内存
     */
    public static long getAvailableSize() {
        String sdcard = Environment.getExternalStorageState();
        String state = Environment.MEDIA_MOUNTED;

        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        if (sdcard.equals(state)) {
            //获得Sdcard上每个block的size
            long blockSize = statFs.getBlockSize();
            //获取可供程序使用的Block数量
            long blockavailable = statFs.getAvailableBlocks();
            //计算标准大小使用：1024，当然使用1000也可以
            long blockavailableTotal = blockSize * blockavailable / 1000 / 1000;
            return blockavailableTotal;
        } else {
            return -1;
        }
    }
}
