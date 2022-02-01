package me.alekseinovikov.qts.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@RequiredArgsConstructor

@Component
public class PrinterImpl implements Printer {

    private static final PrintStream PRINT_STREAM = System.out;


    @Override
    public void println(String line) {
        PRINT_STREAM.println(line);
    }
}
