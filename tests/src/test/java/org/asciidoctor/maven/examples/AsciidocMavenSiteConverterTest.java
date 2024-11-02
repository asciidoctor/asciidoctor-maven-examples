package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-maven-site-converter-example", goal = "site:site")
class AsciidocMavenSiteConverterTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateSitePages() {
        File index = mavenProject.getTarget(sitePage("index.html", ""));
        // Since maven-site v3.20.0 'About' is added to title
        assertThat(index)
            .isNotEmpty()
            .content()
            .contains("<link rel=\"stylesheet\" href=\"./css/site.css\" />")
            .contains("<h1>About AsciiDoc Maven Site Example</h1>")
            .contains("<li class=\"nav-header\">Asciidoctor Example</li>")
            .contains("<li><a href=\"hello.html\">Hello</a></li>")
            .contains("<li><a href=\"article.html\">Article</a></li>");

        File hello = mavenProject.getTarget(sitePage("hello.html", ""));
        assertThat(hello)
            .isNotEmpty()
            .content()
            .contains("<h1>Hello, AsciiDoc!</h1>")
            .contains("<img src=\"images/tiger.png\" alt=\"Ghostscript Tiger\"/>")
            .contains("<h2 id=\"attributes\">Attributes</h2>");

        File article = mavenProject.getTarget(sitePage("article.html", ""));
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

    private static String sitePage(String filename, String locale) {
        return String.format("site/%s/%s", locale, filename);
    }
}
