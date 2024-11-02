package org.asciidoctor.maven.examples;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.asciidoctor.maven.examples.common.CustomAsserter.assertFile;
import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoctor-pdf-with-theme-example")
class AsciidoctorPdfWithThemeTest {

    private static final String OUTPUT_PDF_FILE = "example-manual.pdf";

    private MavenProject mavenProject;

    @Test
    void shouldGeneratePdfWithCustomTheme() throws IOException {
        File pdfWithCustomTheme = mavenProject.getTarget(generatedDocsWithCustomTheme(OUTPUT_PDF_FILE));
        assertFile(pdfWithCustomTheme)
            .isPdf()
            .hasTitle("Example Manual");
        assertImagesInFirstPage(pdfWithCustomTheme, 1);
    }

    @Test
    void shouldGeneratePdfWithDefaultTheme() throws IOException {
        File pdfWithDefaultTheme = mavenProject.getTarget(generatedDocsWithDefaultTheme(OUTPUT_PDF_FILE));
        assertFile(pdfWithDefaultTheme)
            .isPdf()
            .hasTitle("Example Manual");
        assertImagesInFirstPage(pdfWithDefaultTheme, 0);
    }

    private void assertImagesInFirstPage(File pdfWithCustomTheme, int imagesCount) throws IOException {
        PDPage page = Loader
            .loadPDF(pdfWithCustomTheme)
            .getPage(0);
        assertThat(getImages(page)).hasSize(imagesCount);
    }

    private List<PDImage> getImages(PDPage page) throws IOException {
        final List<PDImage> images = new ArrayList<>();

        PDResources resources = page.getResources();
        for (COSName cosName : resources.getXObjectNames()) {
            PDXObject xObject = resources.getXObject(cosName);
            if (xObject instanceof PDImage) {
                images.add((PDImage) xObject);
            }
        }

        return images;
    }

    private String generatedDocsWithDefaultTheme(String filename) {
        return "generated-docs-default-theme/" + filename;
    }

    private String generatedDocsWithCustomTheme(String filename) {
        return "generated-docs-custom-theme/" + filename;
    }
}
