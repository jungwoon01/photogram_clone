package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Comment commentEntity = commentService.writeComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 쓰기 성공", commentEntity), HttpStatus.CREATED);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
