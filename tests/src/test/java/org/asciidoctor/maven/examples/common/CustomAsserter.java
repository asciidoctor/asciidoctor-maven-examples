package org.asciidoctor.maven.examples.common;

import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;

import java.io.File;

public class CustomAsserter {

    public static PdfAsserter assertFile(File pdfFile) {
        return new PdfAsserter(pdfFile);
    }

    public static class PdfAsserter {

        private final AbstractStringAssert<?> content;

        public PdfAsserter(File file) {
            this.content = Assertions.assertThat(file).content();
        }

        public PdfAsserter isPdf() {
            content.startsWith("%PDF-1.4");
            return this;
        }

        public void hasTitle(String title) {
            content.contains(String.format("/Title (%s)", title));
        }
    }
}
