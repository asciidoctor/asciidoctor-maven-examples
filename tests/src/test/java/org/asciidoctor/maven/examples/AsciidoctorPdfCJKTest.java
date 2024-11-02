package org.asciidoctor.maven.examples;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.asciidoctor.maven.examples.common.CustomAsserter;
import org.asciidoctor.maven.examples.tests.MavenProject;
import org.asciidoctor.maven.examples.tests.MavenTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MavenTest(projectPath = "../asciidoctor-pdf-cjk-example")
class AsciidoctorPdfCJKTest {

    private MavenProject mavenProject;

    @Test
    void shouldGenerateCnPdf() throws IOException {
        File readmeCN = mavenProject.getTarget(generatedDocs("README-zh_CN.pdf"));
        assertIsPdfAndContainsFont(readmeCN, "KaiGenGothicCN");
    }

    @Test
    void shouldGenerateJpPdf() throws IOException {
        File readmeJP = mavenProject.getTarget(generatedDocs("README-jp.pdf"));
        assertIsPdfAndContainsFont(readmeJP, "KaiGenGothicJP");
    }

    private void assertIsPdfAndContainsFont(File readmeJP, String fontName) throws IOException {
        CustomAsserter.assertFile(readmeJP)
            .isPdf()
            .hasTitle("Asciidoctor");
        assertThat(extractFonts(Loader.loadPDF(readmeJP)))
            .contains(fontName);
    }

    private Set<String> extractFonts(PDDocument pdf) throws IOException {
        final Set<PDFont> fonts = new HashSet<>();

        final Set<String> fontNamesClean = new HashSet<>();
        for (int i = 0; i < pdf.getNumberOfPages(); i++) {
            PDPage page = pdf.getPage(i);
            PDResources resources = page.getResources();
            for (COSName fontName : resources.getFontNames()) {
                // PDFBox prints (but not throws) exception on missing fonts
                PDFont font = resources.getFont(fontName);
                if (font != null) {
                    // Some fonts appear duplicated with composed names
                    fonts.add(font);
                    if (font.getName().contains("+"))
                        fontNamesClean.add(font.getName().split("\\+")[1]);
                    else
                        fontNamesClean.add(font.getName());
                }
            }
        }
        return fontNamesClean;
    }

    private String generatedDocs(String filename) {
        return "generated-docs/" + filename;
    }
}
