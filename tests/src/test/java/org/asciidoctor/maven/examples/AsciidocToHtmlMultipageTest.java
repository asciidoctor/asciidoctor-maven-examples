package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-to-html-multipage-example")
class AsciidocToHtmlMultipageTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateMultipleHtmlPages() {
        File index = mavenProject.getTarget(docs("index.html"));
        assertThat(index)
            .content()
            .contains("<meta name=\"generator\" content=\"Asciidoctor")
            // TOC
            .contains("<a href=\"introduction.html\">1. Introduction</a>")
            .contains("<a href=\"source-code.html\">2. Source Code</a>")
            .contains("<a href=\"images.html\">3. Images</a>")
            .contains("<a href=\"attributes.html\">4. Attributes</a>")
            .contains("<a href=\"includes.html\">5. Includes</a>")
            // Navigation footer
            .doesNotContain("Previous:")
            .contains("<div class=\"paragraph nav-footer\">")
            .contains("<p>Next:")
            .contains("<a href=\"introduction.html\">Introduction</a>");

        File introduction = mavenProject.getTarget(docs("introduction.html"));
        assertThat(introduction)
            .content()
            .doesNotContain("Previous:")
            .contains("Up:")
            .contains("<a href=\"index.html\">Example Manual</a>")
            .contains("Next:")
            .contains("<a href=\"source-code.html\">Source Code</a>");

        File sourceCode = mavenProject.getTarget(docs("source-code.html"));
        assertThat(sourceCode)
            .content()
            .contains("Previous:")
            .contains("<a href=\"introduction.html\">Introduction</a>")
            .contains("Up:")
            .contains("<a href=\"index.html\">Example Manual</a>")
            .contains("Next:")
            .contains("<a href=\"images.html\">Images</a>");

        File images = mavenProject.getTarget(docs("images.html"));
        assertThat(images)
            .content()
            .contains("Previous:")
            .contains("<a href=\"source-code.html\">Source Code</a>")
            .contains("Up:")
            .contains("<a href=\"index.html\">Example Manual</a>")
            .contains("Next:")
            .contains("<a href=\"attributes.html\">Attributes</a>");

        File attributes = mavenProject.getTarget(docs("attributes.html"));
        assertThat(attributes)
            .content()
            .contains("Previous:")
            .contains("<a href=\"images.html\">Images</a>")
            .contains("Up:")
            .contains("<a href=\"index.html\">Example Manual</a>")
            .contains("Next:")
            .contains("<a href=\"includes.html\">Includes</a>");

        File includes = mavenProject.getTarget(docs("includes.html"));
        assertThat(includes)
            .isNotEmpty()
            .content()
            .contains("Previous:")
            .contains("<a href=\"attributes.html\">Attributes</a>")
            .contains("Up:")
            .contains("<a href=\"index.html\">Example Manual</a>")
            .doesNotContain("Next:");
    }

    private String docs(String filename) {
        return "docs/multipage/" + filename;
    }
}
