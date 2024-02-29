package clubframework;
import java.util.Calendar;

/**
 * Represents a valid calendar date.
 * @author Matteus Coste
 * @author Benjamin Leiby
 */
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;
    private int dateNum;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Default constructor.
     */
    public Date() { }

    /**
     * Overloaded constructor.
     * @param year of date.
     * @param month of year.
     * @param day of month.
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        dateNum = (this.year * 10000) + (this.month * 1000) + this.day;
    }

    /**
     * Overloaded constructor to create a date from a String.
     * @param date String of format "MM/DD/YYYY".
     */
    public Date(String date) {

        String [] split = date.split("/");

        this.month = Integer.parseInt(split[0]);
        this.day = Integer.parseInt(split[1]);
        this.year = Integer.parseInt(split[2]);
        dateNum = (this.year * 10000) + (this.month * 1000) + this.day;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Sets the date to a specified number of months and years from the current date
     * @param months
     * @param years
     */
    public void setToTimeFromToday(int months, int years) {

        Calendar today = Calendar.getInstance();
        this.day = today.get(Calendar.DAY_OF_MONTH);
        this.month = today.get(Calendar.MONTH) + 1 + months;
        this.year = today.get(Calendar.YEAR) + years;
        dateNum = (this.year * 10000) + (this.month * 1000) + this.day;
    }

    /**
     * Determines if the date of birth represents an adult based on the age of 18 years or older
     * @return True if the date of birth represents an adult (18 years or older), false otherwise
     */
    public boolean isAdult() {

        Calendar maxDob = Calendar.getInstance();
        maxDob.add(Calendar.YEAR, -18);

        return this.isBefore(maxDob);

    }

    /**
     * Compares two date integers
     * @param anotherDate the object to be compared.
     * @return -1 if the first value is less than the second,
     * 1 if the first value is greater than the second, and 0 otherwise
     */
    @Override
    public int compareTo(Date anotherDate) {
        return this.dateNum - anotherDate.dateNum;
    }

    /**
     * Determines whether a date is valid.
     * @return true if the date is a valid calendar date,
     * false if otherwise
     */
    public boolean isValid() {

        if (month < 1 || month > 12 || day < 1 || year < 1900) {
            return false;
        }
        int numOfDays = numOfDays(month);
        if (numOfDays == -1 || day > numOfDays) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the current date is before today.
     * @return true if the date is before today. Otherwise, return false.
     */
    public boolean isBeforeToday () {

        Calendar today = Calendar.getInstance();

        if (this.year < today.get(Calendar.YEAR)) {
            return true;
        }
        else if (this.year > today.get(Calendar.YEAR)) {
            return false;
        }
        else {

            if (this.month < today.get(Calendar.MONTH) + 1) {
                return true;
            }
            else if (this.month > today.get(Calendar.MONTH) + 1) {
                return false;
            }
            else {

                if (this.day < today.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }

    }

    /**
     * Checks if the current date represented by the instance is before the specified date
     * @param date
     * @return true if the current date is before the specified date, false if otherwise
     */
    public boolean isBefore(Calendar date) {

        if (this.year < date.get(Calendar.YEAR)) {
            return true;
        }
        else if (this.year > date.get(Calendar.YEAR)) {
            return false;
        }
        else {

            if (this.month < date.get(Calendar.MONTH) + 1) {
                return true;
            }
            else if (this.month > date.get(Calendar.MONTH) + 1) {
                return false;
            }
            else {

                if (this.day < date.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }

    }

    /**
     * Find the number of days in a given month
     * and determine if it's a leap year.
     * @param month of year.
     * @return number of days in given month
     */
    private int numOfDays(int month) {
        switch (month - 1) {
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.FEBRUARY:
                return isLeapYear() ? 29: 28;
            default:
                return -1;
        }
    }

    /**
     * Check if given year is a leap year
     * @return true if the year is a leap year, false otherwise
     */
    public boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Format: [month]/[day]/[year]
     * @return string representation of date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Testbed main.
     * @param args ...
     */
    public static void main(String[] args) {

        Date invalidDate1 = new Date(1899, Calendar.JANUARY, 1);
        Date invalidDate2 = new Date(2018, Calendar.FEBRUARY, 29);  // not a leap year
        Date invalidDate3 = new Date(2003, Calendar.MARCH, 32);
        Date invalidDate4 = new Date(1950, Calendar.APRIL, 31);
        Date invalidDate5 = new Date(1988, Calendar.DECEMBER+1, 8);

        Date validDate1 = new Date(2007, 1, 1);
        Date validDate2 = new Date(1972, Calendar.FEBRUARY, 29);    // leap year

        System.out.println("The date " + invalidDate1.toString() + " is valid?: " + invalidDate1.isValid());
        System.out.println("The date " + invalidDate2.toString() + " is valid?: " + invalidDate2.isValid());
        System.out.println("The date " + invalidDate3.toString() + " is valid?: " + invalidDate3.isValid());
        System.out.println("The date " + invalidDate4.toString() + " is valid?: " + invalidDate4.isValid());
        System.out.println("The date " + invalidDate5.toString() + " is valid?: " + invalidDate5.isValid());

        System.out.println("\nThe date " + validDate1.toString() + " is valid?: " + validDate1.isValid());
        System.out.println("The date " + validDate2.toString() + " is valid?: " + validDate2.isValid());

        Date validDate = new Date(2023, 3, 8);

        System.out.println(validDate.isBeforeToday());

        System.out.println(validDate1.isAdult());
        //validDate1.setTOTim();
         System.out.println(validDate1);

        System.out.println(Calendar.getInstance().toString());

    }

}