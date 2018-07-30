package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.model.EntityType.*;

/**
 * @author Alex To, The University Of Sydney, Falcuty of Engineering & Information Technologies
 * @since 5.1.0
 */
public enum TIMEVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Time Vocab http://www.w3.org/2006/time#

    DAY_OF_WEEK_CLASS(Namespaces.TIME, "DayOfWeek", CLASS),

    GENERAL_YEAR(Namespaces.TIME, "generalYear", CLASS),

    TEMPORAL_DURATION(Namespaces.TIME, "TemporalDuration", CLASS),

    GENERAL_DURATION_DESCRIPTION(Namespaces.TIME, "GeneralDurationDescription", CLASS),

    DURATION_DESCRIPTION(Namespaces.TIME, "DurationDescription", CLASS),

    YEAR_CLASS(Namespaces.TIME, "Year", CLASS),

    TEMPORAL_UNIT(Namespaces.TIME, "TemporalUnit", CLASS),

    DURATION(Namespaces.TIME, "Duration", CLASS),

    TEMPORAL_ENTITY(Namespaces.TIME, "TemporalEntity", CLASS),

    INSTANT(Namespaces.TIME, "Instant", CLASS),

    INTERVAL(Namespaces.TIME, "Interval", CLASS),

    PROPER_INTERVAL(Namespaces.TIME, "ProperInterval", CLASS),

    DATE_TIME_INTERVAL(Namespaces.TIME, "DateTimeInterval", CLASS),

    TEMPORAL_POSITION(Namespaces.TIME, "TemporalPosition", CLASS),

    GENERAL_DATE_TIME_DESCRIPTION(Namespaces.TIME, "GeneralDateTimeDescription", CLASS),

    DATE_TIME_DESCRIPTION(Namespaces.TIME, "DateTimeDescription", CLASS),

    MONTH_OF_YEAR_CLASS(Namespaces.TIME, "MonthOfYear", CLASS),

    JANUARY(Namespaces.TIME, "January", CLASS),

    TIME_POSITION(Namespaces.TIME, "TimePosition", CLASS),

    TEMPORAL_REFERENCE_SYSTEM(Namespaces.TIME, "TRS", CLASS),

    TIME_ZONE_CLASS(Namespaces.TIME, "TimeZone", CLASS),

    AFTER(Namespaces.TIME, "after", OBJECT_PROPERTY),

    INTERVAL_AFTER(Namespaces.TIME, "intervalAfter", OBJECT_PROPERTY),

    BEFORE(Namespaces.TIME, "intervalBefore", OBJECT_PROPERTY),

    DAY_OF_WEEK_PROPERTY(Namespaces.TIME, "dayOfWeek", OBJECT_PROPERTY),

    HAS_DATE_TIME_DESCRIPTION(Namespaces.TIME, "hasDateTimeDescription", OBJECT_PROPERTY),

    HAS_TEMPORAL_DURATION(Namespaces.TIME, "hasTemporalDuration", OBJECT_PROPERTY),

    HAS_DURATION(Namespaces.TIME, "hasDuration", OBJECT_PROPERTY),

    HAS_DURATION_DESCRIPTION(Namespaces.TIME, "hasDurationDescription", OBJECT_PROPERTY),

    HAS_TIME(Namespaces.TIME, "hasTime", OBJECT_PROPERTY),

    HAS_BEGINNING(Namespaces.TIME, "hasBeginning", OBJECT_PROPERTY),

    HAS_END(Namespaces.TIME, "hasEnd", OBJECT_PROPERTY),

    INSIDE(Namespaces.TIME, "inside", OBJECT_PROPERTY),

    TIME_ZONE_PROPERTY(Namespaces.TIME, "timeZone", OBJECT_PROPERTY),

    INTERVAL_CONTAINS(Namespaces.TIME, "intervalContains", OBJECT_PROPERTY),

    INTERVAL_DISJOINT(Namespaces.TIME, "intervalDisjoint", OBJECT_PROPERTY),

    INTERVAL_EQUALS(Namespaces.TIME, "intervalEquals", OBJECT_PROPERTY),

    INTERVAL_FINISHED_BY(Namespaces.TIME, "intervalFinishedBy", OBJECT_PROPERTY),

    INTERVAL_IN(Namespaces.TIME, "intervalIn", OBJECT_PROPERTY),

    INTERVAL_DURING(Namespaces.TIME, "intervalDuring", OBJECT_PROPERTY),

    INTERVAL_FINISHES(Namespaces.TIME, "intervalFinishes", OBJECT_PROPERTY),

    INTERVAL_STARTS(Namespaces.TIME, "intervalStarts", OBJECT_PROPERTY),

    INTERVAL_MEETS(Namespaces.TIME, "intervalMeets", OBJECT_PROPERTY),

    INTERVAL_MET_BY(Namespaces.TIME, "intervalMetBy", OBJECT_PROPERTY),

    INTERVAL_OVERLAPPED_BY(Namespaces.TIME, "intervalOverlappedBy", OBJECT_PROPERTY),

    INTERVAL_OVERLAP(Namespaces.TIME, "intervalOverlaps", OBJECT_PROPERTY),

    INTERVAL_STARTED_BY(Namespaces.TIME, "intervalStartedBy", OBJECT_PROPERTY),

    MONTH_OF_YEAR_PROPERTY(Namespaces.TIME, "monthOfYear", OBJECT_PROPERTY),

    IN_TEMPORAL_POSITION(Namespaces.TIME, "inTemporalPosition", OBJECT_PROPERTY),

    IN_DATE_TIME(Namespaces.TIME, "inDateTime", OBJECT_PROPERTY),

    IN_TIME_POSITION(Namespaces.TIME, "inTimePosition", OBJECT_PROPERTY),

    HAS_TRS(Namespaces.TIME, "hasTRS", OBJECT_PROPERTY),

    UNIT_TYPE(Namespaces.TIME, "unitType", OBJECT_PROPERTY),

    DAY(Namespaces.TIME, "day", DATA_PROPERTY),

    DAY_OF_YEAR(Namespaces.TIME, "dayOfYear", DATA_PROPERTY),

    DAYS(Namespaces.TIME, "days", DATA_PROPERTY),

    HAS_XSD_DURATION(Namespaces.TIME, "hasXSDDuration", DATA_PROPERTY),

    HOUR(Namespaces.TIME, "hour", DATA_PROPERTY),

    HOURS(Namespaces.TIME, "hours", DATA_PROPERTY),

    IN_XSD_DATE_TIME_STAMP(Namespaces.TIME, "inXSDDateTimeStamp", DATA_PROPERTY),

    IN_XSD_G_YEAR(Namespaces.TIME, "inXSDgYear", DATA_PROPERTY),

    IN_XSD_G_YEAR_MONTH(Namespaces.TIME, "inXSDgYearMonth", DATA_PROPERTY),

    MINUTE(Namespaces.TIME, "minute", DATA_PROPERTY),

    MINUTES(Namespaces.TIME, "minutes", DATA_PROPERTY),

    MONTH(Namespaces.TIME, "month", DATA_PROPERTY),

    MONTHS(Namespaces.TIME, "months", DATA_PROPERTY),

    NOMINAL_POSITION(Namespaces.TIME, "nominalPosition", DATA_PROPERTY),

    NUMERIC_DURATION(Namespaces.TIME, "numericDuration", DATA_PROPERTY),

    NUMERIC_POSITION(Namespaces.TIME, "numericPosition", DATA_PROPERTY),

    SECOND(Namespaces.TIME, "second", DATA_PROPERTY),

    SECONDS(Namespaces.TIME, "seconds", DATA_PROPERTY),

    WEEK(Namespaces.TIME, "week", DATA_PROPERTY),

    WEEKS(Namespaces.TIME, "weeks", DATA_PROPERTY),

    YEAR_PROPERTY(Namespaces.TIME, "year", DATA_PROPERTY),

    YEARS(Namespaces.TIME, "years", DATA_PROPERTY),

    XSD_DATE_TIME(Namespaces.TIME, "xsdDateTime", DATA_PROPERTY),

    IN_XSD_DATE_TIME(Namespaces.TIME, "inXSDDateTime", DATA_PROPERTY),

    UNIT_DAY(Namespaces.TIME, "unitDay", NAMED_INDIVIDUAL),

    FRIDAY(Namespaces.TIME, "Friday", NAMED_INDIVIDUAL),

    UNIT_HOUR(Namespaces.TIME, "unitHour", NAMED_INDIVIDUAL),

    UNIT_MINUTE(Namespaces.TIME, "unitMinute", NAMED_INDIVIDUAL),

    MONDAY(Namespaces.TIME, "Monday", NAMED_INDIVIDUAL),

    UNIT_MONTH(Namespaces.TIME, "unitMonth", NAMED_INDIVIDUAL),

    SATURDAY(Namespaces.TIME, "Saturday", NAMED_INDIVIDUAL),

    UNIT_SECOND(Namespaces.TIME, "unitSecond", NAMED_INDIVIDUAL),

    SUNDAY(Namespaces.TIME, "Sunday", NAMED_INDIVIDUAL),

    THURSDAY(Namespaces.TIME, "Thursday", NAMED_INDIVIDUAL),

    TUESDAY(Namespaces.TIME, "Tuesday", NAMED_INDIVIDUAL),

    WEDNESDAY(Namespaces.TIME, "Wednesday", NAMED_INDIVIDUAL),

    UNIT_WEEK(Namespaces.TIME, "unitWeek", NAMED_INDIVIDUAL),

    UNIT_YEAR(Namespaces.TIME, "unitYear", NAMED_INDIVIDUAL);

    private final IRI iri;
    private final Namespaces namespace;
    private final String shortName;
    private final String prefixedName;
    private final EntityType<?> entityType;

    TIMEVocabulary(Namespaces namespace, String shortName, EntityType<?> entityType) {
        this.namespace = namespace;
        this.shortName = shortName;
        this.prefixedName = namespace.getPrefixName() + ':' + shortName;
        this.iri = IRI.create(namespace.toString(), shortName);
        this.entityType = entityType;
    }

    @Override
    public IRI getIRI() {
        return this.iri;
    }

    @Override
    public String getPrefixedName() {
        return this.prefixedName;
    }

    @Override
    public String getShortForm() {
        return this.shortName;
    }

    public Namespaces getNamespace() {
        return this.namespace;
    }

    public EntityType<?> getEntityType() {
        return this.entityType;
    }
}
