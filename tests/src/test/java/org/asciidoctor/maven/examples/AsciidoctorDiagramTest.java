package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoctor-diagram-example")
class AsciidoctorDiagramTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateHtmlWithDiagrams() {
        File htmlExampleManual = mavenProject.getTarget(generatedDocs("example-manual.html"));
        assertThat(htmlExampleManual)
                .isNotEmpty()
                .content()
                .contains("<meta name=\"generator\" content=\"Asciidoctor");

        File revealJsDistribution = mavenProject.getTarget(generatedDocs("images"));
        assertThat(revealJsDistribution)
                .isDirectory()
                .isDirectoryContaining(file -> file.getName().equals("asciidoctor-diagram-process.png"))
                .isDirectoryContaining(file -> file.getName().equals("auth-protocol.png"))
                .isDirectoryContaining(file -> file.getName().equals("dot-example.png"))
                .isDirectoryContaining(file -> file.getName().equals("aspectj-maven-multi-module.png"));
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
