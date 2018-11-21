package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.Construct.*;
import static org.semanticweb.owlapi.util.Languages.*;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.semanticweb.owlapi.util.Construct;
import org.semanticweb.owlapi.util.Languages;

@SuppressWarnings("javadoc")
public class LanguagesTestCase {
    private static EnumSet<Construct> set(Construct... constructs) {
        return EnumSet.copyOf(Arrays.asList(constructs));
    }

    private static void same(Languages l, Construct... constructs) {
        assertEquals(set(constructs), l.components());
    }

    @Test
    public void shouldFindExpectedConstructs() {
        same(FL0, CINT, UNIVRESTR);
        same(FLMINUS, CINT, UNIVRESTR, LIMEXIST);
        same(FL, RRESTR, CINT, UNIVRESTR, LIMEXIST);
        same(AL, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(EL, E, CINT);
        same(ALE, E, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALC, C, U, E, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCD, C, U, E, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCF, C, U, E, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCFD, C, U, E, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCN, C, U, E, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCND, C, U, E, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCQ, C, U, E, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCQD, C, U, E, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCI, C, U, E, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCID, C, U, E, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIF, C, U, E, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIFD, C, U, E, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIN, C, U, E, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIND, C, U, E, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIQ, C, U, E, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCIQD, C, U, E, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCO, C, U, E, O, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOD, C, U, E, O, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOF, C, U, E, O, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOFD, C, U, E, O, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCON, C, U, E, O, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOND, C, U, E, O, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOQ, C, U, E, O, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOQD, C, U, E, O, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOI, C, U, E, O, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOID, C, U, E, O, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIF, C, U, E, O, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIFD, C, U, E, O, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIN, C, U, E, O, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIND, C, U, E, O, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIQ, C, U, E, O, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCOIQD, C, U, E, O, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCH, C, U, E, H, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHD, C, U, E, H, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHF, C, U, E, H, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHFD, C, U, E, H, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHN, C, U, E, H, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHND, C, U, E, H, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHQ, C, U, E, H, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHQD, C, U, E, H, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHI, C, U, E, H, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHID, C, U, E, H, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIF, C, U, E, H, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIFD, C, U, E, H, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIN, C, U, E, H, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIND, C, U, E, H, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIQ, C, U, E, H, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHIQD, C, U, E, H, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHO, C, U, E, H, O, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOD, C, U, E, H, O, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOF, C, U, E, H, O, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOFD, C, U, E, H, O, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHON, C, U, E, H, O, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOND, C, U, E, H, O, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOQ, C, U, E, H, O, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOQD, C, U, E, H, O, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOI, C, U, E, H, O, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOID, C, U, E, H, O, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIF, C, U, E, H, O, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIFD, C, U, E, H, O, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIN, C, U, E, H, O, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIND, C, U, E, H, O, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIQ, C, U, E, H, O, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCHOIQD, C, U, E, H, O, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCR, C, U, E, R, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRD, C, U, E, R, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRF, C, U, E, R, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRFD, C, U, E, R, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRN, C, U, E, R, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRND, C, U, E, R, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRQ, C, U, E, R, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRQD, C, U, E, R, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRI, C, U, E, R, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRID, C, U, E, R, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIF, C, U, E, R, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIFD, C, U, E, R, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIN, C, U, E, R, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIND, C, U, E, R, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIQ, C, U, E, R, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRIQD, C, U, E, R, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRO, C, U, E, R, O, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROD, C, U, E, R, O, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROF, C, U, E, R, O, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROFD, C, U, E, R, O, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCRON, C, U, E, R, O, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROND, C, U, E, R, O, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROQ, C, U, E, R, O, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROQD, C, U, E, R, O, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROI, C, U, E, R, O, I, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROID, C, U, E, R, O, I, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIF, C, U, E, R, O, I, F, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIFD, C, U, E, R, O, I, F, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIN, C, U, E, R, O, I, N, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIND, C, U, E, R, O, I, N, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIQ, C, U, E, R, O, I, Q, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ALCROIQD, C, U, E, R, O, I, Q, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(S, C, U, E, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SD, C, U, E, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SF, C, U, E, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SFD, C, U, E, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SN, C, U, E, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SND, C, U, E, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SQ, C, U, E, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SQD, C, U, E, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SI, C, U, E, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SID, C, U, E, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIF, C, U, E, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIFD, C, U, E, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIN, C, U, E, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIND, C, U, E, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIQ, C, U, E, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SIQD, C, U, E, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SO, C, U, E, O, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOD, C, U, E, O, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOF, C, U, E, O, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOFD, C, U, E, O, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SON, C, U, E, O, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOND, C, U, E, O, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOQ, C, U, E, O, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOQD, C, U, E, O, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOI, C, U, E, O, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOID, C, U, E, O, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIF, C, U, E, O, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIFD, C, U, E, O, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIN, C, U, E, O, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIND, C, U, E, O, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIQ, C, U, E, O, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SOIQD, C, U, E, O, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SH, C, U, E, H, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHD, C, U, E, H, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHF, C, U, E, H, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHFD, C, U, E, H, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHN, C, U, E, H, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHND, C, U, E, H, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHQ, C, U, E, H, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHQD, C, U, E, H, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHI, C, U, E, H, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHID, C, U, E, H, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIF, C, U, E, H, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIFD, C, U, E, H, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIN, C, U, E, H, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIND, C, U, E, H, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIQ, C, U, E, H, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHIQD, C, U, E, H, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHO, C, U, E, H, O, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOD, C, U, E, H, O, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOF, C, U, E, H, O, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOFD, C, U, E, H, O, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHON, C, U, E, H, O, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOND, C, U, E, H, O, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOQ, C, U, E, H, O, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOQD, C, U, E, H, O, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOI, C, U, E, H, O, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOID, C, U, E, H, O, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIF, C, U, E, H, O, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIFD, C, U, E, H, O, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIN, C, U, E, H, O, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIND, C, U, E, H, O, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIQ, C, U, E, H, O, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SHOIQD, C, U, E, H, O, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SR, C, U, E, R, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRD, C, U, E, R, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRF, C, U, E, R, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRFD, C, U, E, R, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRN, C, U, E, R, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRND, C, U, E, R, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRQ, C, U, E, R, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRQD, C, U, E, R, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRI, C, U, E, R, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRID, C, U, E, R, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIF, C, U, E, R, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIFD, C, U, E, R, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIN, C, U, E, R, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIND, C, U, E, R, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIQ, C, U, E, R, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRIQD, C, U, E, R, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRO, C, U, E, R, O, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROD, C, U, E, R, O, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROF, C, U, E, R, O, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROFD, C, U, E, R, O, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SRON, C, U, E, R, O, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROND, C, U, E, R, O, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROQ, C, U, E, R, O, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROQD, C, U, E, R, O, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROI, C, U, E, R, O, I, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROID, C, U, E, R, O, I, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIF, C, U, E, R, O, I, F, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIFD, C, U, E, R, O, I, F, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIN, C, U, E, R, O, I, N, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIND, C, U, E, R, O, I, N, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIQ, RRESTR, C, U, E, R, O, I, Q, TRAN, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(SROIQD, RRESTR, C, U, E, R, O, I, Q, TRAN, D, ATOMNEG, CINT, UNIVRESTR, LIMEXIST);
        same(ELPLUSPLUS, E, R, O, CINT);
    }

    // utility to write the enum declarations for variations of construct combinations
    public static void dump() {
        // ALC || S (ALC + TRAN)
        // H \subset R
        // O
        // I
        // F \subset N \subset Q
        // D
        for (String s1 : new String[] {"ALC", "S"}) {
            for (String s2 : new String[] {"", "H", "R"}) {
                for (String s3 : new String[] {"", "O"}) {
                    for (String s4 : new String[] {"", "I"}) {
                        for (String s5 : new String[] {"", "F", "N", "Q"}) {
                            for (String s6 : new String[] {"", "D"}) {
                                List<String> list = Arrays.asList(s1, s2, s3, s4, s5, s6);
                                String name = list.stream().collect(Collectors.joining());
                                System.out.println("    /** " + name + " language. */\n    " + name
                                    + "(\"" + name + "\"," + list.stream().filter(s -> !s.isEmpty())
                                        .collect(Collectors.joining(", "))
                                    + "),");
                            }
                        }
                    }
                }
            }
        }
    }
}
