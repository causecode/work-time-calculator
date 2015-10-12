package com.cc;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessTimeCalculatorTest {

    private static DateFormat parser = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private BusinessTimeCalculator calculator;

    private Date parse(String date) {
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Test
    public void testCalculatorForOneCompleteWorkingDay() {
        calculator = new BusinessTimeCalculator(parse("05/01/2015 09:30"), parse("05/01/2015 18:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForOneDay() {
        calculator = new BusinessTimeCalculator(parse("05/01/2015 09:30"), parse("05/02/2015 09:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForOneDayIncludingTwoDaysAndSaturdaySunday() {
        calculator = new BusinessTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

}