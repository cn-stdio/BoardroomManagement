package com.ectocyst.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.ImageFormat;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/5 19:29
 * Description: 人脸检测工具类
 */
public class FaceDetectionUtil {

    /**
     * 人脸识别
     * @param f1 欲对比图片
     * @param f2 欲对比图片2
     * @return 精准度
     */
    public float faceEngine(File f1, File f2) {
        String appId = "HRhHKsgqJVFGoZUAoniFJPdZoNN1Na4UPPfPWvmT5NjQ";
        String sdkKey = "Fh3dNarSgkdY9EQ1QLEBQgWiLQLPrrF6vfCvDp77hxwn";

        ImageInfoUtil imageInfoUtil = getRGBData(f1);
        ImageInfoUtil imageInfoUtil2 = getRGBData(f2);

        FaceEngine faceEngine = new FaceEngine();
        //激活引擎
        faceEngine.active(appId, sdkKey);

        EngineConfiguration engineConfiguration = EngineConfiguration.builder().functionConfiguration(
                FunctionConfiguration.builder()
                        .supportAge(true)
                        .supportFace3dAngle(true)
                        .supportFaceDetect(true)
                        .supportFaceRecognition(true)
                        .supportGender(true)
                        .build()).build();
        //初始化引擎
        faceEngine.init(engineConfiguration);

        //人脸检测
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        faceEngine.detectFaces(imageInfoUtil.getRgbData(), imageInfoUtil.getWidth(), imageInfoUtil.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);

        //提取人脸特征
        FaceFeature faceFeature = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfoUtil.getRgbData(), imageInfoUtil.getWidth(), imageInfoUtil.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);

        FaceFeature faceFeature2 = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfoUtil2.getRgbData(), imageInfoUtil2.getWidth(), imageInfoUtil2.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature2);

        //人脸对比
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());

        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());

        FaceSimilar faceSimilar = new FaceSimilar();
        faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        return faceSimilar.getScore();

    }

    /**
     * 获得RGB数据
     * @param file 图片文件
     * @return
     */
    public ImageInfoUtil getRGBData(File file) {
        if (file == null)
            return null;
        ImageInfoUtil imageInfoUtil;
        try {
            //将图片文件加载到内存缓冲区
            BufferedImage image = ImageIO.read(file);
            imageInfoUtil = bufferedImage2ImageInfo(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return imageInfoUtil;
    }

    /**
     * 图片缓冲区处理
     * @param image
     * @return
     */
    private ImageInfoUtil bufferedImage2ImageInfo(BufferedImage image) {
        ImageInfoUtil imageInfoUtil = new ImageInfoUtil();
        int width = image.getWidth();
        int height = image.getHeight();
        // 使图片居中
        width = width & (~3);
        height = height & (~3);
        imageInfoUtil.width = width;
        imageInfoUtil.height = height;
        //根据原图片信息新建一个图片缓冲区
        BufferedImage resultImage = new BufferedImage(width, height, image.getType());
        //得到原图的rgb像素矩阵
        int[] rgb = image.getRGB(0, 0, width, height, null, 0, width);
        //将像素矩阵 绘制到新的图片缓冲区中
        resultImage.setRGB(0, 0, width, height, rgb, 0, width);
        //进行数据格式化为可用数据
        BufferedImage dstImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        if (resultImage.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
            ColorConvertOp colorConvertOp = new ColorConvertOp(cs, dstImage.createGraphics().getRenderingHints());
            colorConvertOp.filter(resultImage, dstImage);
        } else {
            dstImage = resultImage;
        }

        //获取rgb数据
        imageInfoUtil.rgbData = ((DataBufferByte) (dstImage.getRaster().getDataBuffer())).getData();
        return imageInfoUtil;
    }



}
