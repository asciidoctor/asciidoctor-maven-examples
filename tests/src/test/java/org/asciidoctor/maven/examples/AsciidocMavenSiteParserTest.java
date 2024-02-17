package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-maven-site-parser-example", goal = "site:site")
class AsciidocMavenSiteParserTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateSitePages() {
        File index = mavenProject.getTarget(sitePage("index.html"));
        assertThat(index)
                .isNotEmpty()
                .content()
                .contains("<h1>AsciiDoc Maven Site Example</h1>")
                .contains("<li class=\"nav-header\">Asciidoctor Example</li>")
                .contains("<a href=\"hello.html\" title=\"Hello\"><span class=\"none\"></span>Hello</a>")
                .contains("<a href=\"article.html\" title=\"Article\"><span class=\"none\"></span>Article</a>");

        File hello = mavenProject.getTarget(sitePage("hello.html"));
        assertThat(hello)
                .isNotEmpty()
                .content()
                .contains("<h1>Hello, AsciiDoc!</h1>")
                .contains("<img src=\"images/tiger.png\" alt=\"Ghostscript Tiger\">")
                .contains("<h2><a name=\"Attributes\"></a>Attributes</h2>");

        File article = mavenProject.getTarget(sitePage("article.html"));
        assertThat(article)
                .isNotEmpty()
                .content()
                .contains("<h1>AsciiDoc is Writing Zen</h1>")
                .contains("<h3><a name=\"Unordered_list\"></a>Unordered list</h3>")
                .contains("<h3><a name=\"Ordered_list\"></a>Ordered list</h3>")
                .contains("<h3><a name=\"Table\"></a>Table</h3>")
                .contains("<h3><a name=\"Code_blocks\"></a>Code blocks</h3>")
                .contains("<h2><a name=\"Attributes\"></a>Attributes</h2>");
    }

    private String sitePage(String filename) {
        return "site/" + filename;
    }
}
