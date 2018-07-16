/**
 * Created by ses on 1/21/17.
 */

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resolver;
import org.apache.felix.bundlerepository.impl.RepositoryAdminImpl;
import org.apache.felix.bundlerepository.impl.RequirementImpl;
import org.apache.felix.bundlerepository.impl.ResolverImpl;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.service.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class RepositoryFun {
    @SuppressWarnings("UnusedDeclaration")
    private static Logger logger = LoggerFactory.getLogger(RepositoryFun.class);

    @Test
    public void testRepoFun() throws BundleException, ClassNotFoundException {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("org.osgi.framework.storage.clean", "onFirstInit");
        configuration.put("felix.log.level", "4");
        String path = new File("felix-cache").getAbsolutePath();
        configuration.put("org.osgi.framework.storage", path);
        configuration.put("obr.repository.url","file:/Users/ses/src/Semantic/owlapi-gradle/osgidistribution/build/wrapped-bundles/index.xml");
        ServiceLoader<FrameworkFactory> sl = ServiceLoader.load(FrameworkFactory.class);
        FrameworkFactory frameworkFactory = sl.iterator().next();
        Framework framework = frameworkFactory.newFramework(configuration);
        framework.start();
        BundleContext context = framework.getBundleContext();

        //services(context);
        org.apache.felix.utils.log.Logger logger = new org.apache.felix.utils.log.Logger(context);

        RepositoryAdmin admin = new RepositoryAdminImpl(context, logger);
        ResolverImpl resolver = (ResolverImpl) admin.resolver();
        RequirementImpl requirement = new RequirementImpl("bundle");
        requirement.setFilter("(symbolicname=org.eclipse.rdf4j.rdf4j-util)");
       // resolver.add(requirement);
        //resolver.resolve(Resolver.START);
        //resolver.deploy(Resolver.START);
        resolver = (ResolverImpl) admin.resolver();
        requirement = new RequirementImpl("bundle");
        requirement.setFilter("(symbolicname=slf4j.api)");
        resolver.add(requirement);
        resolver.deploy(Resolver.START);

        for (Bundle bundle : framework.getBundleContext().getBundles()) {
            System.out.println("bundle = " + bundle);
        }
        resolver = (ResolverImpl) admin.resolver();
        resolver.deploy(Resolver.START);
        for (Bundle bundle : framework.getBundleContext().getBundles()) {
            System.out.println("bundle = " + bundle);
        }



    }

    private void services(BundleContext context) throws BundleException, ClassNotFoundException {
        Bundle obr = context.installBundle("file:///Users/ses/.gradle/caches/modules-2/files-2.1/" +
                "org.apache.felix/org.apache.felix.bundlerepository/2.0.8/" +
                "a71e48e32e6889bfad65240d2258416fed25587f/org.apache.felix.bundlerepository-2.0.8.jar");
        obr.start();
        for (ServiceReference<?> serviceReference : obr.getRegisteredServices()) {
            System.out.println("serviceReference = " + serviceReference);
        }
        BundleContext obc = obr.getBundleContext();
        Class c = obr.loadClass("org.apache.felix.bundlerepository.RepositoryAdmin");
        ClassLoader cl =  c.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ServiceReference<Repository> sr = obc.getServiceReference(Repository.class);
        Class<RepositoryAdmin> clazz = RepositoryAdmin.class;
        ServiceReference<RepositoryAdmin> serviceReference = obc.getServiceReference(clazz);
        Object repoadmin = obc.getService(serviceReference);
        Class c2 = repoadmin.getClass();
        //repoadmin.listRepositories();
        Object repo = obc.getService(sr);
    }
}
