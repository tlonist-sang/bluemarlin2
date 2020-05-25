package com.project.bluemarlin2.bluemarlin2.service;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.AddKeywordDto;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.DeleteKeywordDto;
import com.project.bluemarlin2.bluemarlin2.repository.KeywordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordJpaRepository keywordRepository;

    public void save(Keyword keyword){
        keywordRepository.save(keyword);
    }

    public Keyword findOne(Long id){
        Keyword keyword = keywordRepository.findOne(id);
        return keyword;
    }

    public void remove(Long id){
        keywordRepository.remove(id);
    }

    public String remove(DeleteKeywordDto deleteKeywordDto){
        Long removedId = keywordRepository.remove(deleteKeywordDto);
        if(keywordExists(removedId))
            return ApiConstants.FAIL;
        return ApiConstants.SUCCESS;
    }

    public Long add(AddKeywordDto addKeywordDto){
        return keywordRepository.add(addKeywordDto);
    }

    private boolean keywordExists(Long keywordId) {
        Keyword keyword = keywordRepository.findOne(keywordId);
        if(keyword != null)
            return true;
        return false;
    }
}
