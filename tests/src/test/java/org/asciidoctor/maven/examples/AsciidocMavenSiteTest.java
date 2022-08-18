package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-maven-site-example", goal = "site:site")
class AsciidocMavenSiteTest {

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
                .contains("<img src=\"images/tiger.png\" alt=\"Ghostscript Tiger\"/>")
                .contains("<h2 id=\"attributes\">Attributes</h2>");

        File article = mavenProject.getTarget(sitePage("article.html"));
        assertThat(article)
                .isNotEmpty()
                .content()
                .contains("<h1>AsciiDoc is Writing Zen</h1>")
                .contains("<h3 id=\"unordered_list\">")
                .contains("<h3 id=\"ordered_list\">")
                .contains("<h3 id=\"table\">")
                .contains("<h3 id=\"code_blocks\">")
                .contains("<h2 id=\"attributes\"><a class=\"anchor\" href=\"#attributes\"></a>Attributes</h2>");
    }

    private String sitePage(String filename) {
        return "site/" + filename;
    }
}
