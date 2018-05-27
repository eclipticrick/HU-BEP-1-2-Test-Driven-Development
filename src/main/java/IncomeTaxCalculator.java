import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;

class IncomeTaxCalculator {

    double calculateTax(double taxableIncome, Date dateOfBirth){
        int slice = getSlice(taxableIncome);
        double tax = 0;
        Date now = new Date();
        if(aowLeeftijd(dateOfBirth, now)) {
            if(slice == 1)
                tax = taxableIncome * 0.1865;
            else if(slice == 2)
                tax = taxableIncome * 0.2295;
        }
        else {
            if(slice == 1)
                tax = taxableIncome * 0.3655;
            else if(slice == 2)
                tax = taxableIncome * 0.4085;
        }
        if(slice == 3)
            tax = taxableIncome * 0.4085;
        else if(slice == 4)
            tax = taxableIncome * 0.5195;
        return round(tax);
    }

    Integer getSlice(double taxableIncome) {
        if(taxableIncome > -1 && taxableIncome <= 20142)
            return 1;
        else if(taxableIncome > 20142 && taxableIncome <= 34404)
            return 2;
        else if(taxableIncome > 34404 && taxableIncome <= 68507)
            return 3;
        else if(taxableIncome > 68507)
            return 4;
        else return 0;
    }

    boolean aowLeeftijd(Date dateOfBirth, Date now) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfBirth);

        // get exact date (as calculated by the amount of months)
        cal.add(Calendar.MONTH, getMontsUntillAow(dateOfBirth));

        // set date to the beginning of the calculated year ( "Jaar waarin u AOW krijgt" )
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);

        Date dateWhenAowStarts = cal.getTime();
        return now.compareTo(dateWhenAowStarts) >= 0;
    }

    int getMontsUntillAow(Date dateOfBirth){
        if(dateOfBirth.compareTo(date(1948, Calendar.JANUARY, 1)) < 0)
            return (65 * 12);
        else if(dateOfBirth.compareTo(date(1948, Calendar.DECEMBER, 1)) < 0)
            return (65 * 12) + 1;
        else if(dateOfBirth.compareTo(date(1949, Calendar.NOVEMBER, 1)) < 0)
            return (65 * 12) + 2;
        else if(dateOfBirth.compareTo(date(1950, Calendar.OCTOBER, 1)) < 0)
            return (65 * 12) + 3;
        else if(dateOfBirth.compareTo(date(1951, Calendar.JULY, 1)) < 0)
            return (65 * 12) + 6;
        else if(dateOfBirth.compareTo(date(1952, Calendar.APRIL, 1)) < 0)
            return (65 * 12) + 9;
        else if(dateOfBirth.compareTo(date(1953, Calendar.JANUARY, 1)) < 0)
            return (66 * 12);
        else if(dateOfBirth.compareTo(date(1953, Calendar.SEPTEMBER, 1)) < 0)
            return (66 * 12) + 4;
        else if(dateOfBirth.compareTo(date(1954, Calendar.MAY, 1)) < 0)
            return (66 * 12) + 8;
        else if(dateOfBirth.compareTo(date(1955, Calendar.JANUARY, 1)) < 0)
            return (67 * 12);
        else if(dateOfBirth.compareTo(date(1955, Calendar.OCTOBER, 1)) < 0 ||
                dateOfBirth.compareTo(date(1955, Calendar.OCTOBER, 1)) >= 0)
            return (67 * 12) + 3;
        else
            return 0;
    }

    /* helper functions */
    Date date(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }
    double round(double value) {
        long factor = (long) Math.pow(10, 2);
        return (double) Math.round(value * factor) / factor;
    }
}
