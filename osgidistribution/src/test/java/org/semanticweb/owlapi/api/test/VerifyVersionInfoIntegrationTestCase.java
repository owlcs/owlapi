package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.semanticweb.owlapi.test.IntegrationTest;
import org.semanticweb.owlapi.util.VersionInfo;

@SuppressWarnings("javadoc")
@Category(IntegrationTest.class)
public class VerifyVersionInfoIntegrationTestCase {

    @Test
    public void checkMatchVersion() {
        // This test will fail after setting the release version if the fallback
        // has not been updated.
        VersionInfo info = VersionInfo.getVersionInfo();
        assertEquals("3.5.5", info.getVersion());
    }
}
