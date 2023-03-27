package com.nasnav.assessment.config;

import com.nasnav.assessment.util.EntityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfigs {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new EntityAuditorAware();
    }
}
