package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Provider;

import uk.ac.manchester.cs.owl.owlapi.OWLImportsDeclarationImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class OWLOntologyManager_Concurrent_TestCase {

    private OWLOntologyManager manager;
    @Mock private Lock readLock, writeLock;
    @Mock private OWLDataFactory dataFactory;
    @Mock private ReadWriteLock readWriteLock;
    private OWLOntology ontology;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        when(readWriteLock.readLock()).thenReturn(readLock);
        when(readWriteLock.writeLock()).thenReturn(writeLock);
        manager = new OWLOntologyManagerImpl(dataFactory, readWriteLock);
        mockAndAddOntologyFactory();
        mockAndAddOntologyStorer();
        IRI iri = IRI.create("http://owlapi/ont");
        ontology = manager.createOntology(iri);
        manager.setOntologyDocumentIRI(ontology, iri);
        reset(readLock, writeLock, readWriteLock);
    }

    private void mockAndAddOntologyFactory() throws OWLOntologyCreationException {
        OWLOntologyFactory ontologyFactory = mock(OWLOntologyFactory.class);
        when(ontologyFactory.canCreateFromDocumentIRI(any(IRI.class))).thenReturn(true);
        when(ontologyFactory.canLoad(any(OWLOntologyDocumentSource.class))).thenReturn(true);
        final OWLOntology owlOntology = new OWLOntologyImpl(manager, new OWLOntologyID());
        when(ontologyFactory.createOWLOntology(any(OWLOntologyManager.class), any(OWLOntologyID.class), any(IRI.class),
            any(OWLOntologyFactory.OWLOntologyCreationHandler.class))).thenAnswer(new Answer<OWLOntology>() {

                @Override
                public OWLOntology answer(InvocationOnMock invocation) throws Throwable {
                    ((OWLOntologyFactory.OWLOntologyCreationHandler) invocation.getArguments()[3]).ontologyCreated(
                        owlOntology);
                    return owlOntology;
                }
            });
        when(ontologyFactory.loadOWLOntology(any(OWLOntologyManager.class), any(OWLOntologyDocumentSource.class), any(
            OWLOntologyFactory.OWLOntologyCreationHandler.class), any(OWLOntologyLoaderConfiguration.class)))
                .thenAnswer(new Answer<OWLOntology>() {

                    @Override
                    public OWLOntology answer(InvocationOnMock invocation) throws Throwable {
                        ((OWLOntologyFactory.OWLOntologyCreationHandler) invocation.getArguments()[2]).ontologyCreated(
                            owlOntology);
                        return owlOntology;
                    }
                });
        manager.setOntologyFactories(Collections.singleton(ontologyFactory));
    }

    private void mockAndAddOntologyStorer() {
        OWLStorer storer = mock(OWLStorer.class);
        when(storer.canStoreOntology(any(OWLDocumentFormat.class))).thenReturn(true);
        OWLStorerFactory storerFactory = mock(OWLStorerFactory.class);
        when(storerFactory.createStorer()).thenReturn(storer);
        when(storerFactory.getFormatFactory()).thenReturn(mock(OWLDocumentFormatFactory.class));
        manager.setOntologyStorers(Collections.singleton(storerFactory));
    }

    @Test
    public void shouldCall_contains_with_readLock() {
        IRI arg0 = mockIRI();
        manager.contains(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_contains_with_readLock_2() {
        OWLOntologyID arg0 = new OWLOntologyID();
        manager.contains(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_contains_with_readLock_3() {
        manager.contains(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologies_with_readLock() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        manager.getOntologies(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_containsVersion_with_readLock() {
        IRI arg0 = mockIRI();
        manager.containsVersion(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getVersions_with_readLock() {
        IRI arg0 = mockIRI();
        manager.getVersions(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyIDsByVersion_with_readLock() {
        IRI arg0 = mockIRI();
        manager.getOntologyIDsByVersion(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntology_with_readLock() {
        OWLOntologyID arg0 = new OWLOntologyID();
        manager.getOntology(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntology_with_readLock_2() {
        IRI arg0 = mockIRI();
        manager.getOntology(arg0);
        verifyReadLock_LockUnlock();
    }

    private IRI mockIRI() {
        return IRI.create("http://owlapi.sourceforge.net/stuff");
    }

    @Test
    public void shouldCall_getImportedOntology_with_readLock() {
        OWLImportsDeclaration arg0 = new OWLImportsDeclarationImpl(IRI.create("http://owlapi/ont"));
        manager.getImportedOntology(arg0);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getDirectImports_with_readLock() {
        manager.getDirectImports(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getImports_with_readLock() {
        manager.getImports(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getImportsClosure_with_readLock() {
        manager.getImportsClosure(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_getSortedImportsClosure_with_readLock() {
        manager.getSortedImportsClosure(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock() throws OWLOntologyCreationException {
        IRI arg0 = mockIRI();
        manager.createOntology(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_2() throws OWLOntologyCreationException {
        OWLOntologyID arg0 = new OWLOntologyID();
        manager.createOntology(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_3() throws OWLOntologyCreationException {
        IRI arg0 = mockIRI();
        Set<OWLOntology> arg1 = Collections.emptySet();
        boolean arg2 = true;
        manager.createOntology(arg0, arg1, arg2);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_4() throws OWLOntologyCreationException {
        IRI arg0 = mockIRI();
        Set<OWLOntology> arg1 = Sets.newConcurrentHashSet();
        manager.createOntology(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_5() throws OWLOntologyCreationException {
        manager.createOntology();
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_6() throws OWLOntologyCreationException {
        Set<OWLAxiom> arg0 = Collections.singleton(mock(OWLAxiom.class));
        manager.createOntology(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_createOntology_with_writeLock_7() throws OWLOntologyCreationException {
        Set<OWLAxiom> arg0 = Collections.emptySet();
        IRI arg1 = mockIRI();
        manager.createOntology(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_loadOntology_with_writeLock() throws OWLOntologyCreationException {
        IRI arg0 = mockIRI();
        manager.loadOntology(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_loadOntologyFromOntologyDocument_with_writeLock() throws OWLOntologyCreationException {
        OWLOntologyDocumentSource arg0 = mock(OWLOntologyDocumentSource.class);
        when(arg0.getDocumentIRI()).thenReturn(IRI.create("http://owlapi/ontdoc"));
        OWLOntologyLoaderConfiguration arg1 = mock(OWLOntologyLoaderConfiguration.class);
        manager.loadOntologyFromOntologyDocument(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_loadOntologyFromOntologyDocument_with_writeLock_2() throws OWLOntologyCreationException {
        OWLOntologyDocumentSource arg0 = mock(OWLOntologyDocumentSource.class);
        when(arg0.getDocumentIRI()).thenReturn(IRI.create("http://owlapi/ontdoc"));
        manager.loadOntologyFromOntologyDocument(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_loadOntologyFromOntologyDocument_with_writeLock_3() throws OWLOntologyCreationException {
        InputStream arg0 = mock(InputStream.class);
        manager.loadOntologyFromOntologyDocument(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_loadOntologyFromOntologyDocument_with_writeLock_4() throws OWLOntologyCreationException {
        OWLOntologyDocumentSource source = mock(OWLOntologyDocumentSource.class);
        when(source.getDocumentIRI()).thenReturn(IRI.create("http://owlapi/ontdoc"));
        manager.loadOntologyFromOntologyDocument(source);
        verifyWriteLock_LockUnlock();
    }

    private void verifyWriteLock_LockUnlock() {
        InOrder inOrder = Mockito.inOrder(writeLock, writeLock);
        inOrder.verify(writeLock, atLeastOnce()).lock();
        inOrder.verify(writeLock, atLeastOnce()).unlock();
    }

    @Test
    public void shouldCall_loadOntologyFromOntologyDocument_with_writeLock_5() throws OWLOntologyCreationException {
        IRI arg0 = mockIRI();
        manager.loadOntologyFromOntologyDocument(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntology_with_writeLock() {
        OWLOntologyID arg0 = mock(OWLOntologyID.class);
        manager.removeOntology(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntology_with_writeLock_2() {
        manager.removeOntology(ontology);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyDocumentIRI_with_readLock() {
        manager.getOntologyDocumentIRI(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyDocumentIRI_with_writeLock() {
        IRI arg1 = mockIRI();
        manager.setOntologyDocumentIRI(ontology, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyFormat_with_readLock() {
        manager.getOntologyFormat(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyFormat_with_writeLock() {
        OWLDocumentFormat arg1 = mock(OWLDocumentFormat.class);
        manager.setOntologyFormat(ontology, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_writeLock() throws OWLOntologyStorageException {
        OWLDocumentFormat arg1 = mock(OWLDocumentFormat.class);
        IRI arg2 = mockIRI();
        manager.saveOntology(ontology, arg1, arg2);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_2() throws OWLOntologyStorageException {
        OWLDocumentFormat arg1 = mock(OWLDocumentFormat.class);
        OutputStream arg2 = mock(OutputStream.class);
        manager.saveOntology(ontology, arg1, arg2);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_3() throws OWLOntologyStorageException {
        OWLOntologyDocumentTarget arg1 = mock(OWLOntologyDocumentTarget.class);
        manager.saveOntology(ontology, arg1);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_4() throws OWLOntologyStorageException {
        OWLDocumentFormat arg1 = mock(OWLDocumentFormat.class);
        OWLOntologyDocumentTarget arg2 = mock(OWLOntologyDocumentTarget.class);
        manager.saveOntology(ontology, arg1, arg2);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_5() throws OWLOntologyStorageException {
        manager.saveOntology(ontology);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_6() throws OWLOntologyStorageException {
        IRI arg1 = mockIRI();
        manager.saveOntology(ontology, arg1);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_7() throws OWLOntologyStorageException {
        OutputStream arg1 = mock(OutputStream.class);
        manager.saveOntology(ontology, arg1);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_saveOntology_with_readLock_8() throws OWLOntologyStorageException {
        OWLDocumentFormat arg1 = mock(OWLDocumentFormat.class);
        manager.saveOntology(ontology, arg1);
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_addIRIMapper_with_writeLock() {
        OWLOntologyIRIMapper arg0 = mock(OWLOntologyIRIMapper.class);
        manager.addIRIMapper(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeIRIMapper_with_writeLock() {
        OWLOntologyIRIMapper arg0 = mock(OWLOntologyIRIMapper.class);
        manager.removeIRIMapper(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_clearIRIMappers_with_writeLock() {
        manager.clearIRIMappers();
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyStorer_with_writeLock() {
        OWLStorerFactory arg0 = mock(OWLStorerFactory.class);
        manager.addOntologyStorer(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntologyStorer_with_writeLock() {
        OWLStorerFactory arg0 = mock(OWLStorerFactory.class);
        manager.removeOntologyStorer(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_clearOntologyStorers_with_writeLock() {
        manager.clearOntologyStorers();
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setIRIMappers_with_writeLock() {
        Set<OWLOntologyIRIMapper> arg0 = Sets.newHashSet();
        manager.setIRIMappers(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getIRIMappers_with_readLock() {
        manager.getIRIMappers().iterator();
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldAddIRIMapper_with_writeLock() {
        manager.getIRIMappers().add(mock(OWLOntologyIRIMapper.class));
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldRemoveIRIMapper_with_writeLock() {
        manager.getIRIMappers().remove(mock(OWLOntologyIRIMapper.class));
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyParsers_with_writeLock() {
        Set<OWLParserFactory> arg0 = Sets.newHashSet();
        manager.setOntologyParsers(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyParsers_with_readLock() {
        manager.getOntologyParsers().iterator();
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldAddOntologyParser_with_writeLock() {
        manager.getOntologyParsers().add(mock(OWLParserFactory.class));
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldRemoveOntologyParser_with_writeLock() {
        manager.getOntologyParsers().remove(mock(OWLParserFactory.class));
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyFactories_with_writeLock() {
        Set<OWLOntologyFactory> arg0 = Sets.newHashSet();
        manager.setOntologyFactories(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyFactories_with_readLock() {
        manager.getOntologyFactories().iterator();
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyStorers_with_writeLock() {
        Set<OWLStorerFactory> arg0 = Sets.newHashSet();
        manager.setOntologyStorers(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyStorers_with_readLock() {
        manager.getOntologyStorers().iterator();
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyChangeListener_with_writeLock() {
        OWLOntologyChangeListener arg0 = mock(OWLOntologyChangeListener.class);
        OWLOntologyChangeBroadcastStrategy arg1 = mock(OWLOntologyChangeBroadcastStrategy.class);
        manager.addOntologyChangeListener(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addImpendingOntologyChangeListener_with_writeLock() {
        ImpendingOWLOntologyChangeListener arg0 = mock(ImpendingOWLOntologyChangeListener.class);
        manager.addImpendingOntologyChangeListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeImpendingOntologyChangeListener_with_writeLock() {
        ImpendingOWLOntologyChangeListener arg0 = mock(ImpendingOWLOntologyChangeListener.class);
        manager.removeImpendingOntologyChangeListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyChangesVetoedListener_with_writeLock() {
        OWLOntologyChangesVetoedListener arg0 = mock(OWLOntologyChangesVetoedListener.class);
        manager.addOntologyChangesVetoedListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntologyChangesVetoedListener_with_writeLock() {
        OWLOntologyChangesVetoedListener arg0 = mock(OWLOntologyChangesVetoedListener.class);
        manager.removeOntologyChangesVetoedListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setDefaultChangeBroadcastStrategy_with_writeLock() {
        OWLOntologyChangeBroadcastStrategy arg0 = mock(OWLOntologyChangeBroadcastStrategy.class);
        manager.setDefaultChangeBroadcastStrategy(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_makeLoadImportRequest_with_writeLock() {
        OWLImportsDeclaration arg0 = mock(OWLImportsDeclaration.class);
        when(arg0.getIRI()).thenReturn(IRI.create("http://owlapi/other"));
        OWLOntologyLoaderConfiguration arg1 = mock(OWLOntologyLoaderConfiguration.class);
        manager.makeLoadImportRequest(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_makeLoadImportRequest_with_writeLock_2() {
        OWLImportsDeclaration arg0 = new OWLImportsDeclarationImpl(IRI.create("http://owlapi/otheront"));
        manager.makeLoadImportRequest(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addMissingImportListener_with_writeLock() {
        MissingImportListener arg0 = mock(MissingImportListener.class);
        manager.addMissingImportListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeMissingImportListener_with_writeLock() {
        MissingImportListener arg0 = mock(MissingImportListener.class);
        manager.removeMissingImportListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyLoaderListener_with_writeLock() {
        OWLOntologyLoaderListener arg0 = mock(OWLOntologyLoaderListener.class);
        manager.addOntologyLoaderListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntologyLoaderListener_with_writeLock() {
        OWLOntologyLoaderListener arg0 = mock(OWLOntologyLoaderListener.class);
        manager.removeOntologyLoaderListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyChangeProgessListener_with_writeLock() {
        OWLOntologyChangeProgressListener arg0 = mock(OWLOntologyChangeProgressListener.class);
        manager.addOntologyChangeProgessListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntologyChangeProgessListener_with_writeLock() {
        OWLOntologyChangeProgressListener arg0 = mock(OWLOntologyChangeProgressListener.class);
        manager.removeOntologyChangeProgessListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologies_with_readLock_2() {
        manager.getOntologies();
        verifyReadLock_LockUnlock();
    }

    @Test
    public void shouldCall_applyChanges_with_writeLock() {
        List<OWLOntologyChange> arg0 = Lists.newArrayList();
        manager.applyChanges(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_applyChange_with_writeLock() {
        OWLAxiom ax = mock(OWLAxiom.class);
        OWLOntologyChange arg0 = new AddAxiom(ontology, ax);
        manager.applyChange(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addAxioms_with_writeLock() {
        OWLOntology arg0 = mock(OWLMutableOntology.class);
        Set<OWLAxiom> axioms = Sets.newHashSet(mock(OWLAxiom.class));
        manager.addAxioms(arg0, axioms);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addAxiom_with_writeLock() {
        OWLOntology arg0 = mock(OWLMutableOntology.class);
        OWLAxiom arg1 = mock(OWLAxiom.class);
        manager.addAxiom(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeAxioms_with_writeLock() {
        Set<OWLAxiom> arg1 = Sets.newHashSet();
        manager.removeAxioms(ontology, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeAxiom_with_writeLock() {
        OWLOntology arg0 = mock(OWLMutableOntology.class);
        OWLAxiom arg1 = mock(OWLAxiom.class);
        manager.removeAxiom(arg0, arg1);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_addOntologyChangeListener_with_writeLock_2() {
        OWLOntologyChangeListener arg0 = mock(OWLOntologyChangeListener.class);
        manager.addOntologyChangeListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_removeOntologyChangeListener_with_writeLock() {
        OWLOntologyChangeListener arg0 = mock(OWLOntologyChangeListener.class);
        manager.removeOntologyChangeListener(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyLoaderConfigurationProvider_with_writeLock() {
        Provider<OWLOntologyLoaderConfiguration> arg0 = mock(Provider.class);
        manager.setOntologyLoaderConfigurationProvider(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_setOntologyLoaderConfiguration_with_writeLock() {
        OWLOntologyLoaderConfiguration arg0 = mock(OWLOntologyLoaderConfiguration.class);
        manager.setOntologyLoaderConfiguration(arg0);
        verifyWriteLock_LockUnlock();
    }

    @Test
    public void shouldCall_getOntologyLoaderConfiguration_with_readLock() {
        manager.getOntologyLoaderConfiguration();
        verifyReadLock_LockUnlock();
    }

    private void verifyReadLock_LockUnlock() {
        InOrder inOrder = Mockito.inOrder(readLock, readLock);
        inOrder.verify(readLock, atLeastOnce()).lock();
        inOrder.verify(readLock, atLeastOnce()).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }
}
