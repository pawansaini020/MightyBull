package com.pawan.MightyBull.services;

import com.pawan.MightyBull.WebClients.KickerxiWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AwakeService {

    @Autowired
    private KickerxiWebClient kickerxiWebClient;

    @Async
    public void awakeKickerxiServer() {
        kickerxiWebClient.awakeKickerxiServer();
    }

    @Async
    public void awakeKickerxiServer1() {
        kickerxiWebClient.awakeKickerxiServer();
    }
}
