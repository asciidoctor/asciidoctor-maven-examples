package org.asciidoctor.maven.examples;

import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoctor-epub-example")
class AsciidoctorEpubTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateEpubFile() {
        File epub = mavenProject.getTarget(generatedDocs("spine.epub"));

        assertThat(epub)
            .isNotEmpty()
            .isFile();
    }

    @Test
    void shouldGenerateExtractedDir() {
        File extractedDir = mavenProject.getTarget(generatedDocs("spine"));

        assertThat(extractedDir)
            .isDirectory()
            .isDirectoryContaining(directory("EPUB"))
            .isDirectoryContaining(directory("META-INF"))
            .isDirectoryContaining(file("mimetype"));

        assertThat(new File(extractedDir, "EPUB"))
            .isDirectory()
            .isDirectoryContaining(file("_chapter_1.xhtml"))
            .isDirectoryContaining(file("_chapter_2.xhtml"))
            .isDirectoryContaining(file("_chapter_3.xhtml"));

        assertThat(new File(extractedDir, "EPUB/images"))
            .isDirectory()
            .isDirectoryContaining(file("sunset.jpg"));

        extractedDir.toPath().resolve("");
    }

    private static Predicate<File> directory(String name) {
        return file -> file.isDirectory() && file.getName().equals(name);
    }

    private static Predicate<File> file(String name) {
        return file -> file.isFile() && file.getName().equals(name) && file.length() > 0;
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
