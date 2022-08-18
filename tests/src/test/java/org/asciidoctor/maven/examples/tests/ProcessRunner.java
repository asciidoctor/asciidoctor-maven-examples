package org.asciidoctor.maven.examples.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProcessRunner {

    private final File workingDir;
    private final List<String> command;

    public ProcessRunner(File workingDir, List<String> command) {
        this.workingDir = workingDir;
        this.command = command;
    }

    public int run() {
        final ProcessBuilder processBuilder = new ProcessBuilder()
                .directory(workingDir)
                .command(command)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            return processBuilder
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
