package com.crazyorange.beauty.baselibrary.file;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * author  v_zhouqiang
 * email   v_zhouqiang@baidu.com
 * Time    2019/1/31 上午10:28
 */
public class FileUtils {
    /**
     * @param file 需要创建父路径的文件及文件夹
     */
    public static void mkParentFileForFile(String file) {
        File nextVideoFile = new File(file);

        if (!nextVideoFile.getParentFile().exists()) {
            nextVideoFile.getParentFile().mkdirs();
        } else if (!nextVideoFile.getParentFile().isDirectory()) {
            nextVideoFile.getParentFile().delete();
            nextVideoFile.getParentFile().mkdirs();
        }
    }

    public static void copy_file_from_asset(Context context, String oldPath, String newPath) {
        try {
            String[] fileNames = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                // directory
                File file = new File(newPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // copy recursivelyC
                for (String fileName : fileNames) {
                    copy_file_from_asset(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                // file
                File file = new File(newPath);
                // if file exists will never copy
                if (file.exists()) {
                    return;
                }

                // copy file to new path
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sortFile(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o2.lastModified() - o1.lastModified());
            }
        });
    }
}
