package com.example.tlover.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Scheduled(cron = "3 * * * * *") // 매월 15일 오전 10시 15분에 실행
    // @Scheduled(cron = "0 15 10 15 11 ?") // 11월 15일 오전 10시 15분에 실행
    // @Scheduled(cron = "${cron.expression}")
    // @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
    // timezone 설정
    public void scheduleTaskUsingCronExpression() {
        System.out.println("현재 시간은 " + new Date());
    }


}
