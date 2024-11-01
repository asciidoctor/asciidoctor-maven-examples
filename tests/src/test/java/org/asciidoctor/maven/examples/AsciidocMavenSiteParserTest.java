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
                .contains("<h2>Hello, AsciiDoc!</h2>")
                .contains("<div><img src=\"images/tiger.png\" alt=\"Ghostscript Tiger\" /></div>")
                .contains("<h3><a name=\"attributes\"></a>Attributes</h3>");

        File article = mavenProject.getTarget(sitePage("article.html"));
        assertThat(article)
                .isNotEmpty()
                .content()
                .contains("<h2>AsciiDoc is Writing Zen</h2>")
                .contains("<h4><a name=\"unordered_list\"></a>Unordered list</h4>")
                .contains("<h4><a name=\"unordered_list\"></a>Unordered list</h4>")
                .contains("<h4><a name=\"table\"></a>Table</h4>")
                .contains("<h4><a name=\"code_blocks\"></a>Code blocks</h4>")
                .contains("<h3><a name=\"attributes\"></a>Attributes</h3>");
    }

    private String sitePage(String filename) {
        return "site/" + filename;
    }
}
