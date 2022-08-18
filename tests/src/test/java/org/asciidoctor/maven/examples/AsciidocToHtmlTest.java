package org.asciidoctor.maven.examples;



import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoc-to-html-example")
class AsciidocToHtmlTest {

    private static Logger logger = LoggerFactory.getLogger(AsciidocToHtmlTest.class);

    private MavenProject mavenProject;

    @Test
    void shouldGenerateHtml() {
        logger.info(()-> "logging from test");
        logger.info(()-> "logging from test");
        logger.info(()-> "logging from test");
        logger.info(()-> "logging from test");
        logger.info(()-> "logging from test");
        logger.info(()-> "logging from test");

        File generatedDoc = mavenProject.getTarget("generated-docs/example-manual.html");

        assertThat(generatedDoc)
                .isNotEmpty()
                .content()
                .contains("<meta name=\"generator\" content=\"Asciidoctor")
                .contains("<pre class=\"rouge highlight\">")
                .contains("<em>src/docs/asciidoc/subdir/c.adoc</em>");
    }
}
