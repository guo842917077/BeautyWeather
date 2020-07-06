package com.crazyorange.beauty.baselibrary.file;


import com.crazyorange.beauty.baselibrary.log.ALog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final String TAG = "ZipUtil";

    /**
     * 打zip包
     *
     * @param files
     * @param dest
     */
    public static File zip(List<File> files, File dest) {
        // 提供了一个数据项压缩成一个ZIP归档输出流
        ZipOutputStream out = null;
        try {
            if (dest.exists()) {
                dest.delete();
            }

            out = new ZipOutputStream(new FileOutputStream(dest));
            // 如果此文件是一个文件，否则为false。
            for (File f : files) {
                zipFileOrDirectory(out, f, "");
            }

            // 停止上报
            out.close();

            return dest;
        } catch (IOException ex) {
            if (ALog.DEBUG) {
                ALog.d(TAG, "zip file error : " + ex.getMessage());
            }
            return null;
        } finally {
            // 关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    if (ALog.DEBUG) {
                        ALog.d(TAG, "zip file error : " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param out
     * @param fileOrDirectory
     * @param curPath
     *
     * @throws IOException
     */
    private static void zipFileOrDirectory(ZipOutputStream out,
                                           File fileOrDirectory, String curPath) throws IOException {
        // 从文件中读取字节的输入流
        FileInputStream in = null;
        try {
            // 如果此文件是一个目录，否则返回false。
            if (!fileOrDirectory.isDirectory()) {
                // 压缩文件
                byte[] buffer = new byte[4096];
                int bytes_read;
                in = new FileInputStream(fileOrDirectory);
                // 实例代表一个条目内的ZIP归档
                ZipEntry entry = new ZipEntry(curPath
                        + fileOrDirectory.getName());
                // 条目的信息写入底层流
                out.putNextEntry(entry);

                while ((bytes_read = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytes_read);
                }

                out.flush();
                out.closeEntry();
            } else {
                // 压缩目录
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; i++) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entries[i], curPath
                            + fileOrDirectory.getName() + "/");
                }
            }
        } catch (IOException ex) {
            if (ALog.DEBUG) {
                ALog.d(TAG, "zip directory error : " + ex.getMessage());
            }
            // throw ex;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    if (ALog.DEBUG) {
                        ALog.d(TAG, "close file error : " + ex.getMessage());
                    }
                }
            }
        }
    }
}
