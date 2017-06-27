package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.model.EntityType.*;

/**
 * @author Alex To, The University Of Sydney, School of Engineering & Information Technologies
 * @since 5.1.0
 */
public enum TIMEVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Time Vocab http://www.w3.org/2006/time#

    TIME_DAY_OF_WEEK_CLASS(Namespaces.TIME, "DayOfWeek", CLASS),

    TIME_GENERAL_YEAR(Namespaces.TIME, "generalYear", CLASS),

    TIME_TEMPORAL_DURATION(Namespaces.TIME, "TemporalDuration", CLASS),

    TIME_GENERAL_DURATION_DESCRIPTION(Namespaces.TIME, "GeneralDurationDescription", CLASS),

    TIME_DURATION_DESCRIPTION(Namespaces.TIME, "DurationDescription", CLASS),

    TIME_YEAR_CLASS(Namespaces.TIME, "Year", CLASS),

    TIME_TEMPORAL_UNIT(Namespaces.TIME, "TemporalUnit", CLASS),

    TIME_DURATION(Namespaces.TIME, "Duration", CLASS),

    TIME_TEMPORAL_ENTITY(Namespaces.TIME, "TemporalEntity", CLASS),

    TIME_INSTANT(Namespaces.TIME, "Instant", CLASS),

    TIME_INTERVAL(Namespaces.TIME, "Interval", CLASS),

    TIME_PROPER_INTERVAL(Namespaces.TIME, "ProperInterval", CLASS),

    TIME_DATE_TIME_INTERVAL(Namespaces.TIME, "DateTimeInterval", CLASS),

    TIME_TEMPORAL_POSITION(Namespaces.TIME, "TemporalPosition", CLASS),

    TIME_GENERAL_DATE_TIME_DESCRIPTION(Namespaces.TIME, "GeneralDateTimeDescription", CLASS),

    TIME_DATE_TIME_DESCRIPTION(Namespaces.TIME, "DateTimeDescription", CLASS),

    TIME_MONTH_OF_YEAR_CLASS(Namespaces.TIME, "MonthOfYear", CLASS),

    TIME_JANUARY(Namespaces.TIME, "January", CLASS),

    TIME_TIME_POSITION(Namespaces.TIME, "TimePosition", CLASS),

    TIME_TEMPORAL_REFERENCE_SYSTEM(Namespaces.TIME, "TRS", CLASS),

    TIME_TIME_ZONE_CLASS(Namespaces.TIME, "TimeZone", CLASS),

    TIME_AFTER(Namespaces.TIME, "after", OBJECT_PROPERTY),

    TIME_INTERVAL_AFTER(Namespaces.TIME, "intervalAfter", OBJECT_PROPERTY),

    TIME_BEFORE(Namespaces.TIME, "intervalBefore", OBJECT_PROPERTY),

    TIME_DAY_OF_WEEK_PROPERTY(Namespaces.TIME, "dayOfWeek", OBJECT_PROPERTY),

    TIME_HAS_DATE_TIME_DESCRIPTION(Namespaces.TIME, "hasDateTimeDescription", OBJECT_PROPERTY),

    TIME_HAS_TEMPORAL_DURATION(Namespaces.TIME, "hasTemporalDuration", OBJECT_PROPERTY),

    TIME_HAS_DURATION(Namespaces.TIME, "hasDuration", OBJECT_PROPERTY),

    TIME_HAS_DURATION_DESCRIPTION(Namespaces.TIME, "hasDurationDescription", OBJECT_PROPERTY),

    TIME_HAS_TIME(Namespaces.TIME, "hasTime", OBJECT_PROPERTY),

    TIME_HAS_BEGINNING(Namespaces.TIME, "hasBeginning", OBJECT_PROPERTY),

    TIME_HAS_END(Namespaces.TIME, "hasEnd", OBJECT_PROPERTY),

    TIME_INSIDE(Namespaces.TIME, "inside", OBJECT_PROPERTY),

    TIME_TIME_ZONE_PROPERTY(Namespaces.TIME, "timeZone", OBJECT_PROPERTY),

    TIME_INTERVAL_CONTAINS(Namespaces.TIME, "intervalContains", OBJECT_PROPERTY),

    TIME_INTERVAL_DISJOINT(Namespaces.TIME, "intervalDisjoint", OBJECT_PROPERTY),

    TIME_INTERVAL_EQUALS(Namespaces.TIME, "intervalEquals", OBJECT_PROPERTY),

    TIME_INTERVAL_FINISHED_BY(Namespaces.TIME, "intervalFinishedBy", OBJECT_PROPERTY),

    TIME_INTERVAL_IN(Namespaces.TIME, "intervalIn", OBJECT_PROPERTY),

    TIME_INTERVAL_DURING(Namespaces.TIME, "intervalDuring", OBJECT_PROPERTY),

    TIME_INTERVAL_FINISHES(Namespaces.TIME, "intervalFinishes", OBJECT_PROPERTY),

    TIME_INTERVAL_STARTS(Namespaces.TIME, "intervalStarts", OBJECT_PROPERTY),

    TIME_INTERVAL_MEETS(Namespaces.TIME, "intervalMeets", OBJECT_PROPERTY),

    TIME_INTERVAL_MET_BY(Namespaces.TIME, "intervalMetBy", OBJECT_PROPERTY),

    TIME_INTERVAL_OVERLAPPED_BY(Namespaces.TIME, "intervalOverlappedBy", OBJECT_PROPERTY),

    TIME_INTERVAL_OVERLAP(Namespaces.TIME, "intervalOverlaps", OBJECT_PROPERTY),

    TIME_INTERVAL_STARTED_BY(Namespaces.TIME, "intervalStartedBy", OBJECT_PROPERTY),

    TIME_MONTH_OF_YEAR_PROPERTY(Namespaces.TIME, "monthOfYear", OBJECT_PROPERTY),

    TIME_IN_TEMPORAL_POSITION(Namespaces.TIME, "inTemporalPosition", OBJECT_PROPERTY),

    TIME_IN_DATE_TIME(Namespaces.TIME, "inDateTime", OBJECT_PROPERTY),

    TIME_IN_TIME_POSITION(Namespaces.TIME, "inTimePosition", OBJECT_PROPERTY),

    TIME_HAS_TRS(Namespaces.TIME, "hasTRS", OBJECT_PROPERTY),

    TIME_UNIT_TYPE(Namespaces.TIME, "unitType", OBJECT_PROPERTY),

    TIME_DAY(Namespaces.TIME, "day", DATA_PROPERTY),

    TIME_DAY_OF_YEAR(Namespaces.TIME, "dayOfYear", DATA_PROPERTY),

    TIME_DAYS(Namespaces.TIME, "days", DATA_PROPERTY),

    TIME_HAS_XSD_DURATION(Namespaces.TIME, "hasXSDDuration", DATA_PROPERTY),

    TIME_HOUR(Namespaces.TIME, "hour", DATA_PROPERTY),

    TIME_HOURS(Namespaces.TIME, "hours", DATA_PROPERTY),

    TIME_IN_XSD_DATE_TIME_STAMP(Namespaces.TIME, "inXSDDateTimeStamp", DATA_PROPERTY),

    TIME_IN_XSD_G_YEAR(Namespaces.TIME, "inXSDgYear", DATA_PROPERTY),

    TIME_IN_XSD_G_YEAR_MONTH(Namespaces.TIME, "inXSDgYearMonth", DATA_PROPERTY),

    TIME_MINUTE(Namespaces.TIME, "minute", DATA_PROPERTY),

    TIME_MINUTES(Namespaces.TIME, "minutes", DATA_PROPERTY),

    TIME_MONTH(Namespaces.TIME, "month", DATA_PROPERTY),

    TIME_MONTHS(Namespaces.TIME, "months", DATA_PROPERTY),

    TIME_NOMINAL_POSITION(Namespaces.TIME, "nominalPosition", DATA_PROPERTY),

    TIME_NUMERIC_DURATION(Namespaces.TIME, "numericDuration", DATA_PROPERTY),

    TIME_NUMERIC_POSITION(Namespaces.TIME, "numericPosition", DATA_PROPERTY),

    TIME_SECOND(Namespaces.TIME, "second", DATA_PROPERTY),

    TIME_SECONDS(Namespaces.TIME, "seconds", DATA_PROPERTY),

    TIME_WEEK(Namespaces.TIME, "week", DATA_PROPERTY),

    TIME_WEEKS(Namespaces.TIME, "weeks", DATA_PROPERTY),

    TIME_YEAR_PROPERTY(Namespaces.TIME, "year", DATA_PROPERTY),

    TIME_YEARS(Namespaces.TIME, "years", DATA_PROPERTY),

    TIME_XSD_DATE_TIME(Namespaces.TIME, "xsdDateTime", DATA_PROPERTY),

    TIME_IN_XSD_DATE_TIME(Namespaces.TIME, "inXSDDateTime", DATA_PROPERTY),

    TIME_UNIT_DAY(Namespaces.TIME, "unitDay", NAMED_INDIVIDUAL),

    TIME_FRIDAY(Namespaces.TIME, "Friday", NAMED_INDIVIDUAL),

    TIME_UNIT_HOUR(Namespaces.TIME, "unitHour", NAMED_INDIVIDUAL),

    TIME_UNIT_MINUTE(Namespaces.TIME, "unitMinute", NAMED_INDIVIDUAL),

    TIME_MONDAY(Namespaces.TIME, "Monday", NAMED_INDIVIDUAL),

    TIME_UNIT_MONTH(Namespaces.TIME, "unitMonth", NAMED_INDIVIDUAL),

    TIME_SATURDAY(Namespaces.TIME, "Saturday", NAMED_INDIVIDUAL),

    TIME_UNIT_SECOND(Namespaces.TIME, "unitSecond", NAMED_INDIVIDUAL),

    TIME_SUNDAY(Namespaces.TIME, "Sunday", NAMED_INDIVIDUAL),

    TIME_THURSDAY(Namespaces.TIME, "Thursday", NAMED_INDIVIDUAL),

    TIME_TUESDAY(Namespaces.TIME, "Tuesday", NAMED_INDIVIDUAL),

    TIME_WEDNESDAY(Namespaces.TIME, "Wednesday", NAMED_INDIVIDUAL),

    TIME_UNIT_WEEK(Namespaces.TIME, "unitWeek", NAMED_INDIVIDUAL),

    TIME_UNIT_YEAR(Namespaces.TIME, "unitYear", NAMED_INDIVIDUAL);

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
