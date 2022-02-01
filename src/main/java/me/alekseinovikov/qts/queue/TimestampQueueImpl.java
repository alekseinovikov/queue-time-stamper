package me.alekseinovikov.qts.queue;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.alekseinovikov.qts.persistence.model.Timestamp;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RequiredArgsConstructor

@Component
public class TimestampQueueImpl implements TimestampQueue {

    private final BlockingQueue<Timestamp> queue = new LinkedBlockingQueue<>();


    @SneakyThrows
    @Override
    public void put(Timestamp timestamp) {
        queue.put(timestamp);
    }

    @SneakyThrows
    @Override
    public Timestamp get() {
        return queue.take(); //Blocking call!!!
    }
}
