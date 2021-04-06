package com.change.changesrmsback.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码图片生成类
 * @author Change
 */
public class VerifyCode {
    private int w = 70;//宽度
    private int h = 35;//高度
    private Random r = new Random();//随机数创建器
    private String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};//字体选择区
    private Color bgColor = new Color(255, 255, 255);//背景颜色
    private String text;//最终的文本

    /**
     * 保存图片到指定路径位置
     * @param image 传入图片
     * @param path  要保存的路径位置
     * @throws IOException 保存图片出错
     */
    public static void output(BufferedImage image, String path) throws IOException {
        OutputStream out = new FileOutputStream(path);
        ImageIO.write(image, "JPEG", out);
    }

    /**
     * 保存图片到指定输出流
     * @param image 传入图片
     * @param out   输出流
     * @throws IOException 保存图片出错
     */
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }

    /**
     * 随机生成颜色，作为文本的颜色
     * @return 返回由RGB组成的颜色
     */
    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        //都设置为150以下，防止太浅色看不清
        return new Color(red, green, blue);
    }

    /**
     * 随机生成字体，作为文本的字体
     * @return 字体样式
     */
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];//随机挑一个字体
        int style = r.nextInt(4);//生成随机的样式，0（无样式），1（粗体），2（斜体），3（粗体+斜体）
        int size = r.nextInt(5) + 24;//字体大小为24~28
        return new Font(fontName, style, size);
    }

    /**
     * 画干扰线
     * @param image 要画线的图片
     */
    private void drawLine(BufferedImage image) {
        int num = 3;//3条
        Graphics2D graphics = (Graphics2D) image.getGraphics();//拿画笔
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            //两点定一线
            graphics.setStroke(new BasicStroke(1.5F));//线条宽度
            graphics.setColor(Color.blue);//线条颜色
            graphics.drawLine(x1, y1, x2, y2);//画线
        }
    }

    /**
     * 随机生成字符
     * @return 一个随机字符
     */
    private char randomChar() {
        //验证码可选字符
        String codes = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 创建图片缓冲区
     * @return 图片
     */
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();//得到画笔
        graphics.setColor(this.bgColor);
        graphics.fillRect(0, 0, w, h);//整个图片画白
        return image;
    }

    /**
     * 得到验证码图片，宽高比2：1
     * @return 验证码图片
     */
    public BufferedImage getImage() {
        BufferedImage image = createImage();//创建图片缓冲区
        Graphics2D graphics = (Graphics2D) image.getGraphics();//得到绘制环境
        StringBuilder stringBuilder = new StringBuilder();//用来装载生成的验证码文本
        //画四个字符
        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";//生成一个字母
            stringBuilder.append(s);//添加到文本中
            float x = i * 1.0F * w / 4;//设置x轴坐标
            graphics.setFont(randomFont());//设置字体
            graphics.setColor(randomColor());//设置颜色
            graphics.drawString(s, x, h - 5);//写入字母s在x、h-5处
        }
        this.text = stringBuilder.toString();//保存字符
        drawLine(image);//添加干扰线
        return image;
    }

    /**
     * 返回文本
     * @return 验证码上的文本
     */
    public String getText() {
        return text;
    }
}
