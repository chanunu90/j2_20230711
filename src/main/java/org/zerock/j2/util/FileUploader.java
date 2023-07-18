package org.zerock.j2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Component
@Log4j2
public class FileUploader {

    //중첩클래스
    //예외처리 만든 클래스
    public static class UplaodException extends RuntimeException{
        public UplaodException(String msg){
            super(msg);
        }
    }

    @Value("${org.zerock.upload.path}")
    private String path;

    public void removeFiles(List<String> fileNames){
        if(fileNames == null || fileNames.size() == 0){
            return;
        }

        for(String fname: fileNames){
            //원본 파일
            File original = new File(path, fname);
            //썸네일 파일
            File thumb = new File(path, "s_"+fname);

            //thumb.exists() 이 파일이 존재 한다면 이라는 메소드
            if(thumb.exists()){
                thumb.delete();
            }
            original.delete();
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumbnail){

        if(files == null || files.size() == 0){
            throw new UplaodException("No File");
        }

        List<String> uploadFileNames = new ArrayList<>();

        // log.info("path: " + path);
        // log.info("files: " + files);

        //loop
        //람다식에서 예외를 밖으로 던지는데 문제가 있음
        for (MultipartFile mfile : files) {

            //실제 파일명
            String originalFileName = mfile.getOriginalFilename();
            //uuid 생성
            String uuid = UUID.randomUUID().toString();

            //저장할 파일명 변수처리
            //나중에 썸네일 만들 때 s_를 붙이는걸 편하게 사용하기 위함
            String saveFileName = uuid + "_" + originalFileName;

            //파일 실제로 저장하는 부분
            //파일 만들 때 폴더이름, 파일이름을 주면 생성 가능
            //인풋스트림 받아오는거
            File savFile = new File(path, saveFileName);

            //예외처리
            //auto close 해주기 위해 InputStream, OutPutStream 선언
            //하나라도 실패하면 던지기 위함
            //Exception은 강력한 리턴 수단
            try (
                    InputStream in = mfile.getInputStream();
                    OutputStream out = new FileOutputStream(savFile);
            ) {
                //원본파일 copy해주는 영역
                FileCopyUtils.copy(in, out);

                //makeThumbnail = true 일 때만 썸네일 만드는 로직
                if(makeThumbnail){
                    //썸네일 만드는 영역
                    //아웃풋스트림 내보내는거
                    File thumbOutFile = new File(path, "s_" + saveFileName);

                    Thumbnailator.createThumbnail(savFile, thumbOutFile, 200, 200);
                }

                //실제 파일 저장
                uploadFileNames.add(saveFileName);

            } catch (Exception e) {
                throw new UplaodException("Upload Fail: " + e.getMessage());
            }

        }
        
        log.info("==================uploadFileNames완료========================");

        return uploadFileNames;
    }
}