package com.nhnacademy.day2toServlet.day2to3;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 현재 서버에 생성된 세션의 갯수를 로그로 출력
 */
@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener {
    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
        int sessionCounter = atomicInteger.incrementAndGet();
        log.error("session-counter:{}", sessionCounter);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
        int sessionCounter = atomicInteger.decrementAndGet();
        log.error("session-counter:{}", sessionCounter);
    }
}
