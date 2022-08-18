package org.asciidoctor.maven.examples.tests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JUnitMavenRunnerExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        final Field mavenProjectField = findMavenProjectField(testInstance);

        if (mavenProjectField != null) {
            injectMavenProject(testInstance, mavenProjectField);
        }
    }

    private Field findMavenProjectField(Object testInstance) {
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (MavenProject.class.equals(field.getType())) return field;
        }
        return null;
    }

    private void injectMavenProject(Object testInstance, Field mavenProjectField) throws IllegalAccessException {
        String pathname = extractProjectPath(testInstance);
        final File projectPath = new File(pathname);

        int run = new MavenProjectBuilder()
                .path(projectPath)
                .goal(extractAnnotation(testInstance).goal())
                .run();

        if (run == 0) {
            final MavenProject target = new MavenProject(projectPath);
            if (Modifier.isPrivate(mavenProjectField.getModifiers())) {
                mavenProjectField.setAccessible(true);
                mavenProjectField.set(testInstance, target);
                mavenProjectField.setAccessible(false);
            } else {
                mavenProjectField.set(testInstance, target);
            }
        } else {
            throw new RuntimeException("Could not run Maven project");
        }
    }

    private String extractProjectPath(Object testInstance) {
        return extractAnnotation(testInstance)
                .projectPath();
    }

    private MavenTest extractAnnotation(Object testInstance) {
        return testInstance
                .getClass()
                .getAnnotation(MavenTest.class);
    }
}
