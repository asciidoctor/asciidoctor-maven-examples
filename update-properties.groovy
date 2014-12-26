/**
 * Updates all properties ending with 'version' copying its value from the 
 * parent project.
 * This script is made as an alternative to `versions-maven-plugin`, which 
 * validates versions and does not allow to use local installed snapshots :(
 */
import groovy.io.FileType;

def root = new File('.')

/* parent properties parsing */
def parentPom = new XmlSlurper().parse(new File(root,'pom.xml'))
def replacements = [:]
parentPom.properties.children().each {
    if (it.name().endsWith('version')) {
        replacements["${it.name()}>"] = it.text()
    }
}

if (replacements.size() > 0) {
    println "[INFO] Found ${replacements.size()} properties to update:"
    replacements.each { k, v ->
        println "[INFO]\t\t${k[0..-2]} = $v"
    }
}

/* child pom's update */
root.eachDir { folder ->
    folder.listFiles({it.name == 'pom.xml'} as FileFilter).each { File pom ->
        // println "[INFO] Parsing ${pom.absolutePath}"
        File copy = new File(pom.absolutePath+'-TEMP')
        copy.withWriter('UTF-8')  { writer ->
            pom.eachLine ('UTF-8') { line ->
                replacements.each { k, v ->
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
