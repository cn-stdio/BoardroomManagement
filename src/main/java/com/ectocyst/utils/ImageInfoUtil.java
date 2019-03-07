package com.ectocyst.utils;

/**
 * @author Seagull_gby
 * @date 2019/3/5 19:33
 * Description: 人脸识别图片工具类
 */

public class ImageInfoUtil {

    public byte[] rgbData;
    public int width;
    public int height;

    public byte[] getRgbData() {
        return rgbData;
    }

    public void setRgbData(byte[] rgbData) {
        this.rgbData = rgbData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
