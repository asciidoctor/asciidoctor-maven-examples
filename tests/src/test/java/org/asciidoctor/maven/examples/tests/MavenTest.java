package org.asciidoctor.maven.examples.tests;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(JUnitMavenRunnerExtension.class)
public @interface MavenTest {

    String projectPath();

    String goal() default "";
}
