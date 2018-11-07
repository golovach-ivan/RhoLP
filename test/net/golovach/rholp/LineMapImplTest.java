package net.golovach.rholp;

import net.golovach.rholp.log.impl.LineMapImpl;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LineMapImplTest {

    @Test
    public void test_offsetToRow() {

        LineMapImpl lineMap = new LineMapImpl("0\n12\r\n345\n\n");

        assertThat(lineMap.offsetToRow(0), is(1));
        assertThat(lineMap.offsetToRow(1), is(1));
        assertThat(lineMap.offsetToRow(2), is(2));
        assertThat(lineMap.offsetToRow(3), is(2));
        assertThat(lineMap.offsetToRow(4), is(2));
        assertThat(lineMap.offsetToRow(5), is(2));
        assertThat(lineMap.offsetToRow(6), is(3));
        assertThat(lineMap.offsetToRow(7), is(3));
        assertThat(lineMap.offsetToRow(8), is(3));
        assertThat(lineMap.offsetToRow(9), is(3));
        assertThat(lineMap.offsetToRow(10), is(4));
    }

    @Test
    public void test_offsetToCol() {

        LineMapImpl lineMap = new LineMapImpl("0\n12\r\n345\n\n");

        assertThat(lineMap.offsetToCol(0), is(1));
        assertThat(lineMap.offsetToCol(1), is(2));
        assertThat(lineMap.offsetToCol(2), is(1));
        assertThat(lineMap.offsetToCol(3), is(2));
        assertThat(lineMap.offsetToCol(4), is(3));
        assertThat(lineMap.offsetToCol(5), is(4));
        assertThat(lineMap.offsetToCol(6), is(1));
        assertThat(lineMap.offsetToCol(7), is(2));
        assertThat(lineMap.offsetToCol(8), is(3));
        assertThat(lineMap.offsetToCol(9), is(4));
        assertThat(lineMap.offsetToCol(10), is(1));
    }

    @Test
    public void test_offsetToSrcLine() {

        LineMapImpl lineMap = new LineMapImpl("0\n12\r\n345\n\n");

        assertThat(lineMap.offsetToSrcLine(0), is("0\n"));
        assertThat(lineMap.offsetToSrcLine(1), is("0\n"));
        assertThat(lineMap.offsetToSrcLine(2), is("12\r\n"));
        assertThat(lineMap.offsetToSrcLine(3), is("12\r\n"));
        assertThat(lineMap.offsetToSrcLine(4), is("12\r\n"));
        assertThat(lineMap.offsetToSrcLine(5), is("12\r\n"));
        assertThat(lineMap.offsetToSrcLine(6), is("345\n"));
        assertThat(lineMap.offsetToSrcLine(7), is("345\n"));
        assertThat(lineMap.offsetToSrcLine(8), is("345\n"));
        assertThat(lineMap.offsetToSrcLine(9), is("345\n"));
        assertThat(lineMap.offsetToSrcLine(10), is("\n"));
    }
}
