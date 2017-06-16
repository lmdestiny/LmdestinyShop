package com.lmdestiny.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtil{
    protected String host;
    protected int port;
    protected String username;
    protected String password;

    /**
     * @param host ip
     * @param port 端口
     * @param username 账号
     * @param password 密码
     * */
    public SFTPUtil(String host, int port, String username, String password) {
        set(host, port, username, password);
    }

    public void set(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    Session sshSession = null ; 
    /**
     * 链接linux
     * */
    public ChannelSftp connect() {
        ChannelSftp sftp = null ;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            //LogManager.info(String.format("%s connect success" , host));
            Channel channel = sshSession.openChannel("sftp");
            channel.connect() ; 
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            //LogManager.err("connect:" + host , e ); 
        	System.out.println("1");
            close( null );
            return null ; 
        }
        return sftp;
    }
    /**
     * linux上传文件
     * */
    public void upload(String directory,File file){
        ChannelSftp sftp = connect() ; 
        try {
            if(null != sftp){
                sftp.cd(directory);
                sftp.put(new FileInputStream(file), file.getName());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	
        }finally{
            sftp.disconnect() ;
            sftp.exit();
            sshSession.disconnect();
        }
    }
    
    /**
     * linux上传文件
     * */
    public void upload(String directory,InputStream inputStream,String newFileName){
        ChannelSftp sftp = connect();
        try {
            if(null != sftp){
            	createDir(directory,sftp);
               // sftp.cd(directory);
                sftp.put(inputStream, newFileName);
            }
        } catch (SftpException e1) {
        	e1.printStackTrace();
        	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            sftp.disconnect() ;
            sftp.exit();
            sshSession.disconnect();
        }
    }

    
 /**
     * linux下载文件
     */
   /* public InputStream get(String directory){
        ChannelSftp sftp = connect() ;
        String fileName;
        try {
            if(null != sftp){
                File file = new File( directory ) ; 
                String parent = getParent( file );
                fileName = directory.substring(parent.length()+1);
                System.out.println(fileName);
                System.out.println(parent);
                sftp.cd( parent );
                return sftp.get(fileName);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
           // close(sftp);
        }
		return null;
    }*/
    
    /**
     * linux下载文件
     * */
    public byte[] get(String directory){
        ChannelSftp sftp = connect() ;
        String fileName;
        try {
            if(null != sftp){
                File file = new File( directory ) ; 
                String parent = getParent( file );
                fileName = directory.substring(parent.length()+1);
                System.out.println(fileName);
                System.out.println(parent);
                sftp.cd( parent );      
               	InputStream in = sftp.get(fileName);
               	return IOUtils.toByteArray(in);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
            close(sftp);
        }
		return null;
    }
    

    protected void close(ChannelSftp sftp){
        if(sftp!=null){
            sftp.disconnect() ;
            sftp.exit();
        }
        if(sshSession!=null){
            sshSession.disconnect();
        }
    }

    protected String getParent(File desc){
        if(SystemUtils.IS_OS_WINDOWS){
            String parent = desc.getParent(); 
            return parent.replace("\\", "/"); 
        }
        return desc.getParent() ;
    }
    
    /**
     * 创建一个文件目录
     * @throws Exception 
     */
    public void createDir(String createpath, ChannelSftp sftp) throws Exception {
    	System.out.println("被调用");
     try {
      if (isDirExist(createpath,sftp)) {
       sftp.cd(createpath);
      }
      String pathArry[] = createpath.split("/");
      StringBuffer filePath = new StringBuffer("/");
      for (String path : pathArry) {
       if (path.equals("")) {
        continue;
       }
       filePath.append(path + "/");
       if (isDirExist(filePath.toString(),sftp)) {
        sftp.cd(filePath.toString());
       } else {
        // 建立目录
        sftp.mkdir(filePath.toString());
        // 进入并设置为当前目录
        sftp.cd(filePath.toString());
       }
      }
      sftp.cd(createpath);
     } catch (SftpException e) {
      throw new Exception("创建路径错误：" + createpath);
     }
    }
    
    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory,ChannelSftp sftp) {
     boolean isDirExistFlag = false;
     try {
      SftpATTRS sftpATTRS = sftp.lstat(directory);
      isDirExistFlag = true;
      return sftpATTRS.isDir();
     } catch (Exception e) {
      if (e.getMessage().toLowerCase().equals("no such file")) {
       isDirExistFlag = false;
      }
     }
     return isDirExistFlag;
    }

    public static void main(String[] args) {

        /**
         * @param host ip
         * @param port 端口
         * @param username 账号
         * @param password 密码
         * */
    	SFTPUtil sftpUtils = new SFTPUtil("192.168.226.159" , 22 , "root" , "li220") ; 
        
    	//sftpUtils.upload("/opt", new File("D:\\java\\a.png"));

        sftpUtils.get("/taotao/images/2017/05/15/1494826118819916.png");
    }
}