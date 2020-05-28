package com.project.bluemarlin2.bluemarlin2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class ScheduleQueue {
    @Bean
    public BlockingQueue<Long> blocking(){
        return new LinkedBlockingQueue<>();
    }
}
