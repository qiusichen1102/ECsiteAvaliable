package com.example.demo.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

public  class  ValidateCodeUtil {


    private static Random random = new Random();
    private int width = 165; //認証コードの幅
    private int height = 45; //認証コードの幅高
    private int lineSize = 30; //認証コードに混ざる妨害線の数
    private int randomStrNum = 4; //認証コードの文字数

    private String randomString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ";
    private final String sessionKey = "JCCODE";

    //フォントの設定
    private Font getFont() {
        return new Font("Times New Roman", Font.ROMAN_BASELINE, 40);
    }

    //色の設定
    private static Color getRandomColor(int fc, int bc) {

        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);

        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);

        return new Color(r, g, b);
    }

    //妨害線の描画
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);

    }

    //ランダムな文字の取得
    private  String getRandomString(int num){
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    //文字列の描画
    private String drawString(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        //System.out.println(random.nextInt(randomString.length()));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }


    //ランダムな画像の生成
    public void getRandomCodeImage(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        // BufferedImageクラスは、バッファを持つImageクラスであり、Imageクラスは画像情報を記述するためのクラスです
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());
        // 妨害線
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }
        // ランダムな文字
        String randomStr = "";
        for (int i = 0; i < randomStrNum; i++) {
            randomStr = drawString(g, randomStr, i);
        }
        System.out.println("随机字符："+randomStr);
        g.dispose();
        //以前のセッションからの認証コード情報を削除する
        session.removeAttribute(sessionKey);
        //認証コードをセッションに再度設定する
        session.setAttribute(sessionKey, randomStr);
        try {
            //画像をPNG形式で返す、返されるものは画像です
            ImageIO.write(image, "PNG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //ランダムな画像のBase64エンコード文字列を生成する

    public String getRandomCodeBase64(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImageクラスは、バッファを持つImageクラスであり、Imageクラスは画像情報を記述するためのクラスです
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());
        //干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        //ランダムな文字
        String randomStr = "";
        for (int i = 0; i < randomStrNum; i++) {
            randomStr = drawString(g, randomStr, i);
        }
        System.out.println("随机字符："+randomStr);
        g.dispose();
        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, randomStr);
        String base64String = "";
        try {
            //画像を直接返す
            //  ImageIO.write(image, "PNG", response.getOutputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);

            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            base64String = encoder.encodeToString(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64String;
    }



}
