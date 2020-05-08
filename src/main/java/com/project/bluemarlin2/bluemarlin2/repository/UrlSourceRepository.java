package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class UrlSourceRepository {
    private final EntityManager em;

    public UrlSource findById(Long id){
        UrlSource urlSource = em.find(UrlSource.class, id);
        return urlSource;
    }

    public List<UrlSource> findAllByUserId(String userId){
        return em.createQuery("select distinct u from UrlSource u" +
                " join fetch u.member m" +
                " where m.userId =: userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
