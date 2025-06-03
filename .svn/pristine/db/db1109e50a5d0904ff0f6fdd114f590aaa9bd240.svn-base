
package com.velotech.fanselection.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class FileUtil {

	private static boolean throughCIFS;

	private static boolean throughS3;

	private static boolean throughLocal;

	private static String localUrl;

	private static String smbUrlRemote;

	private static String accessKey;

	private static String seceretKey;

	private static String region;

	private static String bucketName;

	private static NtlmPasswordAuthentication auth = null;

	private static VelotechUtil velotechUtil = null;

	private static AmazonS3 s3Client = null;

	static Logger log = LogManager.getLogger(FileUtil.class.getName());

	static {

		Properties props = new ResourceUtil().getApplicationProperty();
		throughCIFS = Boolean.valueOf(props.getProperty("throughCIFS").toString());
		throughS3 = Boolean.valueOf(props.getProperty("throughS3").toString());
		throughLocal = Boolean.valueOf(props.getProperty("throughLocal").toString());
		localUrl = props.getProperty("localStoragePath").toString();
		smbUrlRemote = props.getProperty("smbUrl.remote").toString();
		auth = new NtlmPasswordAuthentication("", props.getProperty("ntlmUsername"), props.getProperty("ntlmPassword"));
		// S3 property
		accessKey = props.getProperty("aws.access.key.id");
		seceretKey = props.getProperty("aws.access.key.secret");
		region = props.getProperty("aws.region");
		bucketName = props.getProperty("aws.s3.audio.bucket");
		velotechUtil = new VelotechUtil();
	}

	public static void uploadFile(String path, String fileName, MultipartFile multipartFile) {

		try {
			if (throughS3) {
				uploadFileOnS3(path, fileName, multipartFile);
			} else if (throughCIFS) {
				storeFileOnRemoteMachine(path, fileName, multipartFile);
			} else {
				storeFileOnLocalMachine(path, fileName, multipartFile);
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public static Object showFile(String path, String fileName, String dupFileName, boolean reportCall) {

		Object finalPath = null;
		try {
			if (throughS3) {
				finalPath = generatePreSignUrl(path + "/" + dupFileName, 60000);
			} else if (throughCIFS) {
				finalPath = copyFileUsingCifsToUserTemp(dupFileName, path, reportCall);
			} else {
				finalPath = copyFileToUserTemp(dupFileName, fileName, path);
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalPath;
	}

	public static void deleteFile(String path, String fileName) {

		try {
			if (throughS3) {
				deleteFileOnS3(path, fileName);
			} else if (throughCIFS) {
				deleteFileOnRemoteMachine(path, fileName);
			} else {
				deleteFileOnLocalMachine(path, fileName);
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void deleteFileOnS3(String key, String fileName) {

		// TODO Auto-generated method stub
		try {
			AmazonS3 s3Client = getAmazonS3();
			s3Client.deleteObject(bucketName, key + "/" + fileName);
		} catch (AmazonServiceException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void deleteFileOnLocalMachine(String path, String fileName) {

		// TODO Auto-generated method stub
		try {
			File srcFile = new File(getLocalUrl() + "/" + path + "/" + fileName);
			srcFile.delete();
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void deleteFileOnRemoteMachine(String path, String fileName) {

		// TODO Auto-generated method stub
		try {
			SmbFile srcFile = new SmbFile(getSmbUrlRemote() + "/" + path + "/" + fileName, getAuth());
			srcFile.delete();
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	private static void storeFileOnRemoteMachine(String path, String fileName, MultipartFile multipartFile) {

		SmbFile sFile = null;
		try {
			String finalPath = getSmbUrlRemote() + "/" + path;
			sFile = new SmbFile(finalPath, getAuth());
			if (!sFile.exists()) {
				sFile.mkdirs();
			}
			SmbFile newSfile = new SmbFile(finalPath + "/" + fileName, getAuth());
			if (!newSfile.exists()) {
				SmbFileOutputStream sfos = new SmbFileOutputStream(newSfile);
				sfos.write(multipartFile.getBytes());
				sfos.close();
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void storeFileOnLocalMachine(String path, String fileName, MultipartFile multipartFile) {

		try {
			String finalPath = getLocalUrl() + "/" + path;
			File f1 = new File(finalPath);
			if (!f1.exists()) {
				f1.mkdirs();
			}
			finalPath = finalPath + "/" + fileName;
			File newFile = new File(finalPath);
			if (!newFile.exists()) {
				newFile.createNewFile();

				FileOutputStream fos = new FileOutputStream(newFile);
				fos.write(multipartFile.getBytes());
				fos.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String copyFileUsingCifsToUserTemp(String dupFileName, String foldername, boolean reportCall) {

		String path = null;
		SmbFile srcFile = null;
		try {
			srcFile = new SmbFile(getSmbUrlRemote() + "/" + foldername + "/" + dupFileName, FileUtil.getAuth());
			byte[] fileContent = new byte[(int) srcFile.length()];
			SmbFileInputStream fileInputStream = new SmbFileInputStream(srcFile);

			fileInputStream.read(fileContent);
			fileInputStream.close();

			File dtFile = new File(velotechUtil.getUserContextPath(), dupFileName);

			FileOutputStream fos = new FileOutputStream(dtFile);
			fos.write(fileContent);
			fos.close();

			if (reportCall != true)
				path = velotechUtil.getUserContextPath() + dupFileName;
			else
				path = velotechUtil.getUserContextPath() + dupFileName;

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}

	public static String copyFileToUserTemp(String dupFileName, String fileName, String path) {

		String finalPath = null;

		try {
			String filePath = FileUtil.getLocalUrl() + "/" + path + "/" + dupFileName;
			String tempPath = velotechUtil.getUserContextPath() + fileName;
			File oldFile = new File(filePath);
			File newFile = new File(tempPath);
			if (!newFile.exists())
				newFile.createNewFile();
			finalPath = velotechUtil.getUserContextPath() + fileName;
			FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalPath;
	}

	public static AmazonS3 getAmazonS3() {

		try {
			if (s3Client == null) {
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, seceretKey);

				s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s3Client;
	}

	private static void uploadFileOnS3(String key, String fileName, MultipartFile multipartFile) {

		// TODO Auto-generated method stub
		try {
			AmazonS3 s3Client = getAmazonS3();
			File newFile = new File(multipartFile.getOriginalFilename());
			newFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(multipartFile.getBytes());
			fos.close();
			s3Client.putObject(new PutObjectRequest(bucketName, key + "/" + fileName, newFile));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public static URL generatePreSignUrl(final String key, long ms) {

		URL url = null;
		try {
			AmazonS3 s3Client = getAmazonS3();
			java.util.Date expiration = new java.util.Date();
			long msec = expiration.getTime();
			msec += ms;
			expiration.setTime(msec);
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);
			url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return url;
	}

	public static boolean isThroughCIFS() {

		return throughCIFS;
	}

	public static void setThroughCIFS(boolean throughCIFS) {

		FileUtil.throughCIFS = throughCIFS;
	}

	public static String getSmbUrlRemote() {

		return smbUrlRemote;
	}

	public static void setSmbUrlRemote(String smbUrlRemote) {

		FileUtil.smbUrlRemote = smbUrlRemote;
	}

	public static NtlmPasswordAuthentication getAuth() {

		System.setProperty("jcifs.smb.client.disablePlainTextPasswords", "true");
		return auth;
	}

	public static void setAuth(NtlmPasswordAuthentication auth) {

		FileUtil.auth = auth;
	}

	public static boolean isThroughS3() {

		return throughS3;
	}

	public static void setThroughS3(boolean throughS3) {

		FileUtil.throughS3 = throughS3;
	}

	public static boolean isThroughLocal() {

		return throughLocal;
	}

	public static void setThroughLocal(boolean throughLocal) {

		FileUtil.throughLocal = throughLocal;
	}

	public static String getLocalUrl() {

		return localUrl;
	}

	public static void setLocalUrl(String localUrl) {

		FileUtil.localUrl = localUrl;
	}

	public static File convertMultipartFile(MultipartFile multiPartFile, String finalPath, String fileName) {

		File f1 = new File(finalPath);
		File newFile = null;
		try {
			if (!f1.exists()) {
				f1.mkdirs();
			}
			finalPath = finalPath + fileName;
			newFile = new File(finalPath);
			newFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(multiPartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return newFile;
	}

	public static List<Object> loadFilesWithExtension(String fileType) {

		List<Object> files = new ArrayList<>();
		try {
			File folder = new File(velotechUtil.getCompanyDocumentPath());
			
			
			String[] filesArray = folder.list();
			for (int i = 0; i < filesArray.length; i++) {

				switch (fileType) {
				case "All":
					if (!filesArray[i].contains(".jrxml"))
					files.add(filesArray[i]);
					continue;
				case ".jasper":
					if (filesArray[i].contains(".jasper"))
						files.add(filesArray[i]);
					continue;
				case ".png":
					if (filesArray[i].contains(".png"))
						files.add(filesArray[i]);
					continue;
				case ".jpg":
					if (filesArray[i].contains(".jpg"))
						files.add(filesArray[i]);
					continue;
				case ".dxf":
					if (filesArray[i].contains(".dxf"))
						files.add(filesArray[i]);
					continue;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return files;

	}

}
