package org.asciidoctor.maven.examples.tests;

import java.io.File;

public class MavenProject {

    private final File projectPath;

    public MavenProject(File projectPath) {
        this.projectPath = projectPath;
    }

    public File getTarget() {
        return new File(projectPath, "target");
    }

    public File getTarget(String path) {
        return new File(projectPath, String.format("target/%s", path));
    }
}
