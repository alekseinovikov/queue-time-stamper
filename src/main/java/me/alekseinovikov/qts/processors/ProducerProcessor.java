package me.alekseinovikov.qts.processors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alekseinovikov.qts.persistence.model.Timestamp;
import me.alekseinovikov.qts.queue.TimestampQueue;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ProducerProcessor {

    private final TimestampQueue timestampQueue;


    @Scheduled(fixedRateString = "#{schedulerProps.producerRateMs}", timeUnit = TimeUnit.MILLISECONDS)
    public void produce() {
        final Timestamp timestamp = createTimestamp();

        log.info("Producing new timestamp: {}", timestamp);

        timestampQueue.put(timestamp);
    }

    private Timestamp createTimestamp() {
        return new Timestamp(
                null,
                UUID.randomUUID().toString(),
                ZonedDateTime.now());
    }

}
