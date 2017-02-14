package org.semanticweb.owlapi.gradle.osgi

import aQute.bnd.differ.Baseline
import aQute.bnd.differ.DiffPluginImpl
import aQute.bnd.header.Parameters
import aQute.bnd.osgi.Analyzer
import aQute.bnd.osgi.Domain
import aQute.bnd.osgi.Instructions
import aQute.bnd.osgi.Jar
import aQute.bnd.service.diff.Delta
import aQute.bnd.service.diff.Diff
import aQute.bnd.service.diff.Tree
import aQute.bnd.version.MavenVersion
import aQute.bnd.version.Version as BndVersion
import aQute.libg.reporter.ReporterAdapter
import aQute.service.reporter.Reporter
import org.eclipse.aether.resolution.ArtifactResult
import org.eclipse.aether.resolution.DependencyResult
import org.eclipse.aether.version.Version
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.artifacts.repositories.ArtifactRepository
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.tasks.*
import org.semanticweb.owlapi.gradle.AetherResolver

import java.util.function.Predicate

class ReBaselineTask extends DefaultTask {
    @Optional
    @Input
    String rebaseGroupName = project.group

    @Optional
    @Input
    String rebaseArtifactName = project.name

    @Input
    String rebaseVersionRange

    @Optional
    @Input boolean savedReversionedFiles = true

    @Optional
    @Input
    boolean includeSnapshots = false

    @Optional
    Reporter reporter = null

    @Optional
    boolean pedantic = null

    @Optional
    Collection<ArtifactRepository> remoteRepositories

    @Optional
    def localRepository = null

    @OutputDirectory
    def outputDir = new File(project.buildDir,name)

    @Optional
    BundleTask currentBundle

    @InputFiles
    Collection<File> inputFiles = new HashSet<>()

    Map<String,BndVersion> packageVersions

    private static final Predicate<Version> isSnapshot = { (it.toString().endsWith("-SNAPSHOT")) }
    private static final Predicate<Version> isReleaseVersion = isSnapshot.negate()


    private AetherResolver resolver
    private Baseline baseline
    private DiffPluginImpl differ
    private Collection<DependencyResult> dependencyResults

    @Override
    Task configure(Closure closure) {
        super.configure(closure)

        if (reporter == null) {
            reporter = new ReporterAdapter(System.out.newPrintWriter())
        }
        reporter.pedantic = pedantic

        if (rebaseVersionRange == null) {
            BndVersion v = MavenVersion.parseMavenString(project.version as String).OSGiVersion
            rebaseVersionRange = "[${v.major}.${v.minor},${project.version})"
        }

        resolver = configureResolver()
        def versionFilter = includeSnapshots ? null : isReleaseVersion
        if (logger.isDebugEnabled()) {
            logger.debug "RebaseVersions {}", resolver.resolveVersionRange(rebaseGroupName, rebaseArtifactName, rebaseVersionRange,versionFilter)
        }
        logger.info("resolving versions")
        this.dependencyResults = resolver.resolveVersions(rebaseGroupName, rebaseArtifactName, rebaseVersionRange, versionFilter)
        for (DependencyResult dependencyResult : dependencyResults) {

            for (ArtifactResult artifactResult in dependencyResult.artifactResults) {
                inputFiles += artifactResult.artifact.file
            }
        }
        logger.info("resolved versions")
        logger.debug "input files: {}", inputFiles
        this
    }

    private AetherResolver configureResolver() {

        if (!remoteRepositories) {
            remoteRepositories = project.repositories.matching({
                it instanceof MavenArtifactRepository && it.url.scheme.startsWith("http")
            })
        }
        if (remoteRepositories.empty) {
            remoteRepositories += project.repositories.mavenCentral()
        }
        if (localRepository == null) {
            localRepository = project.repositories.mavenLocal()
        }

        File localRepoFile = null
        if (localRepository instanceof MavenArtifactRepository) {
            def localUrl = localRepository.url
            localRepoFile = new File(localUrl)
        } else if (localRepository instanceof File) {
            localRepoFile = localRepository
        }

        if (localRepoFile == null) {
            localRepoFile = new File(temporaryDir, "local-maven-repository")
        }
        resolver = new AetherResolver(remoteRepositories, localRepoFile)
    }

