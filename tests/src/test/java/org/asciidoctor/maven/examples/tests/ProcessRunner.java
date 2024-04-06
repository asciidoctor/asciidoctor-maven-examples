package org.asciidoctor.maven.examples.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProcessRunner {

    private final File workingDir;
    private final List<String> command;

    public ProcessRunner(File workingDir, List<String> command) {
        this.workingDir = workingDir;
        this.command = command;
    }

    public ProcessResult run() {
        final Path errFile = createTempFile("mvn-error-", ".err");
        final Path outputRedirect = createTempFile("mvn-output-", ".out");

        final ProcessBuilder processBuilder = new ProcessBuilder()
                .directory(workingDir)
                .command(command)
                .redirectError(errFile.toFile())
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            int status = processBuilder.start().waitFor();
            throwIfError(status, errFile);
            return new ProcessResult(status, Files.readString(outputRedirect).trim());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwIfError(int value, Path errFile) throws IOException {
        if (value != 0) {
            String joinedCommand = String.join(" ", command);
            final String errorOutput = Files.readString(errFile);
            throw new RuntimeException(String.format("Command: [%s]", joinedCommand + "\n" + errorOutput));
        }
        errFile.toFile().deleteOnExit();
    }

    private Path createTempFile(String prefix, String suffix) {
        try {
            return Files.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new RuntimeException("Could not create temp file", e);
        }
    }
}
