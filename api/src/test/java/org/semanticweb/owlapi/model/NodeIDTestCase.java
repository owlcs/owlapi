package org.semanticweb.owlapi.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author ignazio
 */
@SuppressWarnings("javadoc")
public class NodeIDTestCase {

    @Test
    public void shouldCreateNodeString() {
        assertEquals("_:genid30", NodeID.nodeString(30));
    }

    @Test
    public void shouldCreateIRIFromNodeString() {
        assertEquals("_:genid-nodeid-somestring_",
                NodeID.getIRIFromNodeID("somestring_genid"));
    }

    @Test
    public void shouldCreateNextIRI() {
        assertTrue(NodeID.nextAnonymousIRI().matches("_:genid[0-9]+"));
    }

    @Test
    public void shouldFindAnonymousNode() {
        assertTrue(NodeID.isAnonymousNodeIRI("_:sometest_genid_something"));
        assertTrue(NodeID.isAnonymousNodeIRI("_:genid_something"));
        assertFalse(NodeID
                .isAnonymousNodeIRI("http://sometest_genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI((String) null));
    }

    @Test
    public void shouldFindAnonymousNodeIRI() {
        assertTrue(NodeID.isAnonymousNodeIRI(IRI
                .create("_:sometest_genid_something")));
        assertTrue(NodeID.isAnonymousNodeIRI(IRI.create("_:genid_something")));
        assertFalse(NodeID.isAnonymousNodeIRI(IRI
                .create("http://sometest_genid_something")));
        assertFalse(NodeID.isAnonymousNodeIRI((IRI) null));
    }

    @Test
    public void shouldFindsharedNodeIRI() {
        assertFalse(NodeID
                .isAnonymousNodeID("_:sometest_genid-nodeid-_something"));
        assertTrue(NodeID.isAnonymousNodeID("_:genid-nodeid-_something"));
        assertFalse(NodeID
                .isAnonymousNodeID("http://sometest_genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID(null));
    }

    @Test
    public void shouldbuildNode() {
        assertTrue(NodeID.isAnonymousNodeIRI(NodeID.getNodeID(
                "_:sometest_genid_something").getID()));
        assertTrue(NodeID.isAnonymousNodeIRI(NodeID.getNodeID(
                "http://sometest_genid_something").getID()));
        assertTrue(NodeID.isAnonymousNodeIRI(NodeID.getNodeID(null).getID()));
        NodeID id = NodeID.getNodeID(null);
        assertEquals(id.getID(), id.toString());
        assertEquals(NodeID.getNodeID("somestring"),
                NodeID.getNodeID("somestring"));
        assertEquals(
                NodeID.getNodeID("somestring").compareTo(
                        NodeID.getNodeID("someotherstring")),
                "somestring".compareTo("someotherstring"));
        assertEquals(id.hashCode(), id.toString().hashCode());
    }
}
