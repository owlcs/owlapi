package org.obolibrary.oboformat.writer;

import static org.semanticweb.owlapi.model.parameters.Navigation.IN_SUB_POSITION;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * The Class OBOFormatWriter.
 *
 * @author Shahid Manzoor
 */
@ParametersAreNonnullByDefault
public class OBOFormatWriter {

    private static final Logger LOG = LoggerFactory.getLogger(OBOFormatWriter.class);
    private static final TObjectIntHashMap<String> TAGSPRIORITIES = buildTagsPriorities();
    private static final TObjectIntHashMap<String> TYPEDEFTAGSPRIORITIES =
        buildTypeDefTagsPriorities();
    private static final Comparator<Frame> framesComparator = Comparator.comparing(Frame::getId);
    private static final TObjectIntHashMap<String> HEADERTAGSPRIORITIES =
        buildHeaderTagsPriorities();
    private static final Set<String> TAGSINFORMATIVE = buildTagsInformative();
    private static final Comparator<String> headerTagsComparator =
        Comparator.comparingInt(OBOFormatWriter::getHeaderPriority);
    /**
     * This comparator sorts clauses with the same tag in the specified write order.
     */
    private static final Comparator<Clause> clauseComparator = (o1, o2) -> compare(o1, o2);
    private static Comparator<String> termsTagsComparator =
        Comparator.comparingInt(OBOFormatWriter::getPriority);
    private static Comparator<String> typeDefTagsComparator =
        Comparator.comparingInt(OBOFormatWriter::getTypedefPriority);
    private static Comparator<Clause> clauseListComparator =
        Comparator.comparing(Clause::getTag, termsTagsComparator).thenComparing(clauseComparator);
    private boolean isCheckStructure = true;

    private static int getHeaderPriority(String s) {
        return actualGet(s, HEADERTAGSPRIORITIES);
    }

    protected static int actualGet(String s, TObjectIntHashMap<String> map) {
        int i = map.get(s);
        if (i == map.getNoEntryValue()) {
            return 10000;
        }
        return i;
    }

    private static int getPriority(String s) {
        return actualGet(s, TAGSPRIORITIES);
    }

    private static int getTypedefPriority(String s) {
        return actualGet(s, TYPEDEFTAGSPRIORITIES);
    }

    private static Set<String> buildTagsInformative() {
        Set<String> set = new HashSet<>();
        set.add(OboFormatTag.TAG_IS_A.getTag());
        set.add(OboFormatTag.TAG_RELATIONSHIP.getTag());
        set.add(OboFormatTag.TAG_DISJOINT_FROM.getTag());
        set.add(OboFormatTag.TAG_INTERSECTION_OF.getTag());
        set.add(OboFormatTag.TAG_UNION_OF.getTag());
        set.add(OboFormatTag.TAG_EQUIVALENT_TO.getTag());
        // removed OboFormatTag.TAG_REPLACED_BY to be compatible with OBO-Edit
        set.add(OboFormatTag.TAG_PROPERTY_VALUE.getTag());
        set.add(OboFormatTag.TAG_DOMAIN.getTag());
        set.add(OboFormatTag.TAG_RANGE.getTag());
        set.add(OboFormatTag.TAG_INVERSE_OF.getTag());
        set.add(OboFormatTag.TAG_TRANSITIVE_OVER.getTag());
        // removed OboFormatTag.TAG_HOLDS_OVER_CHAIN to be compatible with
        // OBO-Edit
        set.add(OboFormatTag.TAG_EQUIVALENT_TO_CHAIN.getTag());
        set.add(OboFormatTag.TAG_DISJOINT_OVER.getTag());
        return set;
    }

    private static void writeLine(StringBuilder ln, Writer writer) throws IOException {
        ln.append('\n');
        writer.write(ln.toString());
    }

    private static void writeLine(String ln, Writer writer) throws IOException {
        writer.write(ln + '\n');
    }

    private static void writeEmptyLine(Writer writer) throws IOException {
        writer.write("\n");
    }

    private static List<String> duplicateTags(Set<String> src) {
        return new ArrayList<>(src);
    }

    private static void writeXRefClause(Clause clause, Writer writer) throws IOException {
        Xref xref = clause.getValue(Xref.class);
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        String idref = xref.getIdref();
        int colonPos = idref.indexOf(':');
        if (colonPos > 0) {
            sb.append(escapeOboString(idref.substring(0, colonPos), EscapeMode.XREF));
            sb.append(':');
            sb.append(escapeOboString(idref.substring(colonPos + 1), EscapeMode.XREF));
        } else {
            sb.append(escapeOboString(idref, EscapeMode.XREF));
        }
        String annotation = xref.getAnnotation();
        if (annotation != null) {
            sb.append(" \"");
            sb.append(escapeOboString(annotation, EscapeMode.QUOTES));
            sb.append('"');
        }
        appendQualifiers(sb, clause);
        writeLine(sb, writer);
    }

