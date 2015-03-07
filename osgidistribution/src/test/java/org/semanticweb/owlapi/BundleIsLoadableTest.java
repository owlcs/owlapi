package org.semanticweb.owlapi;
/**
 * Created by ses on 3/5/15.
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import org.apache.felix.framework.FrameworkFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.test.IntegrationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(IntegrationTest.class)
public class BundleIsLoadableTest {
    @SuppressWarnings("UnusedDeclaration")
    private static Logger logger = LoggerFactory.getLogger(BundleIsLoadableTest.class);

    @Test
    public void startBundle() throws MalformedURLException, BundleException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        HashMap configuration = new HashMap();
        configuration.put("org.osgi.framework.storage.clean","onFirstInit");
        configuration.put("felix.log.level", "4");
        String path = new File("felix-cache").getAbsolutePath();
        configuration.put("org.osgi.framework.storage", path);
        FrameworkFactory frameworkFactory = new FrameworkFactory();
        Framework framework = frameworkFactory.newFramework(configuration);
        assertNotEquals("framework state", framework.getState(), Framework.ACTIVE);
        framework.start();
        assertEquals("framework state", framework.getState(), Framework.ACTIVE);
        File dir = new File("target/");
        dir = dir.getAbsoluteFile();
        File file=null;

        File[] files = dir.listFiles();
        for (File f : files) {
            String fileName = f.getAbsolutePath();
            if (fileName.endsWith("jar") && !fileName.contains("sources") && !fileName.contains("javadoc")) {
                file = f;
                break;
            }
        }

        assertNotNull("file is null", file);
        URI uri = file.toURI();
        assertNotNull("uri is null", uri);
        BundleContext context = framework.getBundleContext();
        assertNotNull("context is null", context);
        try {
            URL simpleLoggerURL;
            Bundle simpleLoggerBundle = context.installBundle(getJarURL("slf4j-simple").toString());
            System.out.println(simpleLoggerBundle);

            simpleLoggerURL = getJarURL("slf4j-api");
            simpleLoggerBundle = context.installBundle(getJarURL("slf4j-api").toString());
            simpleLoggerBundle.start();
        } catch (Throwable e) {
            logger.info("Can't install simple logger;", e); //To change body of catch statement use File | Settings | File Templates.
        }
        Bundle bundle = context.installBundle(uri.toString());
        assertNotNull(bundle);
        bundle.start();
        assertEquals("bundle state", bundle.getState(), Bundle.ACTIVE);

        Class owlManagerClass = null;
            owlManagerClass = bundle.loadClass("org.semanticweb.owlapi.apibinding.OWLManager");
        assertNotNull("no class owlmanager", owlManagerClass);
        Object o = owlManagerClass.newInstance();

        assertNotEquals("OWLManager class from bundle class loader  equals OWLManager class from system class path", OWLManager.class, owlManagerClass);
    }

    private URL getJarURL(String jarNameFragment) {
        URL simpleLoggerURL = null;
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            for (URL url : urlClassLoader.getURLs()) {
                if(url.toString().contains(jarNameFragment)) {
                    simpleLoggerURL = url;
                    break;
                }
            }

        }
        return simpleLoggerURL;
    }
}