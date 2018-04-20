import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Calendar.*;
import java.util.GregorianCalendar;
import java.util.Date;

public class IncomeTaxCalculatorTest {

    private IncomeTaxCalculator taxCalculator;

    @Before public void setUp() {
        taxCalculator = new IncomeTaxCalculator();
    }

    /* full tests */
    @Test public void shouldReturnZeroAsThereIsNoTaxableIncome() {
        assertNotNull(taxCalculator.calculateTax(0, date(-1, -1, -1)));
        assertEquals(taxCalculator.calculateTax(0, date(1930, APRIL, 20)),  0D, 0);
        assertEquals(taxCalculator.calculateTax(0, date(1952, APRIL, 20)),  0D, 0);
        assertEquals(taxCalculator.calculateTax(0, date(1968, MAY, 1)),     0D, 0);
    }
    @Test public void taxCalcultionCheckForSlice1AndHasAowAge() {
        assertEquals(taxCalculator.calculateTax(100, date(1940, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1941, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1942, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1943, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1944, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1945, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1946, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1947, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1948, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1949, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1950, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1951, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1952, JANUARY, 1)), 18.65, 0);
        assertEquals(taxCalculator.calculateTax(1000, date(1952, JANUARY, 1)), 186.5, 0);
    }
    @Test public void taxCalcultionCheckForSlice1AndNotAowAge() {
        assertEquals(taxCalculator.calculateTax(100, date(1954, JANUARY, 1)), 36.55, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1955, JANUARY, 1)), 36.55, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1956, JANUARY, 1)), 36.55, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1955, JANUARY, 1)), 36.55, 0);
        assertEquals(taxCalculator.calculateTax(100, date(1970, JANUARY, 1)), 36.55, 0);
    }
    @Test public void taxCalcultionCheckForSlice2AndHasAowAge() {
        double value1 = 20143;
        double value2 = 34404;
        double perc = 0.2295;
        assertEquals(taxCalculator.calculateTax(value1, date(1930, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1952, JANUARY, 1)), value2 * perc, 0.01);
    }
    @Test public void taxCalcultionCheckForSlice2AndNotAowAge() {
        double value1 = 20143;
        double value2 = 34404;
        double perc = 0.4085;
        assertEquals(taxCalculator.calculateTax(value1, date(1953, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1970, JANUARY, 1)), value2 * perc, 0.01);
    }
    @Test public void taxCalcultionCheckForSlice3() {
        double value1 = 34405;
        double value2 = 68507;
        double perc = 0.4085;
        assertEquals(taxCalculator.calculateTax(value1, date(1930, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1952, JANUARY, 1)), value2 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value1, date(1953, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1970, JANUARY, 1)), value2 * perc, 0.01);
    }
    @Test public void taxCalcultionCheckForSlice4() {
        double value1 = 68508;
        double value2 = 999999;
        double perc = 0.5195;
        assertEquals(taxCalculator.calculateTax(value1, date(1930, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1952, JANUARY, 1)), value2 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value1, date(1953, JANUARY, 1)), value1 * perc, 0.01);
        assertEquals(taxCalculator.calculateTax(value2, date(1970, JANUARY, 1)), value2 * perc, 0.01);
    }

    /* partial tests */
    @Test public void slice1CheckBasedOnTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(0));
        assertSame(taxCalculator.getSlice(0),     1);
        assertSame(taxCalculator.getSlice(1),     1);
        assertSame(taxCalculator.getSlice(20142), 1);
    }
    @Test public void slice2CheckBasedOnTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(20143));
        assertSame(taxCalculator.getSlice(20143), 2);
        assertSame(taxCalculator.getSlice(34404), 2);
    }
    @Test public void slice3CheckBasedOnTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(34405));
        assertSame(taxCalculator.getSlice(34405), 3);
        assertSame(taxCalculator.getSlice(68507), 3);
    }
    @Test public void slice4CheckBasedOnTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(68508));
        assertSame(taxCalculator.getSlice(68508),     4);
        assertSame(taxCalculator.getSlice(999999999), 4);
    }
    @Test public void sliceCheckBasedOnInvalidTaxableIncome() {
        assertNotNull(taxCalculator.getSlice(-1));
        assertNotNull(taxCalculator.getSlice(-1));
        assertSame(taxCalculator.getSlice(-1),   0);
        assertSame(taxCalculator.getSlice(-100), 0);
    }

    /* test with a fake value for 'today', otherwise the test parameters have to be changed every year *//** (e.g. some of the 'assertFalse' will fail next year) */
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
        assertFalse(taxCalculator.aowLeeftijd(date(1970, JANUARY, 1), pretendDateToday));
    }
    @Test public void getMontsUntillAowTest() {
        assertEquals(taxCalculator.getMontsUntillAow(date(1948, JANUARY, 1)), 781);
        assertEquals(taxCalculator.getMontsUntillAow(date(1953, JANUARY, 1)), 796);
        assertEquals(taxCalculator.getMontsUntillAow(date(1953, JANUARY, 1)), 796);
    }

    /* helper functions */
    private static Date date(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }

    @Ignore
    @Test public void helperFunctions(){
        Date d_2_2_2018 = new GregorianCalendar(2018, 1, 2).getTime();
        assertEquals(taxCalculator.date(2018, FEBRUARY, 2), d_2_2_2018);
        assertEquals(taxCalculator.round(123456.123), 123456.12, 0);
        assertEquals(taxCalculator.round(123456.125), 123456.13, 0);
    }
}