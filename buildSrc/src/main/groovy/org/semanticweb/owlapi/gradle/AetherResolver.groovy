package org.semanticweb.owlapi.gradle

import org.apache.maven.repository.internal.MavenRepositorySystemUtils
import org.codehaus.plexus.ContainerConfiguration
import org.codehaus.plexus.DefaultContainerConfiguration
import org.codehaus.plexus.DefaultPlexusContainer
import org.codehaus.plexus.PlexusConstants
import org.eclipse.aether.*
import org.eclipse.aether.artifact.Artifact
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.collection.CollectRequest
import org.eclipse.aether.repository.LocalRepository
import org.eclipse.aether.repository.RemoteRepository
import org.eclipse.aether.repository.RepositoryPolicy
import org.eclipse.aether.resolution.*
import org.eclipse.aether.transfer.AbstractTransferListener
import org.eclipse.aether.transfer.TransferCancelledException
import org.eclipse.aether.transfer.TransferEvent
import org.eclipse.aether.transfer.TransferListener
import org.eclipse.aether.util.artifact.SubArtifact
import org.eclipse.aether.util.graph.version.ContextualSnapshotVersionFilter
import org.eclipse.aether.version.Version
import org.gradle.api.artifacts.Dependency
import org.eclipse.aether.graph.Dependency as AetherDependency
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.function.Predicate
import java.util.stream.Collectors

class AetherResolver {
    private static Logger logger = LoggerFactory.getLogger(AetherResolver.class)
    public static final String CENTRAL_URL = "http://central.maven.org/maven2/"
    private RepositorySystem repositorySystem
    private List<RemoteRepository> repositories
    private RepositorySystemSession session
    private DefaultPlexusContainer plexusContainer

    AetherResolver(def remote = CENTRAL_URL, def localRepo = "build/tmp/mavenRepo/",boolean allowSnapshots=false) {

        ContainerConfiguration config = new DefaultContainerConfiguration()
        config.setAutoWiring(true)
        config.setClassPathScanning(PlexusConstants.SCANNING_INDEX)
        plexusContainer = new DefaultPlexusContainer(config)
        repositorySystem = plexusContainer.lookup(RepositorySystem.class)
        logger.debug "{}", repositorySystem.properties
        //noinspection GroovyAssignabilityCheck                                                                       `
        def localRepository = new LocalRepository(localRepo)

        session = newSession(localRepository)
        if (!allowSnapshots) {
            session.setConfigProperty("aether.snapshotFilter", true)
            session.versionFilter = new ContextualSnapshotVersionFilter()
        }
        logger.debug "localRepository = {} [{}]", localRepository, localRepository?.properties

        if (!(remote instanceof Iterable)) {
            repositories = new ArrayList<RemoteRepository>(Arrays.asList(newCentralRepository()))
        } else {

            repositories = remote.collect { MavenArtifactRepository it -> newRemoteRepository(it.url, it.name,
                    "default",allowSnapshots) }
        }
    }

    def newSession(LocalRepository localRepository) {

        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession()
        LocalRepository localRepo = localRepository
        session.localRepositoryManager = repositorySystem.newLocalRepositoryManager(session, localRepo)

        TransferListener listener = defaultTransferListener()
        session.transferListener = listener
        session.repositoryListener = defaultRepositoryListener()

        // uncomment to generate dirty trees
        // session.setDependencyGraphTransformer( null );

        return session
    }

    static RepositoryListener defaultRepositoryListener() {
        RepositoryListener repositoryListener = { RepositoryEvent event ->
            logger.debug "{}", event.properties
        } as RepositoryListener
        return repositoryListener
    }

    TransferListener defaultTransferListener() {
        def listener = new AbstractTransferListener() {
            @Override
            void transferInitiated(TransferEvent event) throws TransferCancelledException {
                super.transferInitiated(event)
                logger.info "Transfer initiated for {}", event.resource
            }
        }
        return listener
    }

