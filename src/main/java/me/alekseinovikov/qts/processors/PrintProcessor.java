package me.alekseinovikov.qts.processors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alekseinovikov.qts.service.TimestampService;
import me.alekseinovikov.qts.tools.Printer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;

@Slf4j
@RequiredArgsConstructor
public class PrintProcessor implements CommandLineRunner, ExitCodeGenerator {

    private final Printer printer;
    private final TimestampService timestampService;

    private int exitCode = 0;


    @Override
    public void run(String... args) throws Exception {
        try {
            printer.println("PRINTING TIMESTAMPS FROM DB:");
            timestampService.findAll()
                    .forEach(record -> printer.println(record.toString()));
        } catch (Exception ex) {
            log.error("Error on printing data from DB: ", ex);
            exitCode = -1;
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
