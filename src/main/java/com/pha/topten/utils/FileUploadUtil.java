package com.pha.topten.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class FileUploadUtil {

	/**
	 * 
	 * @param s3Client AmazonS3Client 객체
	 * @param bucket aws 버킷이름
	 * @param path 버킷 내부 경로
	 * @param img MultipartFile 객체로 이미지정보
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> s3Upload(AmazonS3Client s3Client, String bucket, String path, MultipartFile img){
		
		//버킷 내부의 이미지 경로
		String newName = newFileNameByNanotime(img.getOriginalFilename()); //뉴네임 얻어오기
		String bucketKey=path+newName; //path에 새로운 이름 더해서 버킷 만든다
		
		try {
			PutObjectRequest or = new PutObjectRequest(bucket, bucketKey, img.getInputStream(), ObjectMetadata(img));
			s3Client.putObject(or.withCannedAcl(CannedAccessControlList.PublicRead)); //저장 
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> result = new HashMap<>();
		result.put("url", s3Client.getUrl(bucket, bucketKey).toString().substring(6));
		result.put("bucketKey", bucketKey);
		result.put("orgName", img.getOriginalFilename());
		result.put("newName", newName);
		
		return result;
	}
	
	
	//Amazon S3에서 임시 경로에 저장된 이미지를 지정한 경로로 복사하고, 복사된 이미지의 URL을 반환
	public static String s3TempToSrc(AmazonS3Client s3Client, String bucket, String tempKey, String uploadKey) {
		CopyObjectRequest cor = new CopyObjectRequest(bucket, tempKey, bucket, uploadKey);
		s3Client.copyObject(cor.withCannedAccessControlList(CannedAccessControlList.PublicRead));
		
		s3Client.deleteObject(bucket, tempKey); //temp 경로 이미지 삭제
		
		return s3Client.getUrl(bucket, uploadKey).toString().substring(6); //src 폴더의 url
	}
	
	
	//클래스패스 내의 지정된 경로에 이미지를 업로드
	/**
	 * 
	 * @param path : static 하위 경로 예) "/images/upload/temp"
	 * @param tempImg : MultipartFile 객체
	 * @return
	 */
	public static Map<String, String> classPathUpload(String path, MultipartFile tempImg){
		ClassPathResource cpr = new ClassPathResource("static"+path);
		String newFileName = newFileName(tempImg.getOriginalFilename());
		
		try {
			File folder = cpr.getFile();
			if(!folder.exists())folder.mkdir();
			
			tempImg.transferTo(new File(folder, newFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, String> result = new HashMap<>();
		result.put("url", path+newFileName);
		result.put("orgName", tempImg.getOriginalFilename());
		
		return result;
	}

	//파일 설정 정보 //파일의 메타데이터를 설정
	private static ObjectMetadata ObjectMetadata(MultipartFile mf) {
		ObjectMetadata objectMetadata = new ObjectMetadata(); //객체생성
		objectMetadata.setContentType(mf.getContentType()); //파일타입
		objectMetadata.setContentLength(mf.getSize()); //파일 사이즈
		
		return objectMetadata;
	} //objectMetadata
	
	//방법1. 파일이름 UUID 이용하여 변경
	private static String newFileName(String orgName) {
		int idx = orgName.lastIndexOf(".");
		return UUID.randomUUID().toString() //새로운 이름을 UUID로 생성
						+orgName.substring(idx); //.확장자
	}
	
	//방법2. 파일이름 nanoTime()을 이용하여 변경
	private static String newFileNameByNanotime(String orgName) {
		int idx = orgName.lastIndexOf(".");
		return orgName.substring(0, idx)+"-"+(System.nanoTime()/1000000)
						+orgName.substring(idx); //확장자
	}
}
