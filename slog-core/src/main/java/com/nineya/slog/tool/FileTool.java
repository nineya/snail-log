package com.nineya.slog.tool;

import java.io.*;
import java.net.URISyntaxException;

/**
 * @author linsongwang
 * @date 2020/5/31 10:14
 * 文件操作工具
 */
public class FileTool {

    /**
     * 判断文件或目录是否存在
     * @param path 文件路径
     * @return true：存在，false：不存在
     */
    public static boolean fileExists(String path) {
        if(path==null){
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * 判断文件所在的路径是否存在，如果不存在则创建该路径
     * @param filePath 文件所在的路径
     */
    public static void createFileFolder(String filePath){
        File file = new File(filePath).getParentFile();
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 判断路径是不是文件夹
     * @param path 路径
     * @return true 是文件夹，false不是
     */
    public static boolean isDirectory(String path){
        if(path==null){
            return false;
        }
        return new File(path).isDirectory();
    }

    /**
     * 判断路径是不是文件
     * @param path 路径
     * @return true 是文件，false不是
     */
    public static boolean isFile(String path){
        if(path==null){
            return false;
        }
        return new File(path).isFile();
    }

    /**
     * 取得文件后缀
     * @param path 文件路径
     * @return 返回后缀名
     */
    public static String nameSuffix(String path){
        if(path==null){
            return null;
        }
        int index = path.lastIndexOf(".");
        if (index==-1){
            return null;
        }
        return path.substring(index + 1);
    }

    /**
     * 传入路径，返回是否是绝对路径，是绝对路径返回true，反之
     * @param path 路径
     * @return true:是绝对路径，false：不是绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if(path==null){
            return false;
        }
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }

    /**
     * 取得resource目录中的文件的路径
     * @param fileName 文件名
     * @return 文件的输入流
     */
    public static InputStream getResourcesStream(String fileName) {
        return FileTool.class.getClassLoader().getResourceAsStream(fileName);
    }

    // 取得jar所在path
    public static String jarWholePath(){
        String jarWholePath = FileTool.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(System.getProperty("os.name").contains("dows")) {
            jarWholePath = jarWholePath.substring(1);
        }
        if(jarWholePath.contains("jar")) {
            return jarWholePath.substring(0,jarWholePath.lastIndexOf("/") + 1);
        }
        if (jarWholePath.endsWith("target/classes/")){
            return jarWholePath.substring(0, jarWholePath.length() - 15);
        }
        return jarWholePath;
    }
}
