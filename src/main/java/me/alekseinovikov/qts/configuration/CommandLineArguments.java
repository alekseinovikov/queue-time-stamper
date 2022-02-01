package me.alekseinovikov.qts.configuration;

import lombok.Data;
import picocli.CommandLine;

@Data
public class CommandLineArguments {

    @CommandLine.Option(names = "-p", description = "Print all data from the storage", required = false)
    private boolean isPrintModeEnabled;
    
}
