package com.project.dotori.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1); // 스레드 풀에 항상 살아있는 최소 스레드 수
        executor.setMaxPoolSize(10); // 스레드 풀이 확장할 수 있는 최대 스레드 수
        executor.setQueueCapacity(10); // 스레드 풀에서 사용할 최대 큐의 크기
        executor.setThreadNamePrefix("DDAJA-ASYNC-"); // 생성된 각 스레드의 이름 접두사
        executor.setKeepAliveSeconds(60); // corePoolSize 초과인 상태에서, 대기 상태의 스레드가 종료되기까지의 대기 시간
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.warn("[Async Exception] method : {}\nparam : {}\n\nex : ", method, params, ex);
    }
}