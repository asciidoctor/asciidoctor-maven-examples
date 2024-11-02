package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-to-revealjs-example")
class AsciidocToRevealjsTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateRevealJsSlides() {
        File slides = mavenProject.getTarget(generatedSlides("slides.html"));

        assertThat(slides)
            .isNotEmpty()
            .content()
            .doesNotContain("<meta name=\"generator\" content=\"Asciidoctor")
            .contains("<title>Example Manual</title>")
            .contains("<h2>Introduction</h2>")
            .contains("<h2>Speaker Notes</h2>")
            .contains("<h2>Source Code</h2>")
            .contains("<h2>Blank screen</h2>")
            .contains("<h2>Overview</h2>")
            .contains("<h2>Including documents from subdir</h2>")
            .contains("<h2>Images as background</h2>")
            .contains("<h2>Attributes</h2>");

        File sunsetImage = mavenProject.getTarget(generatedSlides("images/sunset.jpg"));
        assertThat(sunsetImage)
            .isNotEmpty();

        File revealJsDistribution = mavenProject.getTarget(generatedSlides("reveal.js-3.9.2"));
        assertThat(revealJsDistribution)
            .isDirectory()
            .isDirectoryContaining(file -> file.getName().equals("package.json"));
    }

    private String generatedSlides(String filename) {
        return "generated-slides/" + filename;
    }
}
