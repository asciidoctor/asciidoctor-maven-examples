/**
 * Updates all properties ending with 'version' copying its value from the 
 * parent project.
 * This script is made as an alternative to `versions-maven-plugin`, which 
 * validates versions and does not allow to use local installed snapshots :(
 */
import ParsingUtils as parser
import CommandLineUtils as commandLine

def root = new File('.')
def parentPom = new XmlSlurper().parse(new File(root,'pom.xml'))
// parent properties parsing
def replacements = parser.findReplacements(parentPom)
replacements << commandLine.getVersionProperties()
println replacements
// updates
parser.updateProjects(root, replacements)

commandLine.getVersionProperties()

class CommandLineUtils {

    static Map getVersionProperties () {
        def properties = [:]
        if (System.getProperty("versions")) {
            System.getProperty("versions").split(",").each { p ->
                def (k, v) = p.split("=")
                properties["${k}>"] = v
            }
        }
        properties
    }
}


// Create class to avoid naming problems with the hyphen in the script name
class ParsingUtils {

    /**
     * Returns a map with the different candidates (properties with values) to update
     */
    static Map findReplacements(def pom) {
        def replacements = [:]
        pom.properties.children().each {
            if (it.name().endsWith('version')) {
                replacements["${it.name()}>"] = it.text()
            }
        }

        if (replacements.size() > 0) {
            println "[INFO] Found ${replacements.size()} candidate properties to update:"
            replacements.each { k, v ->
                println "[INFO]\t\t${k[0..-2]} = $v"
            }
        }
        replacements
    }

    /**
     * Child pom's update
     */
    static void updateProjects(File rootFolder, def properties) {
        rootFolder.eachDirRecurse { folder ->
            folder.listFiles({ it.name == 'pom.xml' } as FileFilter).each {
                updatePom(it, properties)
            }
        }
    }

    static void updatePom(File pom, def properties) {
        // println "[INFO] Parsing ${pom.absolutePath}"
        File copy = new File(pom.absolutePath + '-TEMP')
        copy.withWriter('UTF-8') { writer ->
            pom.eachLine('UTF-8') { line ->
                properties.each { k, v ->
                    if (line.contains(k)) {
                        // println "[INFO] Updating property ${k[0..-2]}"
                        line = "${line.split(k)[0]}$k$v</$k"
                    }
                }
                /* manually concatenate line break to avoid windows evil bytes */
                writer.write("$line\n")
            }
        }
        pom.delete()
        copy.renameTo(pom)
    }

}