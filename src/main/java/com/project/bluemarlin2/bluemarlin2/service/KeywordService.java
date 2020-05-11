package com.project.bluemarlin2.bluemarlin2.service;

import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.DeleteKeywordDto;
import com.project.bluemarlin2.bluemarlin2.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

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

    public void remove(DeleteKeywordDto deleteKeywordDto){
        keywordRepository.remove(deleteKeywordDto);
    }
}
