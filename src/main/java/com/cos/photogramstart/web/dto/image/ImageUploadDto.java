package com.cos.photogramstart.web.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file; // 파일을 저장하기 위한 타입
    private String caption;

}
