package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("issue-141")
@MavenTest(projectPath = "../asciidoctor-epub-example")
class AsciidoctorEpubTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateEpubFiles() {
        File epub = mavenProject.getTarget(generatedDocs("spine.epub"));
        assertThat(epub)
            .isNotEmpty();

        File epubKf8 = mavenProject.getTarget(generatedDocs("spine-kf8.epub"));
        assertThat(epubKf8)
            .isNotEmpty();
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
