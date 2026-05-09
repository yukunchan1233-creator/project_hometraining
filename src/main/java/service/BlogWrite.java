package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.BlogDao;
import model.BlogDto;
import model.Command;

public class BlogWrite implements Command {

   @Override
   public void doCommand(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      
      request.setCharacterEncoding("utf-8");
      
      String name = request.getParameter("name");
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      Part imagefile = request.getPart("imgFile");  // imgFile로 수정
      
      //첨부파일은 getParameter() .메서드로 요청할 수 없다
      String fileName = "default.jpg";  // 기본값 설정
      if(imagefile != null && imagefile.getSize()>0) {
         //첨부파일이 존재하면
         String oriFileName = Paths.get(imagefile.getSubmittedFileName()).getFileName().toString();
         //오리지널 파일 이름 구하기
         fileName = UUID.randomUUID().toString()+"_"+oriFileName;
         //파일중복을 피하기 위해서 오리지널 파일이름 변경한다
         System.out.println("UUID : "+fileName);
         String uploadPath = "C:/upload";
         //물리적으로 첨부파일 저장할 경로
         File uploadDir = new File(uploadPath);
         //uploadPath 파일 경로 정보 객체 생성
         if(!uploadDir.exists()) {
            //upload 폴더가 존재하지 않으면
            uploadDir.mkdirs();
            //최상위 폴더부터 하위폴더까지 생성한다
         }
         String filePath = uploadPath+File.separator+fileName;
         //파일 전체 경로를 구한다
         //separator 운영체제에 따라 파일 경로 자동으로 바꾼다
         System.out.println("filePath : "+filePath);
         imagefile.write(filePath);
         //파일을 물리적으로 저장한다
         System.out.println("파일저장 완료 : "+filePath);
      }
      
      // 파일이 있든 없든 글은 저장해야 함
      BlogDto dto = new BlogDto();
      dto.setTitle(title);
      dto.setContent(content);
      dto.setImgfile(fileName);
      dto.setName(name);
      
      BlogDao dao = new BlogDao();
      dao.blogInsert(dto);
      
      // hometraining context path를 고려한 리다이렉트
      response.sendRedirect(request.getContextPath() + "/port/list.do");
   }
}




