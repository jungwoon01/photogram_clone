package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// NotNull = null 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = null, 빈값, 공백(space) 까지 체크

@Data
public class CommentDto {
    @NotBlank // null, 빈값, 공백(space) 까지 체크
    private String content;
    @NotNull // 빈값 체크
    private Integer imageId;
}
