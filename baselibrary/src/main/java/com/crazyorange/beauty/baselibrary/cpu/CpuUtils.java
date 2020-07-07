package com.crazyorange.beauty.baselibrary.cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * author  Guojinlong
 * email  v_guojinlong@baidu.com
 * Date   2018/11/28下午7:45
 * cpu 工具集
 */
public class CpuUtils {
    private static final String TAG = "CpuUtils";
    private static final String CPU_INFO_PATH = "/proc/cpuinfo";
    private static final String INTEL_CPU_NAME = "flower_model name";

    public static int getNumCpuCores() {
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    // Check if filename is "cpu", followed by a single digit number
                    if (Pattern.matches("cpu[0-9]+", file.getName())) {
                        return true;
                    }
                    return false;
                }
            });
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * cpu的最大频率
     *
     * @return 单位KHZ
     */
    public static long getCpuMaxFreq() {
        long result = 0L;
        try {
            String line;
            BufferedReader br = new BufferedReader(
                    new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"));
            if ((line = br.readLine()) != null) {
                result = Long.parseLong(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 当前cpu的频率
     *
     * @return cpu频率信息 单位KHZ
     */
    public static List<Integer> getCpuCurFreq() {
        List<Integer> results = new ArrayList<Integer>();
        String freq = "";
        FileReader fr = null;
        try {
            int cpuIndex = 0;
            Integer lastFreq = 0;
            while (true) {
                File file = new File("/sys/devices/system/cpu/cpu" + cpuIndex + "/");
                if (!file.exists()) {
                    break;
                }
                file = new File("/sys/devices/system/cpu/cpu" + cpuIndex + "/cpufreq/");
                if (!file.exists()) {
                    lastFreq = 0;
                    results.add(0);
                    cpuIndex++;
                    continue;
                }
                file = new File("/sys/devices/system/cpu/cpu" + cpuIndex
                        + "/cpufreq/scaling_cur_freq");
                if (!file.exists()) {
                    results.add(lastFreq);
                    cpuIndex++;
                    continue;
                }
                fr = new FileReader(
                        "/sys/devices/system/cpu/cpu" + cpuIndex
                                + "/cpufreq/scaling_cur_freq");
                BufferedReader br = new BufferedReader(fr);
                String text = br.readLine();
                freq = text.trim();
                lastFreq = Integer.valueOf(freq);
                results.add(lastFreq);
                fr.close();
                cpuIndex++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    public static String getCpuName() {
        try {
            RandomAccessFile cpuStat = new RandomAccessFile(CPU_INFO_PATH, "r");
            // check cpu type
            String line;
            while (null != (line = cpuStat.readLine())) {
                String[] values = line.split(":");
                if (values[0].contains(INTEL_CPU_NAME) || values[0].contains("Processor")) {
                    cpuStat.close();
                    return values[1];
                }
            }
        } catch (IOException e) {
            return "";
        }
        return "";
    }

    /**
     * 获取当前cpu占用时间
     *
     * @return
     */
    public static long getTotalCpuTime() {
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            return 0;
        }
        long totalCpu = 0;
        try {
            totalCpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        return totalCpu;
    }

    /**
     * 获取当前cpu的使用时间
     *
     * @return
     */
    public static long getAppCpuTime() {
        String[] cpuInfos = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException e) {
            return 0;
        }
        long appCpuTime = 0;
        try {
            appCpuTime = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        return appCpuTime;
    }

    /**
     * 获取cpu的使用率
     *
     * @return
     */
    public static float getProcessCpuRate() {
        //        long totalCpuTime1 = getTotalCpuTime();
        //        long processCpuTime1 = getAppCpuTime();
        //        try {
        //            Thread.sleep(1000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        long totalCupTime2 = getTotalCpuTime();
        //        long processCpuTime2 = getAppCpuTime();
        //        float mCurrentCpuRate = 0;
        //        if (totalCpuTime1 != totalCupTime2) {
        //            float rate = (float) (100 * (processCpuTime2 - processCpuTime1) / (totalCupTime2
        //                                                                                       - totalCpuTime1));
        //            if (rate >= 0.0f || rate <= 100.0f) {
        //                mCurrentCpuRate = rate;
        //            }
        //        }
        //        return mCurrentCpuRate;
        //    }
        return 10;
    }
}
