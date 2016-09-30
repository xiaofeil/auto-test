package com.xuanru.util;

import com.xuanru.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class UploadUtil {

	private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

	public static String uploadExcel(CommonsMultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			logger.info("upload fileName=[{}]", file.getOriginalFilename());
			String postfix = ParseExcelUtil.getPostfix(file.getOriginalFilename());
			if (!Constants.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)
					&& !Constants.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
				logger.info("上传文件格式不正确");
				return "上传文件格式不正确";
			}
			File dir = new File(Constants.EXCLE_UPLOAD_PATH);
			logger.info("dir path:{}",Constants.EXCLE_UPLOAD_PATH);
			if (!dir.exists()) {
				boolean mkdirFlag = dir.mkdir();
				logger.info("mkdirFlag=" + mkdirFlag);
			}
			// 拿到输出流，同时重命名上传的文件
			String fileName = file.getOriginalFilename();
			String newName = fileName.substring(0, fileName.indexOf(".")) + "_"
					+ DateUtil.dateToStrByTemplate(new Date(), Constants.DATE_TEMPLATE)
					+ fileName.substring(fileName.indexOf("."));
			FileOutputStream os = new FileOutputStream(Constants.EXCLE_UPLOAD_PATH + newName);
			// 拿到上传文件的输入流
			InputStream in = file.getInputStream();
			// 以写字节的方式写文件
			int b = 0;
			while ((b = in.read()) != -1) {
				os.write(b);
			}

			os.flush();
			os.close();
			in.close();

			return newName;
		} else {
			return "uploadExcel MultipartFile is null";
		}
	}

	public static String uploadImage(CommonsMultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			logger.info("upload fileName=[{}]", file.getOriginalFilename());
			String postfix = ParseExcelUtil.getPostfix(file.getOriginalFilename());
			if (!"jpg".equals(postfix) && !"jpeg".equals(postfix) && !"png".equals(postfix)
					&& !"icon".equals(postfix)) {
				logger.info("上传图片格式不正确");
				return "上传图片格式不正确";
			}
			File dir = new File(Constants.IMAGE_UPLOAD_PATH);
			if (!dir.exists()) {
				dir.mkdir();
			}
			// 拿到输出流，同时重命名上传的文件
			String fileName = file.getOriginalFilename();
//			String newName = fileName.substring(0, fileName.indexOf(".")) + "_"
//					+ DateUtil.dateToStrByTemplate(new Date(), Constants.DATE_TEMPLATE)
//					+ fileName.substring(fileName.indexOf("."));
			FileOutputStream os = new FileOutputStream(Constants.IMAGE_UPLOAD_PATH + fileName);
			// 拿到上传文件的输入流
			InputStream in = file.getInputStream();
			// 以写字节的方式写文件
			int b = 0;
			while ((b = in.read()) != -1) {
				os.write(b);
			}

			os.flush();
			os.close();
			in.close();

			return fileName;
		} else {
			logger.info("uploadImage MultipartFile is null");
			return "uploadImage MultipartFile is null";
		}
	}
}
