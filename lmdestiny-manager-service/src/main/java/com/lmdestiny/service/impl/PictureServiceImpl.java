package com.lmdestiny.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lmdestiny.service.PictureService;
import com.lmdestiny.util.IDUtils;
import com.lmdestiny.util.PictureResult;
import com.lmdestiny.util.SFTPUtil;
@Service
	public class PictureServiceImpl implements PictureService {

		@Value("${IMAGE_BASE_URL}")
		private String IMAGE_BASE_URL;
		@Value("${FILI_UPLOAD_PATH}")
		private String FILI_UPLOAD_PATH;
		@Value("${FTP_SERVER_IP}")
		private String FTP_SERVER_IP;
		@Value("${FTP_SERVER_PORT}")
		private Integer FTP_SERVER_PORT;
		@Value("${FTP_SERVER_USERNAME}")
		private String FTP_SERVER_USERNAME;
		@Value("${FTP_SERVER_PASSWORD}")
		private String FTP_SERVER_PASSWORD;

		@Override
		public PictureResult uploadFile(MultipartFile uploadFile) {

			// 上传文件功能实现
			String path = savePicture(uploadFile);
			// 回显
			PictureResult result = new PictureResult(0,path);
			return result;
		}

		public byte[] downloadFile(String path){
			//图片回显
			SFTPUtil sftpUtils = new SFTPUtil(FTP_SERVER_IP,FTP_SERVER_PORT,FTP_SERVER_USERNAME,FTP_SERVER_PASSWORD);
			return sftpUtils.get(path);
		}
		
		private String savePicture(MultipartFile uploadFile) {
			String result = null;
			try {
				// 上传文件功能实现
				// 判断文件是否为空
				if (uploadFile.isEmpty())
					return null;
				// 上传文件以日期为单位分开存放，可以提高图片的查询速度
				String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date()) + "/"
						+ new SimpleDateFormat("MM").format(new Date()) + "/"
						+ new SimpleDateFormat("dd").format(new Date());

				// 取原始文件名
				String originalFilename = uploadFile.getOriginalFilename();
				// 新文件名
				String newFileName = IDUtils.genImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));
				// 转存文件，上传到sftp服务器
				SFTPUtil sftpUtils = new SFTPUtil(FTP_SERVER_IP,FTP_SERVER_PORT,FTP_SERVER_USERNAME,FTP_SERVER_PASSWORD);
				sftpUtils.upload(FILI_UPLOAD_PATH+filePath,uploadFile.getInputStream(),newFileName);
				result = FILI_UPLOAD_PATH+filePath+"/"+newFileName;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

	}
