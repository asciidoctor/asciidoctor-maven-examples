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
        // Since maven-site v3.20.0 'About' is added to title
        assertThat(index)
                .isNotEmpty()
                .content()
                .contains("<h1>About AsciiDoc Maven Site Example</h1>")
                .contains("<li class=\"nav-header\">Asciidoctor Example</li>")
                .contains("<li><a href=\"hello.html\">Hello</a></li>")
                .contains("<li><a href=\"article.html\">Article</a></li>");

        File hello = mavenProject.getTarget(sitePage("hello.html"));
        assertThat(hello)
                .isNotEmpty()
                .content()
                .contains("<h1>Hello, AsciiDoc!</h1>")
                .contains("<div><img src=\"images/tiger.png\" alt=\"Ghostscript Tiger\" /></div>")
                .contains("<h2><a id=\"attributes\"></a>Attributes</h2>");

        File article = mavenProject.getTarget(sitePage("article.html"));
        assertThat(article)
                .isNotEmpty()
                .content()
                .contains("<h1>AsciiDoc is Writing Zen</h1>")
                .contains("<h3><a id=\"unordered_list\"></a>Unordered list</h3>")
                .contains("<h3><a id=\"ordered_list\"></a>Ordered list</h3>")
                .contains("<h3><a id=\"table\"></a>Table</h3>")
                .contains("<h3><a id=\"code_blocks\"></a>Code blocks</h3>")
                .contains("<h2><a id=\"attributes\"></a>Attributes</h2>");
    }

    private static String sitePage(String filename) {
        return "site/" + filename;
    }
}
