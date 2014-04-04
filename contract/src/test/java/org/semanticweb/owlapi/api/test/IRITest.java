/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

/**
 * Unit tests for the {@link IRI} class.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
@Ignore("this test takes a long time to run but in the current architecture it's only testing Guava caches")
public class IRITest {

    /** All of the unit tests individually timeout after 60 seconds. */
    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.IRI#IRI(java.lang.String)}.
     */
    @Test
    public void testIRIStringConcurrentWithCacheUse() throws Exception {
        final AtomicInteger count = new AtomicInteger(0);
        final CountDownLatch openLatch = new CountDownLatch(1);
        final int threadCount = 37;
        final CountDownLatch closeLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int number = i;
            Runnable runner = new Runnable() {

                @Override
                public void run() {
                    try {
                        openLatch.await();
                        for (int j = 0; j < 100000; j++) {
                            final int k = j % 371;
                            // final String nextPrefix = "urn:test" + k + "#";
                            // IRI.create(nextPrefix, "test");
                            final String nextUri = "urn:test" + k + "#test" + j;
                            final IRI result = IRI.create(nextUri);
                            result.hashCode();
                            // count.addAndGet(result.length());
                        }
                        count.incrementAndGet();
                        closeLatch.countDown();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                        fail("Failed in test: " + number);
                    }
                }
            };
            new Thread(runner, "TestThread" + number).start();
        }
        // all threads are waiting on the latch.
        openLatch.countDown(); // release the latch
        // all threads are now running concurrently.
        closeLatch.await();
        assertEquals(threadCount, count.get());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.IRI#IRI(java.lang.String)}.
     */
    @Test
    public void testIRIStringConcurrentNoCacheUse() throws Exception {
        final AtomicInteger count = new AtomicInteger(0);
        final CountDownLatch openLatch = new CountDownLatch(1);
        final int threadCount = 37;
        final CountDownLatch closeLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int number = i;
            Runnable runner = new Runnable() {

                @Override
                public void run() {
                    try {
                        openLatch.await();
                        for (int j = 0; j < 100000; j++) {
                            final int k = j % 371;
                            // final String nextPrefix = "urn:test" + k + "#";
                            // IRI.create(nextPrefix, "test");
                            final String nextUri = "urn:test#" + k + "test" + j;
                            final IRI result = IRI.create(nextUri);
                            result.hashCode();
                            // count.addAndGet(result.length());
                        }
                        count.incrementAndGet();
                        closeLatch.countDown();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                        fail("Failed in test: " + number);
                    }
                }
            };
            new Thread(runner, "TestThread" + number).start();
        }
        // all threads are waiting on the latch.
        openLatch.countDown(); // release the latch
        // all threads are now running concurrently.
        closeLatch.await();
        assertEquals(threadCount, count.get());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.IRI#IRI(java.lang.String)}.
     */
    @Test
    public void testIRIStringSerialWithCacheUse() {
        for (int i = 0; i < 100000; i++) {
            final int k = i % 371;
            IRI.create("urn:test" + k + "#test");
        }
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.IRI#IRI(java.lang.String)}.
     */
    @Test
    public void testIRIStringSerialNoCacheUse() {
        for (int i = 0; i < 100000; i++) {
            final int k = i % 371;
            IRI.create("urn:test#" + k + "test");
        }
    }

    @Test
    public void testEquivalentConstructors() {
        assertEquals(IRI.create("http://purl.obolibrary.org/obo/TEST_", "a"),
                IRI.create("http://purl.obolibrary.org/obo/TEST_a"));
    }

    @Test
    public void testNullConstructorSuffix() {
        assertEquals(IRI.create("http://purl.obolibrary.org/obo/TEST_a", null),
                IRI.create("http://purl.obolibrary.org/obo/TEST_a"));
    }

    @Test
    public void testNullConstructorPrefix() {
        assertEquals(IRI.create(null, "http://purl.obolibrary.org/obo/TEST_a"),
                IRI.create("http://purl.obolibrary.org/obo/TEST_a"));
    }
}
