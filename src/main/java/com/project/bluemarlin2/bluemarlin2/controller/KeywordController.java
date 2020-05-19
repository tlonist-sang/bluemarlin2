package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.*;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.AddKeywordDto;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.DeleteKeywordDto;
import com.project.bluemarlin2.bluemarlin2.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/keyword")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @PutMapping
    public CustomResponse addKeyword(@RequestBody AddKeywordDto addKeywordDto){
        CustomResponse response = new CustomResponse();
        Long newKeywordId = keywordService.add(addKeywordDto);

        Keyword isExist = keywordService.findOne(newKeywordId);
        if(isExist != null){
            response.setStatus(ApiConstants.SUCCESS);
            response.setCount(1);
        }else {
            response.setStatus(ApiConstants.FAIL);
            response.setCount(1);
        }
        return response;
    }

    @DeleteMapping
    public CustomResponse deleteKeyword(@RequestBody DeleteKeywordDto deleteKeywordDto){
        String apiResult = keywordService.remove(deleteKeywordDto);
        return new CustomResponse(apiResult);
    }

}
