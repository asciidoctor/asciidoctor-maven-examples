/**
 * Checks and replaces tabs for whitespaces in .java, .groovy, .adoc and pom.xml's files.
 * This script is made to help comply some basic code styling conventions.
 * Use of tools like checkstyle was not possible due to the characteristics of this project layout.
 * See https://github.com/asciidoctor/asciidoctor-maven-examples/issues/22.
 */
import groovy.io.FileType;

def rootFolder = new File('.')

boolean fix = (System.getProperty('fix') != null)
if (fix) {
    println "[INFO] Fix option enabled. Changes will be applied to the code."
} else {
    println "[INFO] Fix option not enabled. No changes will be applied to the code."
}
def totalIssues = checks(rootFolder, fix)

println "[INFO]"
if (!fix) {
    println "[INFO] Found $totalIssues issue(s)"
} else if (totalIssues > 0) {
    println "[INFO] Fixed $totalIssues issue(s)"
}


/**
 * Recursively checks all files in the input folder looking for inconsistencies
 */
def checks(File folder, boolean fix) {
    int total = 0
    folder.eachFileRecurse (FileType.FILES) { file ->
        if (file.name =~ /(pom\.xml)|(.*\.(java|groovy|adoc))/) {
            int count = file.text.count('\t')
            total += count
            if (count > 0) {
                String message = "Found $count tab(s) in '$file'"
                if (fix) {
                    replaceTabs(file)
                    println "[INFO] $message, all replaced by withespaces"
                } else {
                    println "[INFO] $message. Please review this before the pull request"
                }
            }
        }
    }
    return total
}

/**
 * Replaces all tabs in a file sequences of 4 whitespaces
 */
def replaceTabs (File file) {
    File copy = new File(file.absolutePath+'-TEMP')
    copy.withWriter('UTF-8')  { writer ->
        file.eachLine ('UTF-8') { line ->
            writer.writeLine(line.replace('\t', '    '))
        }
    }
    file.delete()
    copy.renameTo(file)
}
