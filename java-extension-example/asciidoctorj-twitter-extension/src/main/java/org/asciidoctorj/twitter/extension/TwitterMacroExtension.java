package org.asciidoctorj.twitter.extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.extension.spi.ExtensionRegistry;

public class TwitterMacroExtension implements ExtensionRegistry {

    public void register(Asciidoctor asciidoctor) {
        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();

        javaExtensionRegistry.inlineMacro("twitter", TwitterMacro.class);
    }
}
