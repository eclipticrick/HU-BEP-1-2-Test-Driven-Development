import org.junit.Ignore;
import org.junit.Test;

import static java.util.Calendar.*;
import java.util.GregorianCalendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class IncomeTaxCalculatorTest {

    private IncomeTaxCalculator taxCalculator;

    @org.junit.Before
    public void setUp() {
        taxCalculator = new IncomeTaxCalculator();
    }
    /* full tests */
    @Test public void shouldReturnZeroAsThereIsNoTaxableIncome() {
        assertNotNull(taxCalculator.calculateTax(0, date(-1, -1, -1)));
        assertThat(taxCalculator.calculateTax(0, date(1930, APRIL, 20)), is(0D));
        assertThat(taxCalculator.calculateTax(0, date(1952, APRIL, 20)), is(0D));
        assertThat(taxCalculator.calculateTax(0, date(1968, MAY, 1)), is(0D));
    }
    @Ignore
    @Test public void taxCalcultionCheck() {
        // https://www.berekenen.nl/inkomen/inkomstenbelasting-berekenen/resultaat
        // –assertEquals( ... )
        // –assertSame( ... )gelijkaan==
        // –assertNotSame( ... )  gelijkaan!=
        // –assertTrue( ... )
        // –assertFalse( ... )
        // –assertArrayEquals( ... )
        // –assertNull( ... )–assertNotNull( ... )
        // –fail( ... )
        assertThat(taxCalculator.calculateTax(123, date(1948, JANUARY, 1)), is(0D));
    }

    /* partial tests */
    @Test public void slice0CheckBasedOnTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(0));
    }
    @Test public void slice1CheckBasedOnTaxableIncome() {
        assertSame(taxCalculator.getSlice(0),     1);
        assertSame(taxCalculator.getSlice(1),     1);
        assertSame(taxCalculator.getSlice(20142), 1);
    }
    @Test public void slice2CheckBasedOnTaxableIncome() {
        assertSame(taxCalculator.getSlice(20143), 2);
        assertSame(taxCalculator.getSlice(34404), 2);
    }
    @Test public void slice3CheckBasedOnTaxableIncome() {
        assertSame(taxCalculator.getSlice(34405), 3);
        assertSame(taxCalculator.getSlice(68507), 3);
    }
    @Test public void slice4CheckBasedOnTaxableIncome() {
        assertSame(taxCalculator.getSlice(68508),     4);
        assertSame(taxCalculator.getSlice(999999999), 4);
    }
    @Test public void sliceCheckBasedOnInvalidTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(-1));
        assertSame(taxCalculator.getSlice(-1),   0);
        assertSame(taxCalculator.getSlice(-100), 0);
    }

    /* test with a fake value for 'today', else the test parameters has to be changed every year *//** (else some of the 'assertFalse' will fail next year) */
    private Date pretendDateToday = date(2018, 4, 18);
    @Test public void aowLeeftijdShouldNotBeNullIn2018() {
        assertNotNull(taxCalculator.aowLeeftijd(date(-1, -1, -1), pretendDateToday));
        assertNotNull(taxCalculator.aowLeeftijd(date(1948, JANUARY, 1), pretendDateToday));
        assertNotNull(taxCalculator.aowLeeftijd(date(1953, JANUARY, 1), pretendDateToday));
    }
    @Test public void aowLeeftijdShouldBeTrueIn2018() {
        assertTrue(taxCalculator.aowLeeftijd(date(1948, JANUARY, 1),   pretendDateToday));
        assertTrue(taxCalculator.aowLeeftijd(date(1951, JUNE, 31),     pretendDateToday));
        assertTrue(taxCalculator.aowLeeftijd(date(1951, JULY, 1),      pretendDateToday));
        assertTrue(taxCalculator.aowLeeftijd(date(1952, DECEMBER, 31), pretendDateToday));
    }
    @Test public void aowLeeftijdShouldBeFalseIn2018() {
        assertFalse(taxCalculator.aowLeeftijd(date(1953, JANUARY, 1), pretendDateToday));
    }

    @Test public void getMontsUntillAowTest() {
        assertThat(taxCalculator.getMontsUntillAow(date(1948, JANUARY, 1)), is(781));
        assertThat(taxCalculator.getMontsUntillAow(date(1953, JANUARY, 1)), is(796));
        assertThat(taxCalculator.getMontsUntillAow(date(1953, JANUARY, 1)), is(796));
    }



    /* helper functions */
    private Date date(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }
}