package com.ectocyst;

import com.ectocyst.component.FileToMultipartFile;
import com.ectocyst.utils.QiniuUploadUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EctocystApplicationTests {

	@Autowired
	private FileToMultipartFile fileToMultipartFile;

	@Test
	public void contextLoads() throws Exception {

		/*File f1 = new File("D:\\IdeaProjects\\ectocyst\\src\\test\\java\\com\\ectocyst\\2.jpg");

		MultipartFile multipartFile = fileToMultipartFile.fileToMultipartFile(f1);*/

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(QiniuUploadUtil.getPictureUrlByJobId(10001));
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

}
