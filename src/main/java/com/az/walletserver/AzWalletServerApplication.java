package com.az.walletserver;


import com.az.walletserver.exceptions.RejectedExecutionHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
public class AzWalletServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AzWalletServerApplication.class, args);
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setQueueCapacity(800);
        threadPoolTaskExecutor.setThreadNamePrefix("threadPoolExecutor-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
