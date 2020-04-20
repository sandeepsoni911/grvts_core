package com.owners.gravitas.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hubzu.common.logger.HubzuLog;


/**
* FileStorageUtil.java
* 
* Class to implement Amazon S3 file upload and download features
* 
* @author dalaipar
*/
@Component
public class FileStorageUtil {
	
	
	private static final HubzuLog LOGGER = HubzuLog.getLogger( FileStorageUtil.class );
      
   @Value("${notification.file.output.bucketName}")
   private String bucketName;
   
   @Value("${notification.file.keyName}")
    private String keyName;
   
   @Value("${notification.file.secretKey}")
    private String secretKey;
   
   @Value("${notification.upload.directory.location}")
   private String uploadDirectory;
   
    /*@Value( "${coshopping.thumbnail.width}" ) 
   private Integer thumbnailWidth;*/
   
   
    /**
     * Method to upload given File Object and file name into Amazon S3
     * 
     * @param uploadFile
     *            - File object that contains the SSA rules file to be uploaded
     * @param fileName
     *            - name to be given to the uploaded file
     *            
     * @return String
     *            - result of file upload to Amazon S3
     *                       
     * @author dalaipar
     */
    public void uploadFile(File uploadFile, String fileName){
        AmazonS3Client s3Client = getAmazonS3Client();
        AccessControlList acl = new AccessControlList();
        acl.grantPermission( GroupGrantee.AllUsers, Permission.Read);
        if(uploadFile==null || uploadFile.length()==0){
            return;
        }
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile).withAccessControlList( acl ));
    }
    
//    public void uploadFile(JasperReportBuilder  jasperReportBuilder, String fileName) {
//    		AmazonS3Client s3Client = getAmazonS3Client();
//        AccessControlList acl = new AccessControlList();
//        acl.grantPermission( GroupGrantee.AllUsers, Permission.Read);
//        if(jasperReportBuilder==null ){
//            return;
//        }
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, jasperReportBuilder.to).withAccessControlList( acl ));
//    }
    
    public String getResourceUrl(String fileName){
        if(!StringUtils.isEmpty( fileName ) && fileName.contains( bucketName ))
            return fileName;
        return getAmazonS3Client().getResourceUrl( bucketName, fileName );
    }
    
    /**
     * Method to download file into given File Object from the given file name in Amazon S3
     * 
     * @param downloadFile
     *            - File object for the location where file will be downloaded
     * @param fileName
     *            - name of file to refer to required file in Amazon S3
     *            
     * @return String
     *            - content type of downloaded file
     *                       
     * @author dalaipar
     */
    public String downloadFile(File downloadFile, String fileName) {
        AmazonS3 s3Client = getAmazonS3Client();

        ObjectMetadata object = s3Client.getObject(new GetObjectRequest(bucketName, fileName), downloadFile);

        return object.getContentType();
    }
    
    public String deleteFile(String url) throws UnsupportedEncodingException{
        AmazonS3Client s3Client = getAmazonS3Client();
        AccessControlList acl = new AccessControlList();
        acl.grantPermission( GroupGrantee.AllUsers, Permission.Read);
        if(StringUtils.isEmpty( url )){
            return "FAILURE";
        }
        String fileName = url.substring( url.indexOf( "amazonaws.com/" ) + 14);
        fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
        s3Client.deleteObject( bucketName, fileName );
        return "SUCCESS";
    }
    
    /**
     * Method to authenticate connection to Amazon S3 using credentials
     *     
     * @return AmazonS3 Object
     *            - authenticated Amazon S3 client object to perform upload/download actions
     *                       
     * @author dalaipar
     */
   private AmazonS3Client getAmazonS3Client(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(keyName, secretKey);
        return new AmazonS3Client(credentials);
   }
   
    public File multipartToFile( MultipartFile multipart ) throws IllegalStateException, IOException {
        File convFile = new File(uploadDirectory, multipart.getOriginalFilename() );
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( multipart.getBytes() );
        fos.close();
        return convFile;
    }
    
    
    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}