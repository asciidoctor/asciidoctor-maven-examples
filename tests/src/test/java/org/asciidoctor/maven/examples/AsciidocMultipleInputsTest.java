package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.asciidoctor.maven.examples.common.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-multiple-inputs-example")
class AsciidocMultipleInputsTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateHtml() {
        File html = mavenProject.getTarget(generatedDoc("example-technical-doc.html"));
        assertThat(html)
                .isNotEmpty()
                .content()
                .contains("<meta name=\"generator\" content=\"Asciidoctor")
                .contains(StringUtils.lines("<ul class=\"sectlevel1\">",
                        "<li><a href=\"#chapter-1\">Chapter 1</a></li>",
                        "<li><a href=\"#chapter-2\">Chapter 2</a></li>",
                        "</ul>"))
                .contains("<span id=\"author\" class=\"author\">Doc Writer</span>")
                .contains("<h2 id=\"chapter-1\"><a class=\"anchor\" href=\"#chapter-1\"></a>Chapter 1</h2>")
                .contains("<h2 id=\"chapter-2\"><a class=\"anchor\" href=\"#chapter-2\"></a>Chapter 2</h2>");
    }

    @Test
    void shouldGeneratePdf() {
        File pdf = mavenProject.getTarget(generatedDoc("example-manual.pdf"));
        assertThat(pdf)
                .isNotEmpty()
                .content()
                .startsWith("%PDF-1.4")
                .contains("/Title (Example Manual)")
                .contains("/Author (Doc Writer)")
                .contains("/Creator (Asciidoctor PDF");
    }

    private String generatedDoc(String filename) {
        return "generated-docs/" + filename;
    }
}