    @TaskAction
    def rebaseline() {
        

        if(this.dependencyResults.size() <2) {
            logger.warn("less than two versions found for rebaselining {}", this.dependencyResults)
            return
        }

        differ = new DiffPluginImpl()
        baseline = new Baseline(this.reporter, differ)

        Iterator<DependencyResult> iterator = this.dependencyResults.iterator()
        Analyzer oldVersionAnalyzer =null

        while(iterator.hasNext()) {
            Analyzer tmpAnalyzer
            tmpAnalyzer?.close()

            tmpAnalyzer = buildAnalyzer(iterator.next())
            long oldLastModified = tmpAnalyzer.jar.lastModified()
            File destFile = getDestFile(tmpAnalyzer)
            if(destFile.exists() &&  destFile.lastModified() >= oldLastModified) {
                logger.info("skipping $tmpAnalyzer.jar.source because $destFile is newer")
                logger.info("tmp {}, dest {}",tmpAnalyzer.jar.lastModified(),destFile.lastModified())
                oldVersionAnalyzer?.close()
                oldVersionAnalyzer = new Analyzer()
                oldVersionAnalyzer.jar = new Jar(destFile)
            }  else {
                logger.info("tmp {}, dest {}",tmpAnalyzer.jar.lastModified(),destFile.lastModified())
                oldVersionAnalyzer = tmpAnalyzer
                saveJarIfNeeded(oldVersionAnalyzer)
                logger.info("found newer jar")
                break
            }
        }

        Analyzer newVersionAnalyzer
        while(iterator.hasNext()) {
            newVersionAnalyzer = buildAnalyzer(iterator.next())
            compareVersions(newVersionAnalyzer,oldVersionAnalyzer)
            // next round
            oldVersionAnalyzer.close()
            oldVersionAnalyzer = newVersionAnalyzer
        }
        if(currentBundle) {
            newVersionAnalyzer = new Analyzer()
            newVersionAnalyzer.jar = new Jar(currentBundle.archivePath)
            newVersionAnalyzer.classpath = currentBundle.configuration.collect {
                new Jar(it)
            }
            compareVersions(newVersionAnalyzer,oldVersionAnalyzer)
        }
        if(!packageVersions) {
          println "last ditch - " + oldVersionAnalyzer.getExportPackage()
            compareVersions(oldVersionAnalyzer,oldVersionAnalyzer)
        }
        newVersionAnalyzer?.close()
        oldVersionAnalyzer?.close()
    }
    private Set<Baseline.Info> runBaseline(Analyzer newer, Analyzer older, Instructions packageFilters) {
        Tree n = differ.tree(newer);
        Parameters nExports = newer.exportPackage;
        Tree o = differ.tree(older);
        Parameters oExports = older.exportPackage;
        if (packageFilters == null)
            packageFilters = new Instructions();

        return baseline.baseline(n, nExports, o, oExports, packageFilters);
    }
    void compareVersions(Analyzer newVersion, Analyzer oldVersion) {
        logger.info("compare $newVersion.jar.name to $oldVersion.jar.name")
        Set<Baseline.Info> x = baseline.baseline(newVersion.jar, oldVersion.jar, new Instructions())

        TreeMap<String, Baseline.Info> infoByPackage = indexByPackageName(x)

        SortedSet<String> unchangedPacks = findPackagesWithNoResourceDiffs(baseline.diff.get("<resources>"))

       infoByPackage.each { k, v ->
           logger.debug  "ibp: $k , ${v.packageDiff.delta} sv = ${v.suggestedVersion} ov = ${v.olderVersion} nv = ${v.newerVersion}" }

        Map<String, BndVersion> versionsToSet = infoByPackage.collectEntries { k, v -> [k, v.suggestedVersion] }
        def baselineChangedPackages = infoByPackage.findAll {k,v -> v.mismatch}

        Map<String, Baseline.Info> needMinors = infoByPackage.findAll {
            it.value.packageDiff.delta == Delta.UNCHANGED && !unchangedPacks.contains(it.key)
        }
        needMinors.each { k, v ->
            BndVersion sv = v.suggestedVersion
            BndVersion newMicro = bumpMicro(sv)
            logger.info "bump micro for {} - suggested {}, newMicro {}",k,sv,newMicro
            versionsToSet[k] = newMicro
        }
        Domain d = Domain.domain(newVersion.jar.manifest)

        def exportPackage = d.exportPackage
        boolean changedVersion = false
        exportPackage.each { k, v ->
            BndVersion nv = versionsToSet[k]
            if (nv) {
                String prevValue = v.get("version")
                String newValue = nv as String
                if(newValue != prevValue) {
                    v.put("version", newValue)
                    changedVersion=true
                }
            }
        }
        packageVersions = versionsToSet
        logger.info "Set packageVersions to {}",packageVersions
        if (changedVersion) {
            def z = Analyzer.printClauses(exportPackage)
            newVersion.properties.setProperty(Analyzer.EXPORT_PACKAGE, z)
            def oldImport = newVersion.getImportPackage()
            logger.info "old import {}",oldImport
            newVersion.properties.setProperty(Analyzer.IMPORT_PACKAGE, "*")
            def suggestedBundleVersion = baseline.bundleInfo.suggestedVersion
            if(!baseline.bundleInfo.mismatch) {
                suggestedBundleVersion  =  bumpMicro(suggestedBundleVersion)
            }
            logger.info("Changes: bv was {} - setting to {}",newVersion.jar.version,
                    suggestedBundleVersion
            )

            newVersion.properties.setProperty(Analyzer.BUNDLE_VERSION, suggestedBundleVersion.toString())

            def newMan = newVersion.calcManifest()
            if (logger.isDebugEnabled()) {
                newMan.write(System.out)
            }
            newVersion.jar.manifest = newMan
            didWork = true
        }
        saveJarIfNeeded(newVersion)
    }