    private static void writeSynonymtypedef(Clause clause, Writer writer) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        Iterator<Object> valuesIterator = clause.getValues().iterator();
        Collection<?> values = clause.getValues();
        for (int i = 0; i < values.size(); i++) {
            String value = valuesIterator.next().toString();
            if (i == 1) {
                sb.append('"');
            }
            sb.append(escapeOboString(value, EscapeMode.QUOTES));
            if (i == 1) {
                sb.append('"');
            }
            if (valuesIterator.hasNext()) {
                sb.append(' ');
            }
        }
        appendQualifiers(sb, clause);
        writeLine(sb, writer);
    }

    private static void writeHeaderDate(Clause clause, Writer writer) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        Object value = clause.getValue();
        if (value instanceof Date) {
            sb.append(OBOFormatConstants.headerDateFormat().format((Date) value));
        } else if (value instanceof String) {
            sb.append(value);
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Unknown datatype ('{}') for value in clause: {}",
                    value.getClass().getName(), clause);
                sb.append(value);
            }
        }
        writeLine(sb, writer);
    }

    private static void writeIdSpace(Clause cl, Writer writer) throws IOException {
        StringBuilder sb = new StringBuilder(cl.getTag());
        sb.append(": ");
        Collection<Object> values = cl.getValues();
        int i = 0;
        Iterator<Object> iterator = values.iterator();
        while (iterator.hasNext() && i < 3) {
            String value = iterator.next().toString();
            if (i == 2) {
                sb.append('"').append(escapeOboString(value, EscapeMode.QUOTES)).append('"');
            } else {
                sb.append(escapeOboString(value, EscapeMode.SIMPLE)).append(' ');
            }
            i++;
        }
        appendQualifiers(sb, cl);
        writeLine(sb, writer);
    }

    private static void writeClauseWithQuotedString(Clause clause, Writer writer)
        throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        boolean first = true;
        Iterator<Object> valuesIterator = clause.getValues().iterator();
        while (valuesIterator.hasNext()) {
            if (first) {
                sb.append('"');
            }
            String value = valuesIterator.next().toString();
            sb.append(escapeOboString(value, EscapeMode.QUOTES));
            if (first) {
                sb.append('"');
            }
            if (valuesIterator.hasNext()) {
                sb.append(' ');
            }
            first = false;
        }
        Collection<Xref> xrefs = clause.getXrefs();
        // if the xrefs value is null, then there should *never* be xrefs at
        // this location
        // not that the value may be a non-null empty list - here we still want
        // to write []
        if (!xrefs.isEmpty()) {
            appendXrefs(sb, xrefs);
        } else if (OboFormatTag.TAG_DEF.getTag().equals(clause.getTag())
            || OboFormatTag.TAG_SYNONYM.getTag().equals(clause.getTag())
            || OboFormatTag.TAG_EXPAND_EXPRESSION_TO.getTag().equals(clause.getTag())
            || OboFormatTag.TAG_EXPAND_ASSERTION_TO.getTag().equals(clause.getTag())) {
            sb.append(" []");
        }
        appendQualifiers(sb, clause);
        writeLine(sb, writer);
    }

    private static void appendXrefs(StringBuilder sb, Collection<Xref> xrefs) {
        List<Xref> sortedXrefs = new ArrayList<>(xrefs);
        Collections.sort(sortedXrefs, XrefComparator.INSTANCE);
        sb.append(" [");
        Iterator<Xref> xrefsIterator = sortedXrefs.iterator();
        while (xrefsIterator.hasNext()) {
            Xref current = xrefsIterator.next();
            String idref = current.getIdref();
            int colonPos = idref.indexOf(':');
            if (colonPos > 0) {
                sb.append(escapeOboString(idref.substring(0, colonPos), EscapeMode.XREFLIST));
                sb.append(':');
                sb.append(escapeOboString(idref.substring(colonPos + 1), EscapeMode.XREFLIST));
            } else {
                sb.append(escapeOboString(idref, EscapeMode.XREFLIST));
            }
            String annotation = current.getAnnotation();
            if (annotation != null) {
                sb.append(' ');
                sb.append('"');
                sb.append(escapeOboString(annotation, EscapeMode.QUOTES));
                sb.append('"');
            }
            if (xrefsIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    /**
     * Write def.
     *
     * @param clause the clause
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeDef(Clause clause, Writer writer) throws IOException {
        writeClauseWithQuotedString(clause, writer);
    }

    /**
     * Write property value.
     *
     * @param clause the clause
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertyValue(Clause clause, Writer writer) throws IOException {
        Collection<?> cols = clause.getValues();
        if (cols.size() < 2) {
            LOG.error("The {} has incorrect number of values: {}",
                OboFormatTag.TAG_PROPERTY_VALUE.getTag(), clause);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        Iterator<?> it = cols.iterator();
        // write property
        // TODO replace toString() method
        String property = it.next().toString();
        sb.append(escapeOboString(property, EscapeMode.SIMPLE));
        // write value and optional type
        while (it.hasNext()) {
            sb.append(' ');
            String val = it.next().toString(); // TODO replace toString() method
            if (val.contains(" ") || !val.contains(":")) {
                sb.append('"');
                sb.append(escapeOboString(val, EscapeMode.QUOTES));
                sb.append('"');
            } else {
                sb.append(escapeOboString(val, EscapeMode.SIMPLE));
            }
        }
        appendQualifiers(sb, clause);
        writeLine(sb, writer);
    }

    /**
     * Write synonym.
     *
     * @param clause the clause
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeSynonym(Clause clause, Writer writer) throws IOException {
        writeClauseWithQuotedString(clause, writer);
    }

    /**
     * Write.
     *
     * @param clause the clause
     * @param writer the writer
     * @param nameProvider the name provider
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void write(Clause clause, Writer writer, @Nullable NameProvider nameProvider)
        throws IOException {
        if (OboFormatTag.TAG_IS_OBSELETE.getTag().equals(clause.getTag())) {
            // only write the obsolete tag if the value is Boolean.TRUE or
            // "true"
            Object value = clause.getValue();
            if (value instanceof Boolean) {
                if (Boolean.FALSE.equals(value)) {
                    return;
                }
            } else {
                // also check for a String representation of Boolean.TRUE
                if (!Boolean.TRUE.toString().equals(value)) {
                    return;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(clause.getTag());
        sb.append(": ");
        Iterator<Object> valuesIterator = clause.getValues().iterator();
        StringBuilder idsLabel = null;
        if (nameProvider != null && TAGSINFORMATIVE.contains(clause.getTag())) {
            idsLabel = new StringBuilder();
        }
        while (valuesIterator.hasNext()) {
            String value = valuesIterator.next().toString();
            if (idsLabel != null && nameProvider != null) {
                String label = nameProvider.getName(value);
                if (label != null && (isOpaqueIdentifier(value) || !valuesIterator.hasNext())) {
                    // only print label if the label exists
                    // and the label is different from the id
                    // relationships: ID part_of LABEL part_of
                    if (idsLabel.length() > 0) {
                        idsLabel.append(' ');
                    }
                    idsLabel.append(label);
                }
            }
            EscapeMode mode = EscapeMode.MOST;
            if (OboFormatTag.TAG_COMMENT.getTag().equals(clause.getTag())) {
                mode = EscapeMode.PARENTHESIS;
            }
            sb.append(escapeOboString(value, mode));
            if (valuesIterator.hasNext()) {
                sb.append(' ');
            }
        }
        Collection<Xref> xrefs = clause.getXrefs();
        if (!xrefs.isEmpty()) {
            appendXrefs(sb, xrefs);
        }
        appendQualifiers(sb, clause);
        if (idsLabel != null && idsLabel.length() > 0) {
            String trimmed = idsLabel.toString().trim();
            if (!trimmed.isEmpty()) {
                sb.append(" ! ");
                sb.append(trimmed);
            }
        }
        writeLine(sb, writer);
    }

    private static boolean isOpaqueIdentifier(@Nullable String value) {
        boolean result = false;
        if (value != null && !value.isEmpty()) {
            // check for colon
            int colonPos = value.indexOf(':');
            // check that the suffix after the colon contains only digits
            if (colonPos > 0 && value.length() > colonPos + 1) {
                result = true;
                for (int i = colonPos; i < value.length(); i++) {
                    char c = value.charAt(i);
                    if (!Character.isDigit(c) && c != ':') {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private static void appendQualifiers(StringBuilder sb, Clause clause) {
        Collection<QualifierValue> qvs = clause.getQualifierValues();
        if (!qvs.isEmpty()) {
            sb.append(" {");
            Iterator<QualifierValue> qvsIterator = qvs.iterator();
            while (qvsIterator.hasNext()) {
                QualifierValue qv = qvsIterator.next();
                sb.append(qv.getQualifier());
                sb.append("=\"");
                sb.append(escapeOboString(qv.getValue(), EscapeMode.QUOTES));
                sb.append('"');
                if (qvsIterator.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append('}');
        }
    }

    private static CharSequence escapeOboString(String in, EscapeMode mode) {
        String replace = in.replace("\\", "\\\\");
        if (mode == EscapeMode.MOST || mode == EscapeMode.QUOTES) {
            replace = replace.replace("\"", "\\\"");
        }
        replace = replace.replace("\n", "\\n");
        if (mode == EscapeMode.MOST || mode == EscapeMode.PARENTHESIS) {
            replace = replace.replace("{", "\\{");
        }
        if (mode == EscapeMode.XREF || mode == EscapeMode.XREFLIST) {
            replace = replace.replace(",", "\\,").replace(":", "\\:");
        }
        if (mode == EscapeMode.XREFLIST) {
            replace = replace.replace("]", "\\]");
        }
        return replace;
    }

    private static TObjectIntHashMap<String> buildHeaderTagsPriorities() {
        TObjectIntHashMap<String> table = new TObjectIntHashMap<>();
        table.put(OboFormatTag.TAG_FORMAT_VERSION.getTag(), 0);
        table.put(OboFormatTag.TAG_DATA_VERSION.getTag(), 10);
        table.put(OboFormatTag.TAG_DATE.getTag(), 15);
        table.put(OboFormatTag.TAG_SAVED_BY.getTag(), 20);
        table.put(OboFormatTag.TAG_AUTO_GENERATED_BY.getTag(), 25);
        table.put(OboFormatTag.TAG_SUBSETDEF.getTag(), 35);
        table.put(OboFormatTag.TAG_SYNONYMTYPEDEF.getTag(), 40);
        table.put(OboFormatTag.TAG_DEFAULT_NAMESPACE.getTag(), 45);
        table.put(OboFormatTag.TAG_NAMESPACE_ID_RULE.getTag(), 46);
        table.put(OboFormatTag.TAG_IDSPACE.getTag(), 50);
        table.put(OboFormatTag.TAG_TREAT_XREFS_AS_EQUIVALENT.getTag(), 55);
        table.put(OboFormatTag.TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA.getTag(), 60);
        table.put(OboFormatTag.TAG_TREAT_XREFS_AS_RELATIONSHIP.getTag(), 65);
        table.put(OboFormatTag.TAG_TREAT_XREFS_AS_IS_A.getTag(), 70);
        table.put(OboFormatTag.TAG_REMARK.getTag(), 75);
        // moved from pos 30 to emulate OBO-Edit behavior
        table.put(OboFormatTag.TAG_IMPORT.getTag(), 80);
        // moved from pos 5 to emulate OBO-Edit behavior
        table.put(OboFormatTag.TAG_ONTOLOGY.getTag(), 85);
        table.put(OboFormatTag.TAG_PROPERTY_VALUE.getTag(), 100);
        table.put(OboFormatTag.TAG_OWL_AXIOMS.getTag(), 110);
        return table;
    }

    private static TObjectIntHashMap<String> buildTagsPriorities() {
        TObjectIntHashMap<String> table = new TObjectIntHashMap<>();
        table.put(OboFormatTag.TAG_ID.getTag(), 5);
        table.put(OboFormatTag.TAG_IS_ANONYMOUS.getTag(), 10);
        table.put(OboFormatTag.TAG_NAME.getTag(), 15);
        table.put(OboFormatTag.TAG_NAMESPACE.getTag(), 20);
        table.put(OboFormatTag.TAG_ALT_ID.getTag(), 25);
        table.put(OboFormatTag.TAG_DEF.getTag(), 30);
        table.put(OboFormatTag.TAG_COMMENT.getTag(), 35);
        table.put(OboFormatTag.TAG_SUBSET.getTag(), 40);
        table.put(OboFormatTag.TAG_SYNONYM.getTag(), 45);
        table.put(OboFormatTag.TAG_XREF.getTag(), 50);
        table.put(OboFormatTag.TAG_BUILTIN.getTag(), 55);
        table.put(OboFormatTag.TAG_HOLDS_OVER_CHAIN.getTag(), 60);
        table.put(OboFormatTag.TAG_IS_A.getTag(), 65);
        table.put(OboFormatTag.TAG_INTERSECTION_OF.getTag(), 70);
        table.put(OboFormatTag.TAG_UNION_OF.getTag(), 80);
        table.put(OboFormatTag.TAG_EQUIVALENT_TO.getTag(), 85);
        table.put(OboFormatTag.TAG_DISJOINT_FROM.getTag(), 90);
        table.put(OboFormatTag.TAG_RELATIONSHIP.getTag(), 95);
        table.put(OboFormatTag.TAG_PROPERTY_VALUE.getTag(), 98);
        table.put(OboFormatTag.TAG_IS_OBSELETE.getTag(), 110);
        table.put(OboFormatTag.TAG_REPLACED_BY.getTag(), 115);
        table.put(OboFormatTag.TAG_CONSIDER.getTag(), 120);
        table.put(OboFormatTag.TAG_CREATED_BY.getTag(), 130);
        table.put(OboFormatTag.TAG_CREATION_DATE.getTag(), 140);
        return table;
    }

    private static TObjectIntHashMap<String> buildTypeDefTagsPriorities() {
        TObjectIntHashMap<String> table = new TObjectIntHashMap<>();
        table.put(OboFormatTag.TAG_ID.getTag(), 5);
        table.put(OboFormatTag.TAG_IS_ANONYMOUS.getTag(), 10);
        table.put(OboFormatTag.TAG_NAME.getTag(), 15);
        table.put(OboFormatTag.TAG_NAMESPACE.getTag(), 20);
        table.put(OboFormatTag.TAG_ALT_ID.getTag(), 25);
        table.put(OboFormatTag.TAG_DEF.getTag(), 30);
        table.put(OboFormatTag.TAG_COMMENT.getTag(), 35);
        table.put(OboFormatTag.TAG_SUBSET.getTag(), 40);
        table.put(OboFormatTag.TAG_SYNONYM.getTag(), 45);
        table.put(OboFormatTag.TAG_XREF.getTag(), 50);
        table.put(OboFormatTag.TAG_PROPERTY_VALUE.getTag(), 55);
        table.put(OboFormatTag.TAG_DOMAIN.getTag(), 60);
        table.put(OboFormatTag.TAG_RANGE.getTag(), 65);
        table.put(OboFormatTag.TAG_BUILTIN.getTag(), 70);
        table.put(OboFormatTag.TAG_HOLDS_OVER_CHAIN.getTag(), 71);
        table.put(OboFormatTag.TAG_IS_ANTI_SYMMETRIC.getTag(), 75);
        table.put(OboFormatTag.TAG_IS_CYCLIC.getTag(), 80);
        table.put(OboFormatTag.TAG_IS_REFLEXIVE.getTag(), 85);
        table.put(OboFormatTag.TAG_IS_SYMMETRIC.getTag(), 90);
        table.put(OboFormatTag.TAG_IS_TRANSITIVE.getTag(), 100);
        table.put(OboFormatTag.TAG_IS_FUNCTIONAL.getTag(), 105);
        table.put(OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL.getTag(), 110);
        table.put(OboFormatTag.TAG_IS_A.getTag(), 115);
        table.put(OboFormatTag.TAG_INTERSECTION_OF.getTag(), 120);
        table.put(OboFormatTag.TAG_UNION_OF.getTag(), 125);
        table.put(OboFormatTag.TAG_EQUIVALENT_TO.getTag(), 130);
        table.put(OboFormatTag.TAG_DISJOINT_FROM.getTag(), 135);
        table.put(OboFormatTag.TAG_INVERSE_OF.getTag(), 140);
        table.put(OboFormatTag.TAG_TRANSITIVE_OVER.getTag(), 145);
        table.put(OboFormatTag.TAG_EQUIVALENT_TO_CHAIN.getTag(), 155);
        table.put(OboFormatTag.TAG_DISJOINT_OVER.getTag(), 160);
        table.put(OboFormatTag.TAG_RELATIONSHIP.getTag(), 165);
        table.put(OboFormatTag.TAG_IS_OBSELETE.getTag(), 169);
        table.put(OboFormatTag.TAG_REPLACED_BY.getTag(), 185);
        table.put(OboFormatTag.TAG_CONSIDER.getTag(), 190);
        table.put(OboFormatTag.TAG_CREATED_BY.getTag(), 191);
        table.put(OboFormatTag.TAG_CREATION_DATE.getTag(), 192);
        table.put(OboFormatTag.TAG_EXPAND_ASSERTION_TO.getTag(), 195);
        table.put(OboFormatTag.TAG_EXPAND_EXPRESSION_TO.getTag(), 200);
        table.put(OboFormatTag.TAG_IS_METADATA_TAG.getTag(), 205);
        table.put(OboFormatTag.TAG_IS_CLASS_LEVEL_TAG.getTag(), 210);
        return table;
    }

    /**
     * Sort a list of term frame clauses according to in the OBO format specified tag and value
     * order.
     *
     * @param clauses the clauses
     */
    public static void sortTermClauses(List<Clause> clauses) {
        Collections.sort(clauses, clauseListComparator);
    }

    private static int compare(Clause o1, Clause o2) {
        // special case for intersections
        String tag = o1.getTag();
        if (OboFormatTag.TAG_INTERSECTION_OF.getTag().equals(tag)) {
            // sort by values size, prefer short ones.
            int s1 = o1.getValues().size();
            int s2 = o2.getValues().size();
            if (s1 < s2) {
                return -1;
            } else if (s1 > s2) {
                return 1;
            }
        }
        // sort by value
        int comp = compareValues(o1.getValue(), o2.getValue());
        if (comp != 0) {
            return comp;
        }
        return compareValues(o1.getValue2(), o2.getValue2());
    }

    /**
     * Compare values.
     *
     * @param o1 the o1
     * @param o2 the o2
     * @return the int
     */
    private static int compareValues(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        String s1 = toStringRepresentation(o1);
        String s2 = toStringRepresentation(o2);
        int comp = s1.compareToIgnoreCase(s2);
        if (comp == 0) {
            // normally ignore case, for sorting
            // but if the strings are equal,
            // try again with case check
            comp = s1.compareTo(s2);
        }
        return comp;
    }

    /**
     * @param obj the obj
     * @return toString representation
     */
    private static String toStringRepresentation(@Nullable Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Xref) {
            Xref xref = (Xref) obj;
            return xref.getIdref() + ' ' + xref.getAnnotation();
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    /**
     * @return true, if is check structure
     */
    public boolean isCheckStructure() {
        return isCheckStructure;
    }

    /**
     * @param isCheckStructure the new check structure
     */
    public void setCheckStructure(boolean isCheckStructure) {
        this.isCheckStructure = isCheckStructure;
    }

    /**
     * @param fn the file name to read in
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OBOFormatParserException the oBO format parser exception
     */
    public void write(String fn, Writer writer) throws IOException {
        AtomicReference<OBODoc> doc = new AtomicReference<>();
        OWLParser parser = new OBOFormatOWLAPIParser((o, d) -> doc.set(d));
        new IRIDocumentSource(IRI.create(fn)).acceptParser(parser, null,
            new OntologyConfigurator());
        write(doc.get(), writer);
    }

    /**
     * Write.
     *
     * @param url the url
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OBOFormatParserException the oBO format parser exception
     */
    public void write(InputStream url, Writer writer) throws IOException {
        write(new BufferedReader(new InputStreamReader(url)), writer);
    }

    /**
     * Write.
     *
     * @param url the url
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OBOFormatParserException the oBO format parser exception
     */
    public void write(URL url, Writer writer) throws IOException {
        write(url.toString(), writer);
    }

    /**
     * @param reader the reader
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OBOFormatParserException the oBO format parser exception
     */
    public void write(Reader reader, Writer writer) throws IOException {
        OBOFormatParser parser = new OBOFormatParser();
        OBODoc doc = parser.parse(reader);
        write(doc, writer);
    }

    /**
     * @param doc the doc
     * @param outFilename the out file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(OBODoc doc, String outFilename) throws IOException {
        write(doc, new File(outFilename));
    }

    /**
     * @param doc the doc
     * @param outFile the out file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(OBODoc doc, File outFile) throws IOException {
        try (FileOutputStream os = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            Writer bw = new BufferedWriter(osw);) {
            write(doc, bw);
        }
    }

    /**
     * @param doc the doc
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(OBODoc doc, Writer writer) throws IOException {
        NameProvider nameProvider = new OBODocNameProvider(doc);
        write(doc, writer, nameProvider);
    }

    /**
     * @param doc the doc
     * @param writer the writer
     * @param nameProvider the name provider
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(OBODoc doc, Writer writer, NameProvider nameProvider) throws IOException {
        if (isCheckStructure) {
            doc.check();
        }
        Frame headerFrame = doc.getHeaderFrame();
        writeHeader(checkNotNull(headerFrame), writer, nameProvider);
        List<Frame> termFrames = new ArrayList<>();
        termFrames.addAll(doc.getTermFrames());
        Collections.sort(termFrames, framesComparator);
        List<Frame> typeDefFrames = new ArrayList<>();
        typeDefFrames.addAll(doc.getTypedefFrames());
        Collections.sort(typeDefFrames, framesComparator);
        List<Frame> instanceFrames = new ArrayList<>();
        typeDefFrames.addAll(doc.getInstanceFrames());
        Collections.sort(instanceFrames, framesComparator);
        for (Frame f : termFrames) {
            write(f, writer, nameProvider);
        }
        for (Frame f : typeDefFrames) {
            write(f, writer, nameProvider);
        }
        for (Frame f : instanceFrames) {
            write(f, writer, nameProvider);
        }
        // to be save always flush writer
        writer.flush();
    }

    /**
     * Write header.
     *
     * @param frame the frame
     * @param writer the writer
     * @param nameProvider the name provider
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void writeHeader(Frame frame, Writer writer, NameProvider nameProvider)
        throws IOException {
        List<String> tags = duplicateTags(frame.getTags());
        Collections.sort(tags, headerTagsComparator);
        write(new Clause(OboFormatTag.TAG_FORMAT_VERSION.getTag(), "1.2"), writer, nameProvider);
        for (String tag : tags) {
            if (tag.equals(OboFormatTag.TAG_FORMAT_VERSION.getTag())) {
                continue;
            }
            List<Clause> clauses = new ArrayList<>(frame.getClauses(tag));
            Collections.sort(clauses, clauseComparator);
            for (Clause clause : clauses) {
                if (tag.equals(OboFormatTag.TAG_SUBSETDEF.getTag())) {
                    writeSynonymtypedef(clause, writer);
                } else if (tag.equals(OboFormatTag.TAG_SYNONYMTYPEDEF.getTag())) {
                    writeSynonymtypedef(clause, writer);
                } else if (tag.equals(OboFormatTag.TAG_DATE.getTag())) {
                    writeHeaderDate(clause, writer);
                } else if (tag.equals(OboFormatTag.TAG_PROPERTY_VALUE.getTag())) {
                    writePropertyValue(clause, writer);
                } else if (tag.equals(OboFormatTag.TAG_IDSPACE.getTag())) {
                    writeIdSpace(clause, writer);
                } else {
                    write(clause, writer, nameProvider);
                }
            }
        }
        writeEmptyLine(writer);
    }

    /**
     * @param frame the frame
     * @param writer the writer
     * @param nameProvider the name provider
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(Frame frame, Writer writer, @Nullable NameProvider nameProvider)
        throws IOException {
        Comparator<String> comparator = null;
        if (frame.getType() == FrameType.TERM) {
            writeLine("[Term]", writer);
            comparator = termsTagsComparator;
        } else if (frame.getType() == FrameType.TYPEDEF) {
            writeLine("[Typedef]", writer);
            comparator = typeDefTagsComparator;
        } else if (frame.getType() == FrameType.INSTANCE) {
            writeLine("[Instance]", writer);
            comparator = typeDefTagsComparator;
        }
        String id = frame.getId();
        if (id != null) {
            Object label = frame.getTagValue(OboFormatTag.TAG_NAME);
            String extra = "";
            if (label == null && nameProvider != null) {
                // the name clause may not be present in this OBODoc - however,
                // the name provider may be able to provide one, in which case,
                // we
                // write it as a parser-invisible comment, thus preserving the
                // document structure but providing useful information for any
                // person that inspects the obo file
                label = nameProvider.getName(id);
                if (label != null) {
                    extra = " ! " + label;
                }
            }
            writeLine(OboFormatTag.TAG_ID.getTag() + ": " + id + extra, writer);
        }
        List<String> tags = duplicateTags(frame.getTags());
        Collections.sort(tags, comparator);
        String defaultOboNamespace = null;
        if (nameProvider != null) {
            defaultOboNamespace = nameProvider.getDefaultOboNamespace();
        }
        for (String tag : tags) {
            List<Clause> clauses = new ArrayList<>(frame.getClauses(tag));
            Collections.sort(clauses, clauseComparator);
            for (Clause clause : clauses) {
                String clauseTag = clause.getTag();
                if (OboFormatTag.TAG_ID.getTag().equals(clauseTag)) {
                    continue;
                } else if (OboFormatTag.TAG_DEF.getTag().equals(clauseTag)) {
                    writeDef(clause, writer);
                } else if (OboFormatTag.TAG_SYNONYM.getTag().equals(clauseTag)) {
                    writeSynonym(clause, writer);
                } else if (OboFormatTag.TAG_PROPERTY_VALUE.getTag().equals(clauseTag)) {
                    writePropertyValue(clause, writer);
                } else if (OboFormatTag.TAG_EXPAND_EXPRESSION_TO.getTag().equals(clauseTag)
                    || OboFormatTag.TAG_EXPAND_ASSERTION_TO.getTag().equals(clauseTag)) {
                    writeClauseWithQuotedString(clause, writer);
                } else if (OboFormatTag.TAG_XREF.getTag().equals(clauseTag)) {
                    writeXRefClause(clause, writer);
                } else if (OboFormatTag.TAG_NAMESPACE.getTag().equals(clauseTag)) {
                    // only write OBO namespace,
                    // if it is different from the default OBO namespace
                    if (defaultOboNamespace == null
                        || !clause.getValue().equals(defaultOboNamespace)) {
                        write(clause, writer, nameProvider);
                    }
                } else {
                    write(clause, writer, nameProvider);
                }
            }
        }
        writeEmptyLine(writer);
    }

    /** The Enum EscapeMode. */
    private enum EscapeMode {
        //@formatter:off
        /** all except xref and xrefList. */    MOST,
        /** simple + parenthesis. */            PARENTHESIS,
        /** simple + quotes. */                 QUOTES,
        /** simple + comma + colon. */          XREF,
        /** xref + closing brackets. */         XREFLIST,
        /** newline and backslash. */           SIMPLE
        //@formatter:on
    }

    /**
     * Provide names for given OBO identifiers. This abstraction layer allows to find names from
     * different sources, including {@link OBODoc}.
     */
    public interface NameProvider {

        /**
         * Try to retrieve the valid name for the given identifier. If not available return null.
         *
         * @param id identifier
         * @return name or null
         */
        @Nullable
        String getName(String id);

        /**
         * Retrieve the default OBO namespace.
         *
         * @return default OBO namespace or null
         */
        @Nullable
        String getDefaultOboNamespace();
    }

    /**
     * The Class XrefComparator.
     */
    private static class XrefComparator implements Comparator<Xref>, Serializable {

        static final XrefComparator INSTANCE = new XrefComparator();

        @Override
        public int compare(@Nullable Xref o1, @Nullable Xref o2) {
            String idref1 = checkNotNull(o1).getIdref();
            String idref2 = checkNotNull(o2).getIdref();
            return idref1.compareToIgnoreCase(idref2);
        }
    }

    /**
     * Default implementation of a {@link NameProvider} using an underlying. {@link OBODoc}.
     */
    public static class OBODocNameProvider implements NameProvider {

        private final OBODoc oboDoc;
        @Nullable
        private final String defaultOboNamespace;

        /**
         * Instantiates a new OBO doc name provider.
         *
         * @param oboDoc the obo doc
         */
        public OBODocNameProvider(OBODoc oboDoc) {
            this.oboDoc = oboDoc;
            Frame headerFrame = oboDoc.getHeaderFrame();
            if (headerFrame != null) {
                defaultOboNamespace =
                    headerFrame.getTagValue(OboFormatTag.TAG_DEFAULT_NAMESPACE, String.class);
            } else {
                defaultOboNamespace = null;
            }
        }

        @Override
        @Nullable
        public String getName(String id) {
            String name = null;
            Frame frame = oboDoc.getTermFrame(id);
            if (frame == null) {
                frame = oboDoc.getTypedefFrame(id);
            }
            if (frame != null) {
                Clause cl = frame.getClause(OboFormatTag.TAG_NAME);
                if (cl != null) {
                    name = cl.getValue(String.class);
                }
            }
            return name;
        }

        @Override
        @Nullable
        public String getDefaultOboNamespace() {
            return defaultOboNamespace;
        }
    }

    /**
     * Alternative implementation to lookup labels in an {@link OWLOntology}. <br>
     * This implementation might be a bit slower as it involves additional id conversion back into
     * OWL.
     */
    public static class OWLOntologyNameProvider implements NameProvider {

        private final OWLOntology ont;
        @Nullable
        private final String defaultOboNamespace;

        /**
         * @param ont ontology
         * @param defaultOboNamespace default OBO namespace
         */
        public OWLOntologyNameProvider(OWLOntology ont, @Nullable String defaultOboNamespace) {
            this.ont = ont;
            this.defaultOboNamespace = defaultOboNamespace;
        }

        @Override
        @Nullable
        public String getName(String id) {
            // convert OBO id to IRI
            OWLAPIObo2Owl obo2owl = new OWLAPIObo2Owl(ont.getOWLOntologyManager());
            IRI iri = obo2owl.oboIdToIRI(id);
            // look for label of entity
            List<OWLAnnotationAssertionAxiom> axioms =
                asList(ont.axioms(OWLAnnotationAssertionAxiom.class, OWLAnnotationSubject.class,
                    iri, Imports.INCLUDED, IN_SUB_POSITION));
            for (OWLAnnotationAssertionAxiom axiom : axioms) {
                if (axiom.getProperty().isLabel()) {
                    OWLAnnotationValue value = axiom.getValue();
                    if (value instanceof OWLLiteral) {
                        return ((OWLLiteral) value).getLiteral();
                    }
                }
            }
            return null;
        }

        @Override
        @Nullable
        public String getDefaultOboNamespace() {
            return defaultOboNamespace;
        }
    }
}
