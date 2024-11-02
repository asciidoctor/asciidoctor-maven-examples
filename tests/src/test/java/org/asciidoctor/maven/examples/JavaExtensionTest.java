package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = JavaExtensionTest.PROJECT_PATH)
class JavaExtensionTest {

    public static final String PROJECT_PATH = "../java-extension-example";

    private MavenProject mavenProject;

    @Test
    void shouldGeneratePdfWithCustomTheme() {
        File outputDirectory = new File(
            new File(PROJECT_PATH, "asciidoctor-with-extension-example"),
            "target/generated-docs");

        File htmlWithExtension = new File(outputDirectory, "example-manual.html");
        assertThat(htmlWithExtension)
            .isNotEmpty()
            .content()
            .contains("<a href=\"https://www.twitter.com/mrhaki\">@mrhaki</a>");
    }
}
