package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.AddKeywordDto;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.DeleteKeywordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Long add(AddKeywordDto addKeywordDto){
        UrlSource urlSource = em.find(UrlSource.class, addKeywordDto.getUrlId());
        urlSource.getKeywords().stream()
                .forEach(k->{
                    if(k.getWord().equals(addKeywordDto.getWord()))
                        throw new IllegalStateException("Cannot insert same keyword!");
                });

        Keyword keyword = new Keyword(urlSource, addKeywordDto.getWord());
        urlSource.getKeywords().add(keyword);
        em.persist(urlSource);

        return keyword.getId();
    }


    @Transactional
    public void remove(DeleteKeywordDto deleteKeywordDto){
        String userId = deleteKeywordDto.getUserId();
        String urlName = deleteKeywordDto.getUrlName();
        String keyword = deleteKeywordDto.getKeyword();

        List<Keyword> result = em.createQuery("select k from Keyword k" +
                " where k.urlSource.url = :urlName" +
                " and k.urlSource.member.userId = :userId" +
                " and k.word = :keyword", Keyword.class)
                .setParameter("urlName", urlName)
                .setParameter("userId", userId)
                .setParameter("keyword", keyword)
                .getResultList();


        result.stream().forEach(k->{
            UrlSource urlSource = k.getUrlSource();
            Keyword keywordToDelete = em.find(Keyword.class, k.getId());
            List<Keyword> keywords = urlSource.getKeywords();
            keywords.remove(keywordToDelete);
            urlSource.setKeywords(keywords);
            em.remove(keywordToDelete);
            em.persist(urlSource);
        });
    }
}
