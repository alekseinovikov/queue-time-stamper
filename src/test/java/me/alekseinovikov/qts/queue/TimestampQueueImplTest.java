package me.alekseinovikov.qts.queue;

import me.alekseinovikov.qts.persistence.model.Timestamp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TimestampQueueImplTest {

    @InjectMocks
    private TimestampQueueImpl timestampQueue;


    @DisplayName("We put and we get the messages in the same order")
    @Test
    void greenPath() {
        //arrange
        timestampQueue.put(timestamp(1));
        timestampQueue.put(timestamp(42));
        timestampQueue.put(timestamp(2));
        timestampQueue.put(timestamp(43));
        timestampQueue.put(timestamp(3));

        //act
        //assert
        assertThat(timestampQueue.get()).isEqualTo(timestamp(1));
        assertThat(timestampQueue.get()).isEqualTo(timestamp(42));
        assertThat(timestampQueue.get()).isEqualTo(timestamp(2));
        assertThat(timestampQueue.get()).isEqualTo(timestamp(43));
        assertThat(timestampQueue.get()).isEqualTo(timestamp(3));
    }

    @DisplayName("Thread is blocked if there is no message until it's present")
    @Test
    void checkBlocking() throws InterruptedException {
        //Put one and get one
        timestampQueue.put(timestamp(1));
        assertThat(timestampQueue.get()).isEqualTo(timestamp(1));

        //Now we run a thread to get the value
        var concurrentRunner = new Thread(() -> {
            assertThat(timestampQueue.get())
                    .isEqualTo(timestamp(2));
        });
        concurrentRunner.start();

        //We must force the scheduler to run our new thread and get it blocked
        Thread.sleep(100);

        //Make sure it's waiting for the value being blocked
        assertThat(concurrentRunner.getState())
                .isEqualTo(Thread.State.WAITING);

        timestampQueue.put(timestamp(2));

        //We must wait for the scheduler for unlocking our blocked thread
        Thread.sleep(100);

        //Make sure that the thread is not waiting anymore and may be scheduled for running
        assertThat(concurrentRunner.getState())
                .isNotEqualTo(Thread.State.WAITING);
    }


    private Timestamp timestamp(final long id) {
        var timestamp = new Timestamp();
        timestamp.setId(id);
        return timestamp;
    }
}