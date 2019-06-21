package com.xakj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {
	/**
	 * 将一个文件夹的内容依次写入另一个文件
	 */
	public static void folderToFile(String outPath, String filePath) throws Exception{
        File folder = new File(outPath); // 文件夹路径
        File file = new File(filePath);// 写入路径
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File f : files) {
                    if (f.isDirectory()) {
                        System.out.println("文件夹:" + f.getAbsolutePath());
                        folderToFile(f.getAbsolutePath(), filePath);
                    } else {
                        System.out.println("文件:" + f.getAbsolutePath());
                        // 将该文件写入表格文件
                		FileInputStream fis = new FileInputStream(f);//建立一个输入流对象
                		FileOutputStream fos = new FileOutputStream(file,true);
                		
                		byte[] buf = new byte[1024];//每次读入文件数据量
                		int len = -1;
                		while ((len = (fis.read(buf))) != -1) {
                			fos.write(buf, 0, len);
                		}
                		fos.close();//关闭文件
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
	/**
	 * 将一个文件的内容写入另一个文件
	 */
	public static void fileToFile(String outPath, String filePath) throws Exception{
		File inFile = new File(outPath);//写入路径
    	FileInputStream fis = new FileInputStream(inFile);//建立一个输入流对象
		File outFile = new File(filePath);//写入路径
		FileOutputStream fos = new FileOutputStream(outFile,true);
		byte[] buf = new byte[1024];//每次读入文件数据量
		int len = -1;
		while ((len = (fis.read(buf))) != -1) {
			fos.write(buf, 0, len);
		}
		fos.close();//关闭文件
    }
	/**
	 * 先根遍历序递归删除文件夹
	 *
	 * @param dirFile 要被删除的文件或者目录
	 * @return 删除成功返回true, 否则返回false
	 */
	public static boolean deleteFile(String path) {
		File dirFile = new File(path);
	    // 如果dir对应的文件不存在，则退出
	    if (!dirFile.exists()) {
	        return false;
	    }

	    if (dirFile.isFile()) {
	        return dirFile.delete();
	    } else {
	        for (File file : dirFile.listFiles()) {
	            deleteFile(file.getAbsolutePath());
	        }
	    }

	    return dirFile.delete();
	}
}
