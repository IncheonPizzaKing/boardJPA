package com.crowdsourcing.test;

import com.crowdsourcing.test.controller.TestDatabaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class TestListener implements ApplicationListener<ApplicationStartedEvent> {

    private final TestDatabaseController testDatabaseController;

    /**
     * 실행시 테스트 데이터 들을 주입
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        testDatabaseController.create();
    }
}
