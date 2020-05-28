package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.project.bluemarlin2.bluemarlin2.constants.ErrorCode.USER_ALREADY_EXISTS;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepositoryImpl implements MemberCustomRepository{
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    public Member findByUserIdAndFetchUrlSources(String userId){
        List<Member> byUserId = em.createQuery("select distinct m from Member m" +
                " join fetch m.urlSources us" +
                " where m.userId =: userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
        if(byUserId.size() > 1){
            throw new IllegalStateException("Cannot have same userIDs");
        }
        if(byUserId.size() == 0)
            return null;
        return byUserId.get(0);
    }

    @Transactional
    public Member encodeAndSave(Member member){
        member.encodePassword(passwordEncoder);
        em.persist(member);
        return member;
    }
}