    static BndVersion bumpMicro(BndVersion sv) {
        BndVersion newMicro = new BndVersion(sv.major, sv.minor, sv.micro + 1, sv.qualifier)
        return newMicro
    }

    public void saveJarIfNeeded(Analyzer analyzer) {
        if (savedReversionedFiles) {
            File destFile = getDestFile(analyzer)
            analyzer.save(destFile, true)
        }
    }

    private File getDestFile(Analyzer analyzer) {
        return new File(outputDir, analyzer.jar.name + ".jar")
    }

    public SortedSet<String> findPackagesWithNoResourceDiffs(Diff resourceDiffs) {
        Set<String> packagesWithChangedClassResources = new TreeSet<>()
        Set<String> packagesWithUnchangedClassResources = new TreeSet<>()

        for (rdiff in resourceDiffs.children) {
            def name = rdiff.name
            if (name.endsWith(".class")) {
                def dirPart = name.substring(0, name.lastIndexOf('/'))
                String packName = dirPart.replace('/', '.')
                def filePart = rdiff.name - dirPart
                if (rdiff.delta != Delta.UNCHANGED) {
                    if(packagesWithChangedClassResources.add(packName)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug "1st Changed resource {}", " ${packName} : ${filePart}: ${rdiff.delta} ${rdiff.type} ${rdiff.children*.name}"
                        }
                    }
                } else {
                    packagesWithUnchangedClassResources += packName
                }

            }
        }
        SortedSet<String> unchangedPacks = packagesWithUnchangedClassResources - packagesWithChangedClassResources
        if (logger.isDebugEnabled()) {
            logger.debug  "changed = ${packagesWithChangedClassResources}"

            logger.debug "unchanged = ${unchangedPacks}"
        }
        return unchangedPacks
    }

    public TreeMap<String, Baseline.Info> indexByPackageName(Set<Baseline.Info> x) {
        Map<String, Baseline.Info> infoByPackage = new TreeMap<>()

        for (Baseline.Info y : x) {
            if (logger.isDebugEnabled()) {
                println "packagename = ${y.packageName}, older = ${y.olderVersion}, newer = ${y.newerVersion}, " +
                        "suggested = ${y.suggestedVersion}, providers = ${y.providers}, suggested if prov = ${y.suggestedIfProviders}, " +
                        "mismatch = ${y.mismatch}, warning = ${y.warning}, pdiff = ${y.packageDiff},attributes = ${y.attributes}"
                printDiff(y.packageDiff, "X: ")
            }

            if (logger.isInfoEnabled()) {
                if (y.mismatch) {
                   logger.info "Bump {} for {} - old {}, suggested {}",
                           y.packageDiff.delta.toString().toLowerCase()  ,
                           y.packageName,y.olderVersion,y.suggestedVersion
                }
            }
            infoByPackage[y.packageName] = y
        }
        return infoByPackage
    }


    public static List<Analyzer> buildAnalyzers(Collection<DependencyResult> dependencyResults) {
        return dependencyResults.collect {
            DependencyResult dependencyResult ->
                buildAnalyzer(dependencyResult)
        }
    }

    static def buildAnalyzer(DependencyResult dependencyResult) {
        List<Jar> jars = buildJars(dependencyResult)
        buildAnalyzer(jars)

    }

    static def buildAnalyzer(List<Jar> jars) {
        Analyzer analyzer = new Analyzer()
        analyzer.jar = jars[0]
        analyzer.classpath = jars.drop(1)
        analyzer
    }

    static List<Jar> buildJars(DependencyResult dependencyResult) {
        def jars = dependencyResult.artifactResults.collect {
            new Jar(it.artifact.file)
        }
        return jars
    }
    private printDiff(Diff d, indent = "") {
        if (logger.isDebugEnabled()) {
            if (d.delta != Delta.UNCHANGED) {
                def x = $/$indent$d.type : $d.name $d.delta  /$
                logger.debug "{}", x
                if (d.delta != Delta.REMOVED && d.children) {
                    for (Diff d2 in d.children) {
                        printDiff(d2, indent + "  ")
                    }
                }

            }
        }

    }


}
