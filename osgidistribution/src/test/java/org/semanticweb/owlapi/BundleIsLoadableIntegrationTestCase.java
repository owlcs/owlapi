package org.semanticweb.owlapi;

/**
 * Created by ses on 3/5/15.
 */

import org.apache.felix.framework.FrameworkFactory;
import org.junit.Ignore;
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

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
@Category(IntegrationTest.class)
public class BundleIsLoadableIntegrationTestCase {
    private static Logger logger = LoggerFactory.getLogger(BundleIsLoadableIntegrationTestCase.class);
    @Ignore
    @Test
    public void startBundle() throws BundleException, ClassNotFoundException, IllegalAccessException,
            InstantiationException, IOException {
        // Stream.of(System.getProperty("java.class.path").split(":")).filter(x
        // -> x.contains(".jar")).forEach(
        // System.out::println);
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
        final BundleContext context = framework.getBundleContext();
        assertNotNull("context is null", context);
        BufferedReader in = new BufferedReader(new FileReader("build/tmp/test/bundleName"));
        in.lines().forEach( archivePath -> {
            File file = new File(archivePath);
            assertNotNull("file is null", file);
            URI uri = file.toURI();
            assertNotNull("uri is null", uri);
            Bundle bundle = null;
            try {
                logger.info("{}",uri);
                bundle = context.installBundle(uri.toString());
                assertNotNull(bundle);
            } catch (BundleException e) {
                logger.error("Caught Exception for {}", uri, e); //To change body of catch statement use File | Settings | File Templates.
            }
        });

//        List<String> bundles = Arrays.asList("org.apache.servicemix.bundles.javax-inject",
//            "org.apache.servicemix.bundles.aopalliance", "slf4j-simple", "slf4j-api", "caffeine", "guava", "jsr305",
//            "guice-multibindings", "guice-assistedinject", "guice-4", "commons-io", "commons-codec", "jcl-over-slf4j");
//        for (String bundleName : bundles) {
//            try {
//                String simple = getJarURL(bundleName);
//                if (simple.isEmpty()) {
//                    System.out.println("Can't install " + bundleName + ";");
//                }
//                // System.out.println("BundleIsLoadableIntegrationTestCase.startBundle()
//                // " + simple);
//                Bundle simpleLoggerBundle = context.installBundle(simple);
//                try {
//                    simpleLoggerBundle.start();
//                } catch (BundleException e) {
//                    if (!"Fragment bundles can not be started.".equals(e.getMessage())) {
//                        System.out.println("ERROR " + simple + " " + e.getMessage());
//                    }
//                }
//            } catch (Throwable e) {
//                System.out.println("ERROR " + e.getMessage());
//            }
//        }

        try {
            for (Bundle bundle : context.getBundles()) {
                bundle.start();
                assertEquals("bundle state", bundle.getState(), Bundle.ACTIVE);
            }

            Bundle bundle = context.getBundle();
            Class<?> owlManagerClass = bundle.loadClass("org.semanticweb.owlapi.apibinding.OWLManager");
            assertNotNull("no class owlmanager", owlManagerClass);
            owlManagerClass.newInstance();
            assertNotEquals("OWLManager class from bundle class loader  equals OWLManager class from system class path",
                OWLManager.class, owlManagerClass);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw e;
        }
    }

    @Nonnull
    private String getJarURL(String jarNameFragment) {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                String string = url.toString();
                if (string.contains(jarNameFragment)) {
                    // System.out.println("BundleIsLoadableIntegrationTestCase.getJarURL()
                    // " + string);
                    return string;
                }
            }
        }
        return "";
    }
}
