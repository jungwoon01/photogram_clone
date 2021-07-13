package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // 모든 Repository 는 EntityManager 를 구현해서 만든어져 있는 구현체

    // 구독하기
    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    // 구독 취소
    @Transactional
    public void unsubscribe(int fromUserId, int toUserId) {
        subscribeRepository.mUnsubscribe(fromUserId, toUserId);
    }

    // 구독자 리스트
    @Transactional
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 from subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨

        // 1.물음표 principalId
        // 2.물음표 principalId
        // 3.마지막 물음표 pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리 실행(qlrm 라이브러리 필요 - Dto 에 DB 결과를 매핑하기 위해서)
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<SubscribeDto> subscribeDtoList = resultMapper.list(query, SubscribeDto.class);

        return subscribeDtoList;
    }
}
