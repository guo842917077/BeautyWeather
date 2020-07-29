package com.crazyorange.beauty.baselibrary.net;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.crazyorange.beauty.baselibrary.device.DeviceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author guojinlong
 * <p>
 * 使用 TrafficState 进行流量统计
 */
public class NetWorkUtils {
    /**
     * 获取系统启动时 所消耗的流量
     * Traffic 跟手机相关，不是所有设备都适用
     */
    public static long targetDevice(int uid) {
        return TrafficStats.getUidRxBytes(uid);
    }

    /**
     * 获取系统的流量
     *
     * @param uid 设备 ID
     * @return
     */
    public static long[] deviceFlow(int uid) {
        String line, line2;
        long[] stats = new long[2];
        try {
            File fileSnd = new File("/proc/uid_stat/" + uid + "/tcp_snd");
            File fileRcv = new File("/proc/uid_stat/" + uid + "/tcp_rcv");
            BufferedReader br1 = new BufferedReader(new FileReader(fileSnd));
            BufferedReader br2 = new BufferedReader(new FileReader(fileRcv));
            while ((line = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
                stats[0] = Long.parseLong(line);
                stats[1] = Long.parseLong(line2);
            }
            br1.close();
            br2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * 获取指令时间段指定网络类型的流量
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static long[] getAppointTimeFlow(Context context, long startTime, long endTime, int netWorkType) {
        long[] bucket = new long[2];
        long rx = 0;
        long tx = 0;
        NetworkStatsManager statsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        try {
            NetworkStats networkStats = statsManager.querySummary(netWorkType, DeviceUtils.getSubscriberId(context),
                    startTime, endTime);
            if (networkStats != null) {
                NetworkStats.Bucket bucketOut = new NetworkStats.Bucket();
                int uid = DeviceUtils.getUid(context);
                while (networkStats.hasNextBucket()) {
                    networkStats.getNextBucket(bucketOut);
                    if (uid == bucketOut.getUid()) {
                        rx += bucketOut.getRxBytes();
                        tx += bucketOut.getTxBytes();
                    }
                }
                bucket[0] = rx;
                bucket[1] = tx;
                return bucket;
            }

        } catch (RemoteException e) {
            Log.d("TAG", e.getMessage());
        }
        return null;
    }
}
