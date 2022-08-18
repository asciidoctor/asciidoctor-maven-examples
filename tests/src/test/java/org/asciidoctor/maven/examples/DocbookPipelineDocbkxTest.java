package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.asciidoctor.maven.examples.common.CustomAsserter;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.asciidoctor.maven.examples.common.StringUtils.lines;
import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../docbook-pipeline-docbkx-example")
class DocbookPipelineDocbkxTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateDocbook() {
        File docbookFile = mavenProject.getTarget(generatedDocs("example-manual.xml"));
        assertThat(docbookFile)
                .isNotEmpty()
                .content()
                .startsWith(lines(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                        "<?asciidoc-toc?>",
                        "<?asciidoc-numbered?>",
                        "<book xmlns=\"http://docbook.org/ns/docbook\" xmlns:xl=\"http://www.w3.org/1999/xlink\" version=\"5.0\" xml:lang=\"en\">"));
    }

    @Test
    void shouldGeneratePdf() {
        File pdfFile = mavenProject.getTarget(generatedDocs("example-manual.pdf"));
        CustomAsserter.assertFile(pdfFile)
                .isPdf();
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
