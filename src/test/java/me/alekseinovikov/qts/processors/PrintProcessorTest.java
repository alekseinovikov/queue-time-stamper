package me.alekseinovikov.qts.processors;

import me.alekseinovikov.qts.persistence.model.Timestamp;
import me.alekseinovikov.qts.service.TimestampService;
import me.alekseinovikov.qts.tools.Printer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrintProcessorTest {

    @InjectMocks
    private PrintProcessor printProcessor;

    @Mock
    private Printer printer;

    @Mock
    private TimestampService timestampService;


    @DisplayName("Prints hello and toString for every timestamp returned")
    @Test
    void runGreenPath() throws Exception {
        //arrange
        var timestamps = List.of(
                mockTimestampWithToString("1"),
                mockTimestampWithToString("2"),
                mockTimestampWithToString("3")
        );
        when(timestampService.findAll())
                .thenReturn(timestamps);

        //act
        printProcessor.run();

        //assert
        verify(printer, times(1))
                .println("PRINTING TIMESTAMPS FROM DB:");
        verify(printer, times(1))
                .println("1");
        verify(printer, times(1))
                .println("2");
        verify(printer, times(1))
                .println("3");
    }

    @DisplayName("Returns 0 exit code by default")
    @Test
    void getExitCodeGreenPath() {
        //arrange
        //act
        //assert
        assertThat(printProcessor.getExitCode())
                .isEqualTo(0);
    }

    @DisplayName("Returns -1 exit code on exception and eats the exception")
    @Test
    void getExitCodeRedPath() {
        //arrange
        doThrow(new RuntimeException("Test exception"))
                .when(timestampService).findAll();

        //act
        //assert
        assertThatCode(() -> printProcessor.run())
                .doesNotThrowAnyException();
        assertThat(printProcessor.getExitCode())
                .isEqualTo(-1);
    }

    private Timestamp mockTimestampWithToString(final String toStringValue) {
        var timestamp = mock(Timestamp.class);
        when(timestamp.toString())
                .thenReturn(toStringValue);
        return timestamp;
    }
}