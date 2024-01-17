package com.karmanno.txexample;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.TransactionExecution;
import org.springframework.transaction.TransactionExecutionListener;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class DataJpaTxExample implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaTxExample.class, args);
    }

    @Bean
    @Primary
    public TransactionExecutionListener loggingListener() {
        return new TransactionExecutionListener() {
            @Override
            public void beforeBegin(@NotNull TransactionExecution transaction) {
                log.info("Before begin, new = {}", transaction.isNewTransaction());
            }

            @Override
            public void beforeCommit(@NotNull TransactionExecution transaction) {
                log.info("Before commit, new = {}", transaction.isNewTransaction());
            }
        };
    }

    @Autowired
    private SomeEntityRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("findAll");
        // opens a TX
        repository.findAll();

        log.info("save new");
        // opens a TX
        SomeEntity e = new SomeEntity();
        e.setId(UUID.randomUUID().toString());
        e.setTime(LocalDateTime.now());
        repository.save(e);

        log.info("update");
        // opens a TX
        e.setTime(LocalDateTime.now().plusMonths(1));
        repository.save(e);

        log.info("delete");
        // opens a TX
        repository.delete(e);

        log.info("dynamic");
        // doesn't open a TX
        repository.findAllByTimeBetween(LocalDateTime.now(), LocalDateTime.now().plusYears(1));

        log.info("JPQL");
        // doesn't open a TX
        repository.getByTimeBetween(LocalDateTime.now(), LocalDateTime.now().plusYears(1));
    }

}
