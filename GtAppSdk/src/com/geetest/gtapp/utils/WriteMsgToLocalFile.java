package com.geetest.gtapp.utils;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

/**
 * 日志服务，日志默认会存储在SDcar里。
 * 
 * @author Administrator
 * 
 */
public class WriteMsgToLocalFile extends Activity {
	final static String FILE_NAME = "/log.txt";
	String logcat;
	public  static void setLogcatToText(String logcat) {
		
		write(logcat);
		read();
	}
	private static void write(String content){
		try
        {    
            //如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))
            {
                //获取SD卡的目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir.getCanonicalPath()
                    + FILE_NAME);
                //以指定文件创建    RandomAccessFile对象,第一个参数是文件名称，第二个参数是读写模式
                RandomAccessFile raf = new RandomAccessFile(
                    targetFile , "rw");
                //将文件记录指针移动到最后
                raf.seek(targetFile.length());
                // 输出文件内容
                raf.write(content.getBytes());
                raf.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	private static String read()
    {
        try
        {
            //如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))
            {
                //获取SD卡对应的存储目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                //获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream(sdCardDir
                    .getCanonicalPath()    + FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                //将指定输入流包装成BufferedReader
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder("");
                String line = null;
                while((line = br.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}