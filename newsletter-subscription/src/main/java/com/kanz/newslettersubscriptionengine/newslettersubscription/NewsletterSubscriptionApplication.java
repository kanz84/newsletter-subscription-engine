package com.kanz.newslettersubscriptionengine.newslettersubscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class NewsletterSubscriptionApplication {

    private static final Logger logger = LoggerFactory.getLogger(NewsletterSubscriptionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NewsletterSubscriptionApplication.class, args);
        logger.info("newsletter-subscription app is up and running...");
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("Admin");
    }
}
