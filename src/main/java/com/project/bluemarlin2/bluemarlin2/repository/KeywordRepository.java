package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordRepository{
    private final EntityManager em;

    @Transactional
    public void save(Keyword keyword){
        em.persist(keyword);
    }

    public Keyword findOne(Long keywordId){
        Keyword keyword = em.find(Keyword.class, keywordId);
        return keyword;
    }

    public List<Keyword> findAllByUrlSource(UrlSource urlSource){
        List resultList = em.createQuery("Select k from Keyword k where k.urlSource.id = :urlSourceId and k.urlSource.member.id = :memberId")
                .setParameter("urlSourceId", urlSource.getId())
                .setParameter("memberId", urlSource.getMember().getId())
                .getResultList();
        return resultList;
    }

    public void remove(Long keywordId){
        Keyword keyword = em.find(Keyword.class, keywordId);
        UrlSource urlSource = keyword.getUrlSource();
        List<Keyword> keywords = urlSource.getKeywords();
        for (Keyword word : keywords) {
            if(word.getId() == keyword.getId()){
                keywords.remove(word);
            }
        }
        urlSource.setKeywords(keywords);
        em.remove(keyword);
    }
}
