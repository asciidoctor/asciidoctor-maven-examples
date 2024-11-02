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
        final Path output = createTempFile("mvn-error-", ".err");
        final File file = output.toFile();

        final ProcessBuilder processBuilder = new ProcessBuilder()
            .directory(workingDir)
            .command(command)
            .redirectError(file)
            .redirectOutput(file);

        try {
            int status = processBuilder.start().waitFor();
            throwIfError(status, output);
            return new ProcessResult(status, Files.readString(file.toPath()).trim());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwIfError(int value, Path errFile) throws IOException {
        if (value != 0) {
            String joinedCommand = String.join(" ", command);
            final String errorOutput = Files.readString(errFile);
            throw new RuntimeException(String.format("Command: [%s];\n%s", joinedCommand, errorOutput));
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
