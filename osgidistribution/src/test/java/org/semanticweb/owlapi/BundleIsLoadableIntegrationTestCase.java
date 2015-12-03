package org.semanticweb.owlapi;

/**
 * Created by ses on 3/5/15.
 */
import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.felix.framework.FrameworkFactory;
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

@SuppressWarnings("javadoc")
@Category(IntegrationTest.class)
public class BundleIsLoadableIntegrationTestCase {

    private static Logger logger = LoggerFactory
        .getLogger(BundleIsLoadableIntegrationTestCase.class);

    @Test
    public void startBundle() throws BundleException, ClassNotFoundException,
        IllegalAccessException, InstantiationException {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("org.osgi.framework.storage.clean", "onFirstInit");
        configuration.put("felix.log.level", "4");
        String path = new File("felix-cache").getAbsolutePath();
        configuration.put("org.osgi.framework.storage", path);
        FrameworkFactory frameworkFactory = new FrameworkFactory();
        Framework framework = frameworkFactory.newFramework(configuration);
        assertNotEquals("framework state", framework.getState(), Bundle.ACTIVE);
        framework.start();
        assertEquals("framework state", framework.getState(), Bundle.ACTIVE);
        File dir = new File("target/");
        dir = dir.getAbsoluteFile();
        File file = null;
        File[] files = dir.listFiles();
        for (File f : files) {
            String fileName = f.getAbsolutePath();
            if (fileName.endsWith("jar") && !fileName.contains("sources")
                && !fileName.contains("javadoc")) {
                file = f;
                break;
            }
        }
        assertNotNull("file is null", file);
        URI uri = file.toURI();
        assertNotNull("uri is null", uri);
        BundleContext context = framework.getBundleContext();
        assertNotNull("context is null", context);
        List<String> bundles = Arrays.asList("javax-inject", "aopalliance","caffeine",
            "guava", "guice-multibindings", "guice-assistedinject", "guice-4",
            "aopalliance", "commons-io", "commons-codec", "slf4j-simple", "jcl-over-slf4j", "slf4j-api");
        for (String bundleName : bundles) {
            try {
                String simple = getJarURL(bundleName);
                if (simple.isEmpty()) {
                    logger.info("Can't install " + bundleName + ";");
                }
                Bundle simpleLoggerBundle = context.installBundle(simple);
                try {
                    simpleLoggerBundle.start();
                } catch (BundleException e) {
                    e.printStackTrace();
                }
            } catch (Throwable e) {
                logger.info("Can't install: " + bundleName, e);
            }
        }
        Bundle bundle = context.installBundle(uri.toString());
        assertNotNull(bundle);
        bundle.start();
        assertEquals("bundle state", bundle.getState(), Bundle.ACTIVE);
        Class<?> owlManagerClass = bundle
            .loadClass("org.semanticweb.owlapi.apibinding.OWLManager");
        assertNotNull("no class owlmanager", owlManagerClass);
        owlManagerClass.newInstance();
        assertNotEquals(
            "OWLManager class from bundle class loader  equals OWLManager class from system class path",
            OWLManager.class, owlManagerClass);
    }

    @Nonnull
    private String getJarURL(String jarNameFragment) {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                String string = url.toString();
                if (string.contains(jarNameFragment)) {
                    System.out.println("BundleIsLoadableIntegrationTestCase.getJarURL() " + string);
                    return string;
                }
            }
        }
        return "";
    }
}
