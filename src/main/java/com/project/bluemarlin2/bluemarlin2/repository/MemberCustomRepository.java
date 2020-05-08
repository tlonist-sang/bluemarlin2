package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository {
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    public Member findByUserId(String userId){
        List<Member> byUserId = em.createQuery("select m from Member m"+
                " join fetch m.urlSources us" +
                " where m.userId =: userId")
                .setParameter("userId", userId)
                .getResultList();
        if(byUserId.size() > 1){
            throw new IllegalStateException("Cannot have same userIDs");
        }
        return byUserId.get(0);
    }
    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public Member save(Member member){
        member.encodePassword(passwordEncoder);
        em.persist(member);
        return member;
    }
}
