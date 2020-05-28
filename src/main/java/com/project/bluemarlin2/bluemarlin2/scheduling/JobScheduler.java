package com.project.bluemarlin2.bluemarlin2.scheduling;

import com.project.bluemarlin2.bluemarlin2.config.ScheduleQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class JobScheduler {
    private static Logger logger = LoggerFactory.getLogger(EmailJob.class);
    private final Scheduler scheduler;
    private final ScheduleQueue scheduleQueue;

    @Bean
    public Queue<Long> getUrlSchedulingQueue(){
        return new LinkedList<>();
    }


    public void sendMails(Long id, String uuid, int mailingInterval){

        try {
            scheduleQueue.blocking().add(id);
            ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"));
            JobDetail jobDetail = buildJobDetail(uuid);
            Trigger trigger = buildJobTrigger(jobDetail, uuid, mailingInterval, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("Exception => " + e);
        }
    }

    public void stopSendingMails(String uuid){
        try {
            JobDetail jobDetail = buildJobDetail(uuid);
            scheduler.deleteJob(jobDetail.getKey());
        } catch (SchedulerException e) {
            logger.error("Exception => " + e);
        }
    }

    private JobDetail buildJobDetail(String uuid){
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(uuid, "email-jobs")
                .withDescription("build JobDetail")
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, String uuid, int mailingInterval, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(uuid, "email-jobs")
                .withDescription("build JobTrigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(mailingInterval).repeatForever())
                .build();
    }

}