    @SuppressWarnings("JavaStylePropertiesInvocation")
    static RemoteRepository newRemoteRepository(def url, String id = null, String type = "default", Boolean enableSnapshots=true, String updatePolicy =RepositoryPolicy.UPDATE_POLICY_ALWAYS) {
        RepositoryPolicy snapshotPolicy = createSnapshotPolicy(enableSnapshots, updatePolicy)
        return new RemoteRepository.Builder(id, type, url as String).setSnapshotPolicy(snapshotPolicy).build()
    }

    public static RepositoryPolicy createSnapshotPolicy(Boolean enabled=true, String updatePolicy=RepositoryPolicy.UPDATE_POLICY_DAILY) {
        def snapshotPolicy = new RepositoryPolicy(enabled, updatePolicy, RepositoryPolicy.CHECKSUM_POLICY_WARN)
        return snapshotPolicy
    }


    static def newCentralRepository() {
        return newRemoteRepository(CENTRAL_URL, "central")
    }

    static AetherDependency dep(Dependency gradleDep, String scope = null) {
        new AetherDependency(artifact(gradleDep.group, gradleDep.name, gradleDep.version), scope)
    }

    static Artifact artifact(String group, String name, String version = null, String ext = "jar") {
        return new DefaultArtifact(group, name, ext, version)
    }

    static Iterable<Artifact> artifacts(String group, String name, Iterable<String> versions, String ext = "jar") {
        versions.collect { String version -> artifact(group, name, version, ext) }
    }


    CollectRequest collectRequest() {
        def request = new CollectRequest()
        request.repositories = repositories
        return request
    }

    ArtifactRequest artifactRequest() {
        def ar = new ArtifactRequest()
        ar.repositories = repositories
        ar
    }

    DependencyResult resolveDependencies(Artifact art) {
        CollectRequest cr = collectRequest()
        cr.rootArtifact = art
        cr.root = new AetherDependency(art, null)
        if (logger.debugEnabled) {
            logger.debug "cr = {} [{}]", cr, cr?.properties
        }

        def dr = resolveDependencies(cr)
        if (logger.debugEnabled) {
            logger.debug "dr = {} [{}]", dr, dr?.properties
        }
        dr
    }

    DependencyResult resolveDependencies(CollectRequest cr) {
        def dr = new DependencyRequest()
        dr.collectRequest = cr
        return resolveDependencies(dr)
    }

    DependencyResult resolveDependencies(DependencyRequest dr) {
        return repositorySystem.resolveDependencies(session, dr)
    }

    Collection<DependencyResult> resolveVersions(String group, String name, String range,Predicate<Version> filter = null) {
        def listOfVersions = resolveVersionRange(group, name, range,filter)
        def artifacts = artifacts(group, name, listOfVersions.collect { it as String })
        logger.debug "arts {}", artifacts
        def dependencyResults = artifacts.collect {
            resolveDependencies(it)
        }
        return dependencyResults
    }

    static def addSourcesAndJavadocToRequest(CollectRequest cr) {
        cr.dependencies.clone().each { org.eclipse.aether.graph.Dependency it ->
            cr.addDependency(new org.eclipse.aether.graph.Dependency(new SubArtifact(it.artifact, "javadoc", "jar"), it.scope))
            cr.addDependency(new org.eclipse.aether.graph.Dependency(new SubArtifact(it.artifact, "" +
                    "sources", "jar"), it.scope))
        }
    }

    List<Version> resolveVersionRange(Dependency dep, String range,Predicate<Version> filter = null) {
        return resolveVersionRange(dep.group, dep.name, range,filter)
    }

    List<Version> resolveVersionRange(String group, String name, String range, Predicate<Version> filter=null) {
        Artifact artifact = new DefaultArtifact("${group}:${name}:${range}")

        VersionRangeRequest rangeRequest = new VersionRangeRequest()
        rangeRequest.artifact = artifact
        rangeRequest.repositories = repositories


        VersionRangeResult rangeResult = repositorySystem.resolveVersionRange(session, rangeRequest)
        List<Version> versions = rangeResult.versions
        if (filter)
            versions = versions.stream().filter(filter).collect(Collectors.toList())
        return versions
    }

}
