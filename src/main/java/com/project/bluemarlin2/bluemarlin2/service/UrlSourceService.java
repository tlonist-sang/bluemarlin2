package com.project.bluemarlin2.bluemarlin2.service;

import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlSourceService {
    private final UrlSourceRepository urlSourceRepository;

    public UrlSource findOne(Long id){
        return urlSourceRepository.findById(id);
    }
}
