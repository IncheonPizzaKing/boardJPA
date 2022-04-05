package com.crowdsourcing.test;

import com.crowdsourcing.test.controller.TestDatabaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestListener implements ApplicationListener<ApplicationStartedEvent> {

    private final TestDatabaseController testDatabaseController;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        testDatabaseController.create();
    }
}
