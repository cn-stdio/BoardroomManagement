package com.ectocyst.component;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * @author Seagull_gby
 * @date 2019/3/6 20:12
 * Description: 将 File 转化为 MultipartFile
 */

@Component
public class FileToMultipartFile {

    /**
     * File 类型转换为 MultipartFile
     * @param f1 File 文件
     * @return MultipartFile 文件
     * @throws IOException
     */
    public MultipartFile fileToMultipartFile(File f1) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(f1);

        MultipartFile multipartFile = new MockMultipartFile(f1.getName(), f1.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

        return multipartFile;
    }
}
