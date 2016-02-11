package com.causecode;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BusinessTimeCalculatorTest {

    private static DecimalFormat decimalFormat = new DecimalFormat("0.##");
    private static DateFormat parser = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private WorkTimeCalculator calculator;

    private Date parse(String date) {
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private String comparable(Double value) {
        return decimalFormat.format(value);
    }

    @Test
    public void testCalculatorForOneCompleteWorkingDay() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 09:30"), parse("05/01/2015 18:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForOneCompleteWorkingHours() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 09:30"), parse("05/01/2015 18:30"));
        calculator.setWorkingTime("09:30", "17:30");

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(480, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForOneDay() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 09:30"), parse("05/02/2015 09:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForOneDayIncludingTwoDaysAndSaturdaySunday() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForFourDaysIncludingTwoDaysAndSunday() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));
        calculator.setWeekends(Calendar.SUNDAY, null);

        Assert.assertEquals(2d, calculator.getDays(), 0);
        Assert.assertEquals(1080, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForFourDaysIncludingTwoDaysAndSaturday() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));
        calculator.setWeekends(Calendar.SATURDAY, null);

        Assert.assertEquals(2d, calculator.getDays(), 0);
        Assert.assertEquals(1080, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForFourDaysIncludingTwoDaysAndTwoWeekendDays() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));
        calculator.setWeekends(Calendar.SUNDAY, Calendar.SATURDAY);

        Assert.assertEquals(1d, calculator.getDays(), 0);
        Assert.assertEquals(540, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForFourDaysIncludingTwoDaysAndNonContinuousTwoWeekendDays() {
        calculator = new WorkTimeCalculator(parse("05/01/2015 12:30"), parse("05/04/2015 12:30"));
        calculator.setWeekends(Calendar.SATURDAY, Calendar.MONDAY);

        Assert.assertEquals("1.67", comparable(calculator.getDays()));
        Assert.assertEquals(900, calculator.getMinutes(), 0);
    }

    @Test
    public void testCalculatorForTwoWeekends() {
        calculator = new WorkTimeCalculator(parse("10/14/2015 11:30"), parse("10/31/2015 17:30"));
        calculator.setWeekends(Calendar.SATURDAY, Calendar.SUNDAY);

        Assert.assertEquals(6900, calculator.getMinutes(), 0);
        Assert.assertEquals("12.78", comparable(calculator.getDays()));
    }
}