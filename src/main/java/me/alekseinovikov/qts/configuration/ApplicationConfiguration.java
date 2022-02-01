package me.alekseinovikov.qts.configuration;

import me.alekseinovikov.qts.processors.ConsumerProcessor;
import me.alekseinovikov.qts.processors.PrintProcessor;
import me.alekseinovikov.qts.processors.ProducerProcessor;
import me.alekseinovikov.qts.queue.TimestampQueue;
import me.alekseinovikov.qts.service.TimestampService;
import me.alekseinovikov.qts.tools.Printer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CommandLineArguments commandLineArguments(final ApplicationArguments applicationArguments) {
        var arguments = new CommandLineArguments();
        return CommandLine.populateCommand(arguments, applicationArguments.getSourceArgs());
    }

    @ConditionalOnExpression("#{commandLineArguments.printModeEnabled}")
    @Bean
    public PrintProcessor printProcessor(final Printer printer,
                                         final TimestampService timestampService) {
        return new PrintProcessor(printer, timestampService);
    }

    @ConditionalOnExpression("#{!commandLineArguments.printModeEnabled}")
    @Bean
    public ProducerProcessor producerProcessor(final TimestampQueue timestampQueue) {
        return new ProducerProcessor(timestampQueue);
    }

    @ConditionalOnExpression("#{!commandLineArguments.printModeEnabled}")
    @Bean
    public ConsumerProcessor consumerProcessor(final TimestampQueue timestampQueue,
                                               final SchedulerProps schedulerProps,
                                               final TimestampService timestampService) {
        return new ConsumerProcessor(timestampQueue, schedulerProps, timestampService);
    }

}
