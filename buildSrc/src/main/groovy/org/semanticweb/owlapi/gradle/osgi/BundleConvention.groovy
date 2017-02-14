package org.semanticweb.owlapi.gradle.osgi

import aQute.bnd.header.Parameters
import aQute.bnd.osgi.Builder
import aQute.bnd.version.MavenVersion
import org.gradle.api.GradleException
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.Jar

import java.util.jar.Manifest

import static aQute.bnd.osgi.Constants.*
/**
 * This class is copied from the BND Project in order to make a few changes.
 */
class BundleConvention {
    private final Jar task
    private File bndfile
    private SourceSet sourceSet
    @Optional
    @Classpath
    @InputFiles
    Set<File> bundleClasspath = new TreeSet<>()
    @Optional
    @Input
    Configuration configuration

    BundleConvention(Jar task) {
        this.task = task
    }

    void buildBundle() {
        println "BUILD BUNDLE CALLED"
        task.configure {
            // create Builder and set trace level from gradle
            new Builder().withCloseable { builder ->
                // load bnd properties
                File temporaryBndFile = File.createTempFile('bnd', '.bnd', temporaryDir)
                temporaryBndFile.withWriter('UTF-8') { writer ->
                    // write any task manifest entries into the tmp bnd file
                    manifest.effectiveManifest.attributes.inject(new Properties()) { properties, key, value ->
                        if (key != 'Manifest-Version') {
                            properties.setProperty(key, value.toString())
                        }
                        return properties
                    }.store(writer, null)

                    // if the bnd file exists, add its contents to the tmp bnd file
                    if (bndfile.isFile()) {
                        builder.loadProperties(bndfile).store(writer, null)
                    }
                }
                builder.setProperties(temporaryBndFile, project.projectDir) // this will cause project.dir property to be set
                builder.setProperty('project.name', project.name)
                builder.setProperty('project.output', project.buildDir.canonicalPath)

                // If no bundle to be built, we have nothing to do
                if (Builder.isTrue(builder.getProperty(NOBUNDLES))) {
                    return
                }

                // Reject sub-bundle projects
                if (builder.getSubBuilders() != [builder]) {
                    throw new GradleException('Sub-bundles are not supported by this task')
                }

                // Include entire contents of Jar task generated jar (except the manifest)
                project.copy {
                    from archivePath
                    into temporaryDir
                }
                File archiveCopyFile = new File(temporaryDir, archiveName)
                aQute.bnd.osgi.Jar archiveCopyJar = new aQute.bnd.osgi.Jar(archiveName, archiveCopyFile)
                archiveCopyJar.setManifest(new Manifest())
                builder.setJar(archiveCopyJar)

                // set builder bundleClasspath
                println "BCP = $bundleClasspath"
                if (!bundleClasspath) {
                    def copyRecursive = getConfiguration().copyRecursive()
                    def resFiles = [sourceSet.output.classesDir]

                    resFiles += copyRecursive.resolvedConfiguration.resolvedArtifacts.findAll {
                        it.classifier == null || it.classifier == jar
                    }*.file
                    def prependFiles = project.fileTree("build/wrapped-bundles")
                    resFiles = prependFiles + resFiles
                    bundleClasspath = project.files(resFiles)
                }
                Set<File> bcp = new HashSet<>(bundleClasspath)
                bcp.add(getSourceSet().output.classesDir)
                builder.setProperty('project.buildpath', project.files(bcp).asPath)
                builder.setClasspath(bcp as File[])
                logger.info "CP*.source {}", builder.getClasspath()*.source

                // set builder sourcepath
                def sourcepath = project.files(getSourceSet().allSource.srcDirs.findAll { it.exists() })
                builder.setProperty('project.sourcepath', sourcepath.asPath)
                builder.setSourcepath(sourcepath as File[])
                logger.debug 'builder sourcepath: {}', builder.getSourcePath()

                // set bundle symbolic name from tasks's baseName property if necessary
                String bundleSymbolicName = builder.getProperty(BUNDLE_SYMBOLICNAME)
                if (isEmpty(bundleSymbolicName)) {
                    builder.setProperty(BUNDLE_SYMBOLICNAME, baseName)
                }

                // set bundle version from task's version if necessary
                String bundleVersion = builder.getProperty(BUNDLE_VERSION)
                if (isEmpty(bundleVersion)) {
                    builder.setProperty(BUNDLE_VERSION, MavenVersion.parseString(version?.toString()).getOSGiVersion().toString())
                }

                Parameters exportPackages = builder.exportPackage
                Parameters newExports = new Parameters()
                exportPackages.each { parameter ->
                    if (parameter.key == '{local-packages}') {
                        logger.debug "sso {}", getSourceSet().output.properties
                        def classDir = getSourceSet().output.classesDir
                        def cdPath = classDir.toPath()
                        classDir.eachDirRecurse {
                            File dir ->
                                def hasFile = dir.listFiles().any({ it.isFile() })
                                if (hasFile) {
                                    def rel = cdPath.relativize(dir.toPath())
                                    def pstring = rel.toString().replace('/', '.')
                                    newExports.put(pstring, parameter.value)
                                }
                        }
                    } else {
                        newExports.put(parameter.key, parameter.value)
                    }
                }
                logger.info "newExports = {}", newExports
                builder.setProperty(EXPORT_PACKAGE, newExports.toString())
                logger.debug 'builder properties: {}', builder.getProperties()

                // Build bundle
                aQute.bnd.osgi.Jar bundleJar = builder.build()
                if (!builder.isOk()) {
                    // if we already have an error; fail now
                    builder.getWarnings().each {
                        logger.warn 'Warning: {}', it
                    }
                    builder.getErrors().each {
                        logger.error 'Error  : {}', it
                    }
                    failBuild("Bundle ${archiveName} has errors")
                }

                // Write out the bundle
                bundleJar.updateModified(archiveCopyFile.lastModified(), 'time of Jar task generated jar')
                try {
                    bundleJar.write(archivePath)
                } catch (Exception e) {
                    failBuild("Bundle ${archiveName} failed to build: ${e.getMessage()}", e)
                } finally {
                    bundleJar.close()
                }

                builder.getWarnings().each {
                    logger.warn 'Warning: {}', it
                }
                builder.getErrors().each {
                    logger.error 'Error  : {}', it
                }
                if (!builder.isOk()) {
                    failBuild("Bundle ${archiveName} has errors")
                }
            }
        }
    }

    File getBndfile() {
        if (bndfile == null) {
            setBndfile(task.project.file('bnd.bnd'))
        }
        return bndfile
    }

    void setBndfile(File bndfile) {
        this.bndfile = bndfile
    }


    SourceSet getSourceSet() {
        if (sourceSet == null) {
            println "defaulting sourceset"
            setSourceSet(task.project.sourceSets.main)
        }
        return sourceSet
    }

    void setSourceSet(SourceSet sourceSet) {
        this.sourceSet = sourceSet
    }

    private void failBuild(String msg) {
        task.archivePath.delete()
        throw new GradleException(msg)
    }

    private void failBuild(String msg, Exception e) {
        task.archivePath.delete()
        throw new GradleException(msg, e)
    }

    private boolean isEmpty(String header) {
        return (header == null) || header.trim().isEmpty() || EMPTY_HEADER.equals(header)
    }

}
