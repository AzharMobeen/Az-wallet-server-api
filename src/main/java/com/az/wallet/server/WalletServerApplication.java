package com.az.wallet.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
public class WalletServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletServerApplication.class, args);
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setQueueCapacity(500);
        threadPoolTaskExecutor.setThreadNamePrefix("threadPoolExecutor-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
