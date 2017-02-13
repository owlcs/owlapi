package org.semanticweb.owlapi.gradle

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.assertTrue
/**
 * Created by ses on 2/6/17.
 */
class AetherWrapperTest {
    public static Logger logger = LoggerFactory.getLogger(AetherWrapperTest.class)
    @Test
    public void testLV() {
        AetherResolver lv = new AetherResolver("target/local-repo")
        def listOfVersions = lv.resolveVersionRange("net.sourceforge.owlapi",
                "owlapi-osgidistribution", "[0,)")
        def bar = listOfVersions.collect {it as String}
        logger.debug "{}", bar
        assertTrue("have 4.0.2",bar.contains("4.0.2"))

    }
    
}
