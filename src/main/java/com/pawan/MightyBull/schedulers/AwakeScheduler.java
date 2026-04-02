package com.pawan.MightyBull.schedulers;

import com.pawan.MightyBull.services.AwakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "kickerxi.awake.scheduler.enabled", havingValue = "true")
public class AwakeScheduler {

    @Autowired
    private AwakeService awakeService;

    @Scheduled(fixedDelayString = "${kickerxi.awake.scheduler.poll-interval-ms:600000}")
    public void tick() {
        awakeService.awakeMightBullServer();
        log.info("Successfully awake mightybull server.");
    }
}
