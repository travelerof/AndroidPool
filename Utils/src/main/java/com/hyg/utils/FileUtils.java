package com.hyg.utils;

import androidx.annotation.NonNull;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/02/22
 * @Desc
 */
public class FileUtils {


    /**
     * 根据路径创建文件
     *
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (isExist(file)) {
            return file;
        }
        try {
            if (file.isFile()) {
                file.createNewFile();
            } else {
                file.mkdirs();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(String filePath, String content) {
        return write(filePath, content, false);
    }

    /**
     * 写文件
     *
     * @param filePath
     * @param content
     * @param append
     * @return
     */
    public static boolean write(final String filePath, final String content, final boolean append) {
        return write(createFile(filePath), content, append);
    }

    /**
     * 文件写入
     *
     * @param file    写入文件
     * @param content 内容
     * @param append  是否追加
     * @return
     */
    public static boolean write(final File file, final String content, final boolean append) {
        if (file == null || StringUtils.isEmpty(content)) {
            return false;
        }
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            fos = new FileOutputStream(file, append);
            pw = new PrintWriter(fos);
            pw.write(content);
            pw.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(fos, pw);
        }
        return false;
    }


    public static boolean write(final String filePath, final byte[] bytes, final boolean append) {
        return write(createFile(filePath), bytes, append);
    }

    /**
     * 写入字节
     *
     * @param file
     * @param bytes
     * @param append
     * @return
     */
    public static boolean write(final File file, final byte[] bytes, final boolean append) {
        if (file == null || bytes == null) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, append);
            fos.write(bytes);
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return false;
    }


    public static byte[] read(final File file){
        if (file == null) {
            return null;
        }
//        RandomAccessFile
        return null;
    }

    public static boolean delete(File file){
        return false;
    }
    public static boolean isExist(File file) {
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public static List<String> readFileFormDir(String dir){
        return readFileFormDir(new File(dir));
    }
    /**
     * 查询当前文件夹下的所有文件
     *
     * @param file
     * @return
     */
    public static List<String> readFileFormDir(File file){
        List<String> filenames = new ArrayList<>();
        if (file.isFile()) {
            return filenames;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return filenames;
        }
        for (File f : files) {
            if (f.isFile()) {
                filenames.add(f.getName());
            }
        }
        return filenames;
    }
    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
