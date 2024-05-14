package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

public class ImgDownloadUtil {
	//パスを設定するための設定ファイル形式
	@Value("${reggie.filePath}")
    private String filePath;
	@Value("${reggie.contactPath}")
    private String contactPath;
	
    public static void downloadImage(String imgPath, HttpServletResponse response) throws Exception  {
    	//画像のパスを設定する
    	String filePath = "C:/MyECSite/ECサイト/03 製造/ecsite/src/main/resources/static/uploads/stock/";
        String path = "";
        //画像の名前を取得する
        int lastIndex = imgPath.lastIndexOf("/");
        if (lastIndex != -1 && lastIndex < imgPath.length() - 1) {
            path = imgPath.substring(lastIndex + 1);
        }
        FileInputStream fileInputStream = null;
        try {
        	//画像をストリーム形式でダウンロードし、ページに表示する
            fileInputStream = new FileInputStream(new File(filePath + path));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg"); 
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void downloadImageForContack(String imgPath, HttpServletResponse response) throws Exception  {
    	//画像のパスを設定する
    	String contactPath = "C:/MyECSite/ECサイト/03 製造/ecsite/src/main/resources/static/uploads/contact/";
        String path = "";
        //画像の名前を取得する
        int lastIndex = imgPath.lastIndexOf("/");
        if (lastIndex != -1 && lastIndex < imgPath.length() - 1) {
            path = imgPath.substring(lastIndex + 1);
        }
        FileInputStream fileInputStream = null;
        try {
        	//画像をストリーム形式でダウンロードし、ページに表示する
            fileInputStream = new FileInputStream(new File(contactPath + path));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg"); 
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
