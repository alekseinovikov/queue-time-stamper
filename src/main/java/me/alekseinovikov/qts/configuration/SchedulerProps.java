package me.alekseinovikov.qts.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ConfigurationProperties(prefix = "timestamps.scheduler")
@Component
public class SchedulerProps {
    private long producerRateMs = 1000L; //Produce timestamps every second
    private long consumerRetryOnFailMs = 5000L; //Default 5 secs for recovery
}

