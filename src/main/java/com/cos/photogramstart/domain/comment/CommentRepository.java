package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // 사용 불가 @Modifying 으로 만들진 네이티브쿼리 메서드는 Comment 를 리턴할 수 없다.
    /*@Modifying
    @Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:cotent, :imageId, :userId, now())", nativeQuery = true)
    Comment mSave(String content, int imageId, int userId);*/
}
