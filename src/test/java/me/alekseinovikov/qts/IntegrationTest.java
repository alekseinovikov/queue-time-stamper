package me.alekseinovikov.qts;

import me.alekseinovikov.qts.persistence.repository.TimestampJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

//Do not run integration test if there is no docker on the machine
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private TimestampJpaRepository timestampJpaRepository;

    @Autowired
    private ConfigurableApplicationContext applicationContext;


    @DisplayName("Main integration test")
    @Test
    void integrationTest() throws InterruptedException {
        //Let's sleep for 5 secs
        Thread.sleep(5000L);

        //Now we expect to have at least 5 timestamps in DB
        assertThat(timestampJpaRepository.count())
                .isGreaterThanOrEqualTo(5L);

        //We must stop running as we are in the loop
        new Thread(() -> applicationContext.close())
                .start(); //It must be in a separated thread to not fail the test
    }

}
