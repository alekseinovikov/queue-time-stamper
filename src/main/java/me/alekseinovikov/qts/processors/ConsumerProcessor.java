package me.alekseinovikov.qts.processors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.alekseinovikov.qts.configuration.SchedulerProps;
import me.alekseinovikov.qts.persistence.model.Timestamp;
import me.alekseinovikov.qts.queue.TimestampQueue;
import me.alekseinovikov.qts.service.TimestampService;
import org.springframework.boot.CommandLineRunner;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ConsumerProcessor implements CommandLineRunner {

    private final TimestampQueue timestampQueue;
    private final SchedulerProps schedulerProps;
    private final TimestampService timestampService;

    private Timestamp currentTimestamp = null;


    @Override
    public void run(String... args) throws Exception {
        new Thread(this::process) //We start the consumer in a separated thread to release the main one
                .start();
    }

    @SneakyThrows
    private void process() {
        //We process in infinite loop
        while (true) {
            try {
                //We keep current timestamp from the queue for processing
                if (Objects.isNull(currentTimestamp)) {
                    //Get it for processing
                    currentTimestamp = timestampQueue.get();
                }

                log.info("Consuming new timestamp: {}", currentTimestamp);
                timestampService.save(currentTimestamp);

                //We clear it only if save process was successful
                currentTimestamp = null;
            } catch (Exception ex) {
                log.error("Exception on consuming timestamp: ", ex);
                log.info("Waiting for {} ms for retrying!", schedulerProps.getConsumerRetryOnFailMs());

                Thread.sleep(schedulerProps.getConsumerRetryOnFailMs());
            }
        }
    }
}
