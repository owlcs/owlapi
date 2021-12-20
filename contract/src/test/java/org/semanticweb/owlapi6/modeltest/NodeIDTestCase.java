package org.semanticweb.owlapi6.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.NodeID;

/**
 * @author ignazio
 */
class NodeIDTestCase extends TestBase {

    private static final String SOMESTRING = "somestring";

    @Test
    void shouldCreateNodeString() {
        assertEquals("_:genid30", NodeID.nodeString(30));
    }

    @Test
    void shouldCreateIRIFromNodeString() {
        assertEquals("_:genid-nodeid-somestring_", NodeID.getIRIFromNodeID("somestring_genid"));
    }

    @Test
    void shouldCreateNextIRI() {
        assertTrue(NodeID.nextAnonymousIRI().matches("_:genid[0-9]+"));
    }

    @Test
    void shouldFindAnonymousNode() {
        assertTrue(NodeID.isAnonymousNodeIRI("_:sometest_genid_something"));
        assertTrue(NodeID.isAnonymousNodeIRI("_:genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI("http://sometest_genid_something"));
        assertFalse(NodeID.isAnonymousNodeIRI((String) null));
    }

    @Test
    void shouldFindAnonymousNodeIRI() {
        assertTrue(NodeID.isAnonymousNodeIRI(iri("_:sometest_genid_", "something")));
        assertTrue(NodeID.isAnonymousNodeIRI(iri("_:genid_", "something")));
        assertFalse(NodeID.isAnonymousNodeIRI(iri("http://sometest_genid#", "something")));
        assertFalse(NodeID.isAnonymousNodeIRI((IRI) null));
    }

    @Test
    void shouldFindsharedNodeIRI() {
        assertFalse(NodeID.isAnonymousNodeID("_:sometest_genid-nodeid-_something"));
        assertTrue(NodeID.isAnonymousNodeID("_:genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID("http://sometest_genid-nodeid-_something"));
        assertFalse(NodeID.isAnonymousNodeID(null));
    }

    @Test
    void shouldbuildNode() {
        assertTrue(
            NodeID.isAnonymousNodeIRI(NodeID.getNodeID("_:sometest_genid_something").getID()));
        assertTrue(
            NodeID.isAnonymousNodeIRI(NodeID.getNodeID("http://sometest_genid_something").getID()));
        assertTrue(NodeID.isAnonymousNodeIRI(NodeID.getNodeID(null).getID()));
        NodeID idValue = NodeID.getNodeID(null);
        assertEquals(idValue.getID(), idValue.toString());
        assertEquals(NodeID.getNodeID(SOMESTRING), NodeID.getNodeID(SOMESTRING));
        assertEquals(NodeID.getNodeID(SOMESTRING).compareTo(NodeID.getNodeID("someotherstring")),
            SOMESTRING.compareTo("someotherstring"));
        assertEquals(idValue.hashCode(), idValue.toString().hashCode());
    }
}
