package com.alioo.monitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static String separator = System.getProperty("line.separator");

    /**
     * 读取文件
     * 兼容从目录下读取与jar包中读取两种方式
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        String content = null;
        BufferedReader in = null;
        try {
            String lineSeparator = System.getProperty("line.separator", "\n");

            in = new BufferedReader(new InputStreamReader(FileUtil.class.getClassLoader().getResourceAsStream(path)));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line).append(lineSeparator);
            }
            content = buffer.toString();
        } catch (Exception e) {
            logger.error("读取文件[" + path + "]时异常", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return content;


    }

    /**
     * 读取文件
     * 兼容从目录下读取与jar包中读取两种方式
     *
     * @param path
     * @return
     */
    public static List<String> readFile2List(String path) {
        List<String> list = new ArrayList<>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = "";
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            logger.error("读取文件[" + path + "]时异常", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return list;


    }


    public static void writeFile(String path, List<String> list, boolean append) {
        if (list == null || list.isEmpty()) {
            return;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(path, append);
            for (String line : list) {
                fw.append(line).append(separator);
            }

            fw.flush();
            fw.close();
        } catch (Exception e) {
            logger.error("写入文件[" + path + "]时异常,list:" + list, e);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    public static void mkdirs(String path) {

        try {
            File file = new File(path);
            if (!file.exists()) {
                boolean flag = file.mkdirs();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteFile(File localFile) {
        if (localFile != null && localFile.exists()) {
            localFile.delete();
        }
    }


}
