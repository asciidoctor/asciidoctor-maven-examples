package org.asciidoctor.maven.examples.tests;

class ProcessResult {

    private final int status;
    private final String output;

    ProcessResult(int status, String output) {
        this.status = status;
        this.output = output;
    }

    public int getStatus() {
        return status;
    }

    public String getOutput() {
        return output;
    }
}
