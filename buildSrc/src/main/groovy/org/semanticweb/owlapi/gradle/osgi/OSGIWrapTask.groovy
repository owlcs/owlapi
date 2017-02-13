package org.semanticweb.owlapi.gradle.osgi

import aQute.bnd.osgi.Analyzer
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier
import org.gradle.api.artifacts.result.DependencyResult
import org.gradle.api.tasks.*

import static java.nio.file.Files.copy
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

class OSGIWrapTask extends DefaultTask {
    @Input
    @Optional
    Configuration configuration = null

    def inputFiles
    @OutputDirectory
    File bundleOutputDir = new File(project.buildDir, "wrapped-bundles")
    @Optional
    @Input
    boolean walkExistingBundles = false
    @Optional
    @Input
    boolean copyExistingBundles = true

    @OutputFiles
    Set<File> outputClasspath = []

    def bundles = []


    def postOrderTraversal(Set<DependencyResult> kids, Closure f, int level = 0) {
        if (kids.empty)
            return
        //println("walk level: ${level}")  ${dependencyToFileMap[kid.selected.id]}
        for (DependencyResult kid : kids) {
            def jar = dependencyToJarMap[kid.selected.id]
            if (!jar?.bsn || walkExistingBundles) {
                //println "${spaces(level)}${kid.selected.id} "
                postOrderTraversal(kid.selected.dependencies, f, level + 1)
            }
            f.call(kid)
        }
        // println "end level ${level}"
    }
    Map<ComponentIdentifier, File> dependencyToFileMap = [:]
    def dependencyToJarMap = [:]
    Map<File, File> subMap = [:]
    HashSet seen = []

    private void buildDependencyToFileMap() {
        configuration.resolvedConfiguration.resolvedArtifacts.each {
            dependencyToFileMap[it.id.componentIdentifier] = it.file
            def jar = new aQute.bnd.osgi.Jar(it.file)
            dependencyToJarMap[it.id.componentIdentifier] = jar

        }
    }

    @TaskAction
    wrap() {
        if (!configuration) {
            configuration = project.configurations.compile
        }
        if(!inputFiles)
            inputFiles = configuration.resolvedConfiguration.resolvedArtifacts*.file

        project.delete(bundleOutputDir.listFiles())
        buildDependencyToFileMap()
        postOrderTraversal(configuration.incoming.resolutionResult.root.dependencies, {
            DependencyResult dep ->
                if (dep.selected.id instanceof ProjectComponentIdentifier)
                    return
                if (!seen.add(dep.selected.id)) {
                    return
                }

                aQute.bnd.osgi.Jar jar = dependencyToJarMap[dep.selected.id]
                if (!jar.bsn) {
                    //println dep.selected.properties
                    wrapOneJar(dep, jar)
                    bundles += jar
                } else {
                    logger.debug "jar is already a bundle (${jar.bsn})"
                    if (copyExistingBundles) {
                        def destPath = new File(bundleOutputDir, "${jar.source.name}").toPath()
                        copy(jar.source.toPath(), destPath, REPLACE_EXISTING)
                    }
                    bundles += jar
                }
        })
        println "done wrapping"
        List<File> files = configuration.resolvedConfiguration.resolvedArtifacts.collect {
            File file = subMap[it.file]
            if (file == null) {
                file = it.file
            } else {
                println "replace $it.file with $file"
            }
            file
        }
        outputClasspath.addAll(files)
        println "outputClasspath = ${outputClasspath*.path}"
    }

    void wrapOneJar(DependencyResult dep, aQute.bnd.osgi.Jar toWrap) {
        Analyzer analyzer = new Analyzer()
        analyzer.setClasspath(bundles)
        analyzer.setJar(toWrap)
        def depId = dep.selected.id
        analyzer.setBundleSymbolicName("${depId.group}.${depId.module}")
        analyzer.bundleVersion = depId.version
        analyzer.exportPackage = "*;version=\"${depId.version}\""
        //analyzer.importPackage = "*"
        analyzer.analyze()
        toWrap.manifest = analyzer.calcManifest()
        logger.debug "{}", toWrap.manifest.properties
        def outputFile = new File(bundleOutputDir, "osgi-wrapped.${depId.group}.${depId.module}-${depId.version}.jar")
        analyzer.save(outputFile, true)
        File object = dependencyToFileMap[dep.selected.id]
        if(object == null) {
            println "null file: $dep $dep.properties"
        }
        println "submap[$object] = ${outputFile}"
        subMap[object] = outputFile

    }

    static spaces(int count) {
        StringBuilder buf = new StringBuilder()
        while (count--) {
            buf.append("  ")
        }
        return buf.toString()
    }

}
