package org.asciidoctor.maven.examples.tests;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

// Inspired on org.apache.maven.shared.invoker.MavenCommandLineBuilder
// from maven-invoker-plugin
public class MavenLocator {

    private static Logger logger = LoggerFactory.getLogger(MavenLocator.class);

    private static final Pattern WINDOWS_NAME_PATTERN = Pattern.compile("^[w|W]in.*$");
    private static final String MVN_BIN = "/bin/mvn";
    private static final String SDKMAN_DEFAULT_MAVEN_PATH = ".sdkman/candidates/maven/current/";

    private File baseDirectory;
    private File mavenHome;
    private final File sdkmanDirectory = new File(System.getProperty("user.home"), SDKMAN_DEFAULT_MAVEN_PATH);

    private File mavenExecutable;

    private void setupMavenHome() {

        String candidateJavaHome = System.getProperty("maven.home");
        logger.info(() -> "System.getProperty(\"maven.home\"): " + System.getProperty("maven.home"));
        logger.info(() -> "System.getenv(\"MAVEN_HOME\"): " + System.getenv("MAVEN_HOME"));
        if (candidateJavaHome == null) {
            candidateJavaHome = System.getenv("MAVEN_HOME");
        }
        if (candidateJavaHome != null) {
            this.mavenHome = new File(candidateJavaHome);
        }

        if (this.mavenHome != null && !this.mavenHome.isDirectory()) {
            File binDir = this.mavenHome.getParentFile();
            if (binDir != null && "bin".equals(binDir.getName())) {
                this.mavenHome = binDir.getParentFile();
            }
        }

        if (this.mavenHome != null && !this.mavenHome.isDirectory()) {
            throw new IllegalStateException("Maven home is set to: '" + this.mavenHome + "' which is not a directory");
        } else {
            logger.info(() -> "Using maven.home: '" + this.mavenHome + "'.");
        }
    }

    private void setupMavenExecutable() {

        if (this.mavenExecutable == null) {
            this.mavenExecutable = this.detectMavenExecutablePerOs(this.mavenHome, MVN_BIN);
        }
        if (this.mavenExecutable == null) {
            this.mavenExecutable = this.detectMavenExecutablePerOs(this.baseDirectory, MVN_BIN);
        }
        if (this.mavenExecutable == null) {
            this.mavenExecutable = this.detectMavenExecutablePerOs(this.sdkmanDirectory, MVN_BIN);
        }

        if (this.mavenExecutable == null) {
            logger.info(() -> "Could not locate maven executable, attempting PATH");
            this.mavenExecutable = new File("mvn");
        } else {
            try {
                this.mavenExecutable = this.mavenExecutable.getCanonicalFile();
            } catch (IOException var4) {
                logger.debug(() -> String.format("Failed to canonicalize maven executable: '%s'. Using as-is.", this.mavenExecutable));
            }
        }
    }

    private File detectMavenExecutablePerOs(File baseDirectory, String executable) {
        File executableFile;
        if (isWindows()) {
            executableFile = new File(baseDirectory, executable + ".cmd");
            if (executableFile.isFile()) {
                return executableFile;
            }

            executableFile = new File(baseDirectory, executable + ".bat");
            if (executableFile.isFile()) {
                return executableFile;
            }
        }

        executableFile = new File(baseDirectory, executable);
        return executableFile.isFile() ? executableFile : null;
    }

    private boolean isWindows() {
        return WINDOWS_NAME_PATTERN.matcher(System.getProperty("os.name")).matches();
    }

    public File findExecutable() {
        if (mavenExecutable == null) {
            setupMavenHome();
            setupMavenExecutable();
        }
        return mavenExecutable;
    }

    public MavenLocator baseDirectory(String baseDirectory) {
        this.baseDirectory = new File(baseDirectory);
        return this;
    }
}
