package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * [파일 업로드 유틸리티 - FileUploadUtil]
 * 설명: 이미지 파일 업로드를 처리하는 유틸리티 클래스입니다.
 */
public class FileUploadUtil {
	
	// 업로드 디렉토리 경로 (프로젝트의 images 폴더)
	private static final String UPLOAD_DIR = "images";
	
	/**
	 * 파일 업로드 처리
	 * @param request HttpServletRequest
	 * @param partName 파트 이름 (예: "image_file")
	 * @return 업로드된 파일의 상대 경로 (예: "images/abc123.jpg")
	 */
	public static String uploadFile(HttpServletRequest request, String partName) throws IOException {
		try {
			System.out.println("[FileUploadUtil] 파일 업로드 시작 - partName: " + partName);
			Part part = request.getPart(partName);
			
			if(part == null) {
				System.out.println("[FileUploadUtil] Part가 null입니다.");
				return null; // 파일이 업로드되지 않음
			}
			
			long partSize = part.getSize();
			System.out.println("[FileUploadUtil] Part 크기: " + partSize);
			
			if(partSize == 0) {
				System.out.println("[FileUploadUtil] Part 크기가 0입니다.");
				return null; // 파일이 업로드되지 않음
			}
			
			String fileName = part.getSubmittedFileName();
			System.out.println("[FileUploadUtil] 원본 파일명: " + fileName);
			
			if(fileName == null || fileName.isEmpty()) {
				System.out.println("[FileUploadUtil] 파일명이 없습니다.");
				return null;
			}
			
			// 파일 확장자 추출
			String extension = "";
			int lastDot = fileName.lastIndexOf(".");
			if(lastDot > 0) {
				extension = fileName.substring(lastDot);
			}
			
			// 고유한 파일명 생성 (UUID 사용)
			String uniqueFileName = UUID.randomUUID().toString() + extension;
			
			// 업로드 경로 설정
			// Eclipse 개발 환경: 소스 코드의 images 폴더에 저장 (서버 재시작 후에도 유지)
			String realPath = request.getServletContext().getRealPath("/");
			
			// 소스 코드의 images 폴더 경로 찾기
			String sourceImagesPath = null;
			if(realPath != null && realPath.contains("wtpwebapps")) {
				// 배포 경로에서 소스 경로로 변환
				// 예: C:\jspstudy\.metadata\...\wtpwebapps\hometraining
				// -> C:\jspstudy\hometraining\src\main\webapp\images
				int metadataIndex = realPath.indexOf(".metadata");
				if(metadataIndex > 0) {
					String workspaceRoot = realPath.substring(0, metadataIndex);
					sourceImagesPath = workspaceRoot + "hometraining" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + UPLOAD_DIR;
				}
			}
			
			// 배포 경로 (웹 접근용)
			String deployPath = realPath != null ? realPath + UPLOAD_DIR : null;
			
			// 소스 코드 images 폴더에 저장 (우선)
			if(sourceImagesPath != null) {
				File sourceDir = new File(sourceImagesPath);
				if(!sourceDir.exists()) {
					sourceDir.mkdirs();
				}
				String sourceFilePath = sourceImagesPath + File.separator + uniqueFileName;
				part.write(sourceFilePath);
				
				// 파일 저장 확인
				File savedFile = new File(sourceFilePath);
				if(savedFile.exists()) {
					System.out.println("[FileUploadUtil] 소스 경로에 저장 성공: " + sourceFilePath);
					
					// 배포 경로에도 복사 (즉시 웹 접근 가능하도록)
					if(deployPath != null) {
						try {
							File deployDir = new File(deployPath);
							if(!deployDir.exists()) {
								deployDir.mkdirs();
							}
							String deployFilePath = deployPath + File.separator + uniqueFileName;
							Files.copy(
								Paths.get(sourceFilePath),
								Paths.get(deployFilePath),
								StandardCopyOption.REPLACE_EXISTING
							);
							System.out.println("[FileUploadUtil] 배포 경로에도 복사 완료: " + deployFilePath);
						} catch (Exception e) {
							System.err.println("[FileUploadUtil] 배포 경로 복사 실패: " + e.getMessage());
						}
					}
					
					System.out.println("[FileUploadUtil] DB에 저장할 상대 경로: " + UPLOAD_DIR + "/" + uniqueFileName);
					return UPLOAD_DIR + "/" + uniqueFileName;
				}
			}
			
			// 소스 경로 저장 실패 시 배포 경로에 저장
			if(deployPath != null) {
				File deployDir = new File(deployPath);
				if(!deployDir.exists()) {
					deployDir.mkdirs();
				}
				String deployFilePath = deployPath + File.separator + uniqueFileName;
				part.write(deployFilePath);
				
				File savedFile = new File(deployFilePath);
				if(savedFile.exists()) {
					System.out.println("[FileUploadUtil] 배포 경로에 저장 성공: " + deployFilePath);
					System.out.println("[FileUploadUtil] DB에 저장할 상대 경로: " + UPLOAD_DIR + "/" + uniqueFileName);
					return UPLOAD_DIR + "/" + uniqueFileName;
				}
			}
			
			System.err.println("[FileUploadUtil] 파일 저장 실패");
			return null;
			
		} catch (javax.servlet.ServletException e) {
			// multipart 파싱 오류
			System.err.println("[FileUploadUtil] ServletException 발생: " + e.getMessage());
			e.printStackTrace();
			throw new IOException("파일 업로드 처리 중 오류가 발생했습니다: " + e.getMessage(), e);
		} catch (IOException e) {
			// I/O 오류
			System.err.println("[FileUploadUtil] IOException 발생: " + e.getMessage());
			e.printStackTrace();
			throw e;  // IOException은 그대로 전파
		} catch (Exception e) {
			// 기타 예외
			System.err.println("[FileUploadUtil] 예상치 못한 예외 발생: " + e.getMessage());
			e.printStackTrace();
			throw new IOException("파일 업로드 중 예상치 못한 오류가 발생했습니다: " + e.getMessage(), e);
		}
	}
	
	/**
	 * 기존 파일 삭제
	 * @param request HttpServletRequest
	 * @param filePath 삭제할 파일의 상대 경로
	 */
	public static void deleteFile(HttpServletRequest request, String filePath) {
		if(filePath == null || filePath.isEmpty()) {
			return;
		}
		
		try {
			String realPath = request.getServletContext().getRealPath("/") + filePath;
			File file = new File(realPath);
			if(file.exists() && file.isFile()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
