package com.project.bluemarlin2.bluemarlin2.service;

import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlSourceService {
    private final UrlSourceJpaRepository urlSourceRepository;

    public UrlSource findOne(Long id){
        return urlSourceRepository.findById(id);
    }
}
