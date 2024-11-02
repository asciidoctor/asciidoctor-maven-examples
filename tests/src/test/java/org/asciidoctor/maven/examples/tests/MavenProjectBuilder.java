package org.asciidoctor.maven.examples.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MavenProjectBuilder {

    private File path;
    /**
     * Equivalent to '-X,--debug'
     */
    private boolean debug;

    private String goal;
    private File repository;

    /**
     * Equivalent to '-f,--file'
     */
    public MavenProjectBuilder path(File path) {
        this.path = path;
        return this;
    }

    public MavenProjectBuilder goal(String goal) {
        this.goal = goal;
        return this;
    }

    public MavenProjectBuilder repository(File repository) {
        this.repository = repository;
        return this;
    }

    public int run() {
        if (path == null) {
            throw new RuntimeException("path cannot be null");
        }
        if (repository != null && !repository.exists()) {
            throw new RuntimeException("repository does not exists: " + repository);
        }
        return new ProcessRunner(path.getAbsoluteFile(), buildCommand())
            .run()
            .getStatus();
    }

    private List<String> buildCommand() {
        final List<String> parts = new ArrayList<>();
        File executable = new MavenLocator()
            .baseDirectory(path.getAbsolutePath())
            .findExecutable();
        parts.add(executable.isAbsolute() ? executable.getAbsolutePath() : executable.getName());
        parts.add("-B");
        parts.add("--file");
        parts.add(path.getAbsolutePath());
        if (debug) {
            parts.add("--debug");
        }
        if (repository != null) {
            parts.add("--define");
            parts.add("maven.repo.local=" + repository.getAbsolutePath());
        }
        if (goal != null && goal.length() > 0) {
            parts.add(goal);
        }
        return parts;
    }

    // TODO Find way to change build dir from terminal. Try using '-Dproject.build.directory' does not work
//    public MavenProjectBuilder targetPath(File target) {
//        this.target = target;
//        return this;
//    }

}
