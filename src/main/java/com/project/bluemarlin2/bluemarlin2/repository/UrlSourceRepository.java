package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
public class UrlSourceRepository {
    private final EntityManager em;

    public UrlSource findById(Long id){
        UrlSource urlSource = em.find(UrlSource.class, id);
        return urlSource;
    }
}
