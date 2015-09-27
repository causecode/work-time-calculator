package com.cc;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessTimeCalculatorTest {

    private static DateFormat parser = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private Date parse(String date) {
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Test
    public void testCalculatorForVariousChecks() {
        BusinessTimeCalculator calculator = new BusinessTimeCalculator(parse("05/01/2015 09:30"), parse("05/01/2015 " +
                "18:29"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
    }

}