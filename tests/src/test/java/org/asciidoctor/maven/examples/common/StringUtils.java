package org.asciidoctor.maven.examples.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Fixed in AsciidoctorJ 2.5.12, always uses '\n' for all OSs.
     */
    public static String lines(String... lines) {
        return Arrays.stream(lines)
            .collect(Collectors.joining("\n"));
    }
}
