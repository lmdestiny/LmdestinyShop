package com.lmdestiny.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class TestFtpClient {

	@Test
	public void test() throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.226.159",22);
		System.out.println(ftpClient.login("root", "li220"));
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		FileInputStream inputStream = new FileInputStream(new File("D:\\java\\a.png"));
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalActiveMode();
		ftpClient.changeWorkingDirectory("/home/test");
		ftpClient.storeFile("123.jpg", inputStream);
		System.out.println("文件上传成功");
		inputStream.close();
		ftpClient.logout();
		
		

	}

}
