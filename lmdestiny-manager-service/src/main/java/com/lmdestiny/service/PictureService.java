package com.lmdestiny.service;


import org.springframework.web.multipart.MultipartFile;

import com.lmdestiny.util.PictureResult;

public interface PictureService {
	public PictureResult uploadFile(MultipartFile uploadFile);
	public byte[] downloadFile(String path);
}
