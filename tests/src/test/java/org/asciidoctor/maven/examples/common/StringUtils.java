package org.asciidoctor.maven.examples.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Applies OS specific linebreaks.
     */
    public static String lines(String... lines) {
        return Arrays.stream(lines)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
