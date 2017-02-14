package org.semanticweb.owlapi.gradle.osgi

import aQute.bnd.osgi.Analyzer
import aQute.bnd.osgi.Jar
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier
import org.gradle.api.artifacts.result.DependencyResult
import org.gradle.api.artifacts.result.ResolvedDependencyResult
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.*

import java.nio.file.Files
import java.nio.file.Path

import static java.nio.file.Files.copy
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

@CacheableTask
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
    FileCollection outputClasspath = project.files()

    Set<String> bundles = []

    Set<String> seenBundleVersions = new TreeSet<String>()
    def postOrderTraversal(Set<DependencyResult> kids, Closure f, int level = 0) {
        if (kids.empty)
            return
        //println("walk level: ${level}")  ${dependencyToFileMap[kid.selected.id]}
        for (DependencyResult kid : kids) {
            Jar jar = dependencyToJarMap[kid.selected.id]
            if (jar?.bsn) {
                String versionKey = makeBundleVersionKey(jar.bsn, jar.version).toString()
                if (seenBundleVersions.contains(versionKey)) {
                    logger.info "Have $versionKey"
                }
                if (seenBundleVersions.add(versionKey)) {
                    logger.info("seeing $versionKey for first time")

                }
            }
            if (!jar?.bsn || walkExistingBundles) {
                //println "${spaces(level)}${kid.selected.id} "
                postOrderTraversal(kid.selected.dependencies, f, level + 1)
            }
            f.call(kid)
        }
        // println "end level ${level}"
    }

    private String makeBundleVersionKey(ComponentIdentifier id) {
        return makeBundleVersionKey(id.group, id.module, id.version)
    }

    private String makeBundleVersionKey(String bsn, String version) {
        return "${bsn};version=${version}" as String
    }

    private String makeBundleVersionKey(String group, String mod, String version) {
        return "${group}.${mod};version=${version}" as String
    }
    Map<ComponentIdentifier, File> dependencyToFileMap = [:]
    Map<Object, Jar> dependencyToJarMap = [:]
    Map<File, File> subMap = [:]
    HashSet seen = []

    private void buildDependencyToFileMap() {
        configuration.resolvedConfiguration.resolvedArtifacts.each {
            dependencyToFileMap[it.id.componentIdentifier] = it.file
            def jar = new aQute.bnd.osgi.Jar(it.file)
            dependencyToJarMap[it.id.componentIdentifier] = jar
            if (jar.bsn) {
                def versionKey = "${jar.bsn};version=${jar.version}" as String
                logger.debug "initial mark of $versionKey for $it.file"
                seenBundleVersions.add(versionKey)
            }

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
            ResolvedDependencyResult dep ->
                ComponentIdentifier id = dep.selected.id
                if (!seen.add(id)) {
                    return
                }
                if (id instanceof ProjectComponentIdentifier) {
                    println dep.properties
                    if (copyExistingBundles) {
                        File f = dependencyToFileMap[id]
                        Path existing = f.toPath()
                        def destFile = new File(bundleOutputDir, f.name)
                        Path newPath= destFile.toPath()
                        logger.info "link from {} to {}",existing,newPath
                        Files.createLink(newPath,existing)
                        subMap[f] = destFile

                    }
                    return
                }

                String bundleVersionKey = "${id.group}.${id.module};version=${id.version}" as String

                aQute.bnd.osgi.Jar jar = dependencyToJarMap[id]
                if (!jar.bsn) {
                    logger.debug "try wrapping $bundleVersionKey"
                    if (!seenBundleVersions.add(bundleVersionKey)) {
                        logger.debug "don't wrap - already have $bundleVersionKey"
                        return
                    } else {
                        logger.debug "wrap-away  ;not seen $bundleVersionKey"
                    }
                    wrapOneJar(dep, jar)
                    bundles += jar
                } else {
                    logger.info "jar is already a bundle ($jar.name - ${jar.bsn} $jar.version)"
                    if (copyExistingBundles) {
                        def destFile = new File(bundleOutputDir, "${jar.source.name}")
                        Path destPath = destFile.toPath()
                        copy(jar.source.toPath(), destPath, REPLACE_EXISTING)
                        subMap[jar.source] = destFile
                    }
                    bundles += jar
                }
        })
        logger.info "done wrapping"
        List<File> files = configuration.resolvedConfiguration.resolvedArtifacts.collect {
            File file = subMap[it.file]
            if (file == null) {
                file = it.file
            } else {
                logger.debug "replace $it.file with $file"
            }
            file
        }
        outputClasspath = project.files(files)
        logger.debug "outputClasspath = ${outputClasspath.properties}"
        println outputs.fileProperties*.properties
    }


    void wrapOneJar(DependencyResult dep, aQute.bnd.osgi.Jar toWrap) {
        Analyzer analyzer = new Analyzer()
        analyzer.setClasspath(bundles)
        analyzer.setJar(toWrap)
        def id = dep.selected.id
        def depId = id
        analyzer.setBundleSymbolicName("${depId.group}.${depId.module}")
        analyzer.bundleVersion = depId.version
        analyzer.exportPackage = "*;version=\"${depId.version}\""
        //analyzer.importPackage = "*"
        analyzer.analyze()
        toWrap.manifest = analyzer.calcManifest()
        logger.debug "{}", toWrap.manifest.properties
        def outputFile = new File(bundleOutputDir, "osgi-wrapped.${depId.group}.${depId.module}-${depId.version}.jar")
        analyzer.save(outputFile, true)
        addSubmapEntry(id, outputFile)

    }

    private void addSubmapEntry(id, outputFile) {
        File object = dependencyToFileMap[id]
        logger.debug "submap[$object] = ${outputFile}"
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
