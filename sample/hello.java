/* Copyright (c) 2025 Black Duck Software, Inc. All rights reserved worldwide. */
package com.blackduck.polaris.assessment;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableAsync
@Slf4j
@EnableCaching
public class AssessmentCenterApplication {
    public static final String MIGRATION_PROFILE = "migration";

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(AssessmentCenterApplication.class, args);

        /*
         * Shutdown application after running liquibase in case of migration profile is
         * active
         */
        if (isMigrationProfile(applicationContext.getEnvironment())) {
            log.info("************************************************************");
            log.info("*********** Shutdown initiated, migration completed ********");
            log.info("************************************************************");
            System.exit(SpringApplication.exit(applicationContext, () -> 0));
        }
    }

    @PostConstruct
    private void postConstruct() {
        log.info("***** Application context construction completed *****");
    }

    static boolean isMigrationProfile(ConfigurableEnvironment environment) {
        return Arrays.asList(environment.getActiveProfiles()).contains(MIGRATION_PROFILE);
    }
}
