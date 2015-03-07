package org.semanticweb.owlapi;

/**
 * Created by ses on 3/5/15.
 */
import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.test.IntegrationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(IntegrationTest.class)
public class BundleIsLoadableTest {

    @SuppressWarnings("UnusedDeclaration")
    private static Logger logger = LoggerFactory
            .getLogger(BundleIsLoadableTest.class);

    @Test
    public void startBundle() throws MalformedURLException, BundleException,
            ClassNotFoundException {
        HashMap configuration = new HashMap();
        FrameworkFactory frameworkFactory = new FrameworkFactory();
        Framework framework = frameworkFactory.newFramework(configuration);
        assertNotEquals("framework state", framework.getState(),
                Framework.ACTIVE);
        framework.start();
        assertEquals("framework state", framework.getState(), Framework.ACTIVE);
        File file = new File(
                "target/owlapi-osgidistribution-4.0.2-SNAPSHOT.jar");
        assertNotNull("file is null", file);
        URI uri = file.toURI();
        assertNotNull("uri is null", uri);
        BundleContext context = framework.getBundleContext();
        assertNotNull("context is null", context);
        Bundle bundle = context.installBundle(uri.toString());
        assertNotNull(bundle);
        bundle.start();
        assertEquals("bundle state", bundle.getState(), Bundle.ACTIVE);
        Class owlManagerClass = null;
        owlManagerClass = bundle
                .loadClass("org.semanticweb.owlapi.apibinding.OWLManager");
        assertNotNull("no class owlmanager", owlManagerClass);
        assertNotEquals(
                "OWLManager class from bundle class loader  equals OWLManager class from system class path",
                OWLManager.class, owlManagerClass);
    }
}
