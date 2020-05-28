package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.annotation.LoginUser;
import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceRepository;
import com.project.bluemarlin2.bluemarlin2.scheduling.JobScheduler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/v1/schedule")
public class ScheduleController {
    private final UrlSourceRepository urlSourceRepository;
    private final JobScheduler jobScheduler;

    @GetMapping
    public CustomResponse<Boolean> getScheduling(Long urlId){
        Optional<UrlSource> byId = urlSourceRepository.findById(urlId);
        if(byId.isPresent()){
            UrlSource urlSource = byId.get();
            return new CustomResponse<>(ApiConstants.SUCCESS, urlSource.getIsScheduling());
        }
        return new CustomResponse<>(ApiConstants.FAIL, null);
    }

    @PostMapping
    public CustomResponse updateScheduling(@LoginUser Member member, @RequestBody ScheduleDto scheduleDto){
        Optional<UrlSource> byId = urlSourceRepository.findById(scheduleDto.getUrlId());
        if(byId.isPresent()){
            UrlSource urlSource = byId.get();
            urlSource.setIsScheduling(scheduleDto.getIsScheduling());
            if(scheduleDto.getIsScheduling()){
                if(urlSource.getScheduleId()==null){
                    urlSource.setScheduleId(UUID.randomUUID().toString());
                }
                jobScheduler.sendMails(urlSource.getId(), urlSource.getScheduleId(), urlSource.getMailingInterval());
            }else{
                jobScheduler.stopSendingMails(urlSource.getId(), urlSource.getScheduleId());
            }

            urlSourceRepository.save(urlSource);
            return new CustomResponse(ApiConstants.SUCCESS);
        }
        return new CustomResponse(ApiConstants.FAIL);
    }


    @Data
    static class ScheduleDto{
        public ScheduleDto(Boolean isScheduling, Long urlId) {
            this.isScheduling = isScheduling;
            this.urlId = urlId;
        }
        private Boolean isScheduling;
        private Long urlId;
    }
}
