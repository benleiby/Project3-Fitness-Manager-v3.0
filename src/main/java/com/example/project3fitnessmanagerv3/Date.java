package com.example.project3fitnessmanagerv3;
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
     * Copy constructor.
     * @param other Date to copy to this object.
     */
    public Date(Date other) {

        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
        this.dateNum = other.dateNum;

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

    /**
     * Sets this Date to m months and y years into the future.
     * @param months int the future.
     * @param years into future.
     */
    public void setToTimeFromToday(int months, int years) {

        Calendar today = Calendar.getInstance();
        this.day = today.get(Calendar.DAY_OF_MONTH);
        this.month = today.get(Calendar.MONTH) + 1 + months;
        this.year = today.get(Calendar.YEAR) + years;
        dateNum = (this.year * 10000) + (this.month * 1000) + this.day;

    }

    /**
     * Checks if this Date is the DOB of someone 18 years of age or older.
     * @return True if this is an adult's DOB. If not, return false.
     */
    public boolean isAdultDob() {

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
     * Checks if this Date is a valid calendar date.
     * @return True if valid. Return false if invalid.
     */
    public boolean isValid() {

        if (month < 1 || month > 12 || day < 1 || year < 1900) {
            return false;
        }
        int numOfDays = numOfDays(month);
        return (numOfDays != -1 && day <= numOfDays);

    }

    /**
     * Checks if this Date is before today.
     * @return True if this Date < today. Return false if this Date >= today.
     */
    public boolean isBeforeToday() {

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
                return this.day < today.get(Calendar.DAY_OF_MONTH);
            }

        }

    }

    /**
     * Checks if this Date is before a specified Calendar Date.
     * @param date For comparison.
     * @return True if this Date < date. Return false if this Date >= date.
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
                return this.day < date.get(Calendar.DAY_OF_MONTH);
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

        return switch (month - 1) {
            case Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30;
            case Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER ->
                    31;
            case Calendar.FEBRUARY -> isLeapYear() ? 29 : 28;
            default -> -1;
        };

    }

    /**
     * Check if this Date's year is a leap year.
     * @return True if the year is a leap year. If not, return false.
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
     * Gets String of format "MM/DD/YYYY".
     * @return String representation of this Date.
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
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

}