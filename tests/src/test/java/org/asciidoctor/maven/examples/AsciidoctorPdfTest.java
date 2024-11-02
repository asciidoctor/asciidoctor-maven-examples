package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.asciidoctor.maven.examples.common.CustomAsserter.assertFile;

@MavenTest(projectPath = "../asciidoctor-pdf-example")
class AsciidoctorPdfTest {

    private MavenProject mavenProject;

    @Test
    void shouldGeneratePdf() {
        File readmeJP = mavenProject.getTarget(generatedDocs("example-manual.pdf"));
        assertFile(readmeJP)
            .isPdf()
            .hasTitle("Example Manual");
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
