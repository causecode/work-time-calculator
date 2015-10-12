package com.cc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * A utility class to calculate business minutes/hours/days between two given dates.
 * @since 0.0.1
 * @auhor Shashank Agrawal
 */
class BusinessTimeCalculator {

    private Date startDate;
    private Date endDate;
    private Integer minutes = 0;

    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();

    private String dayStartTimeString = "09:30", dayEndTimeString = "18:30";
    private DateFormat timeParser = new SimpleDateFormat("HH:mm");

    private int weekendDay1 = Calendar.SUNDAY;
    private int weekendDay2 = Calendar.SATURDAY;

    BusinessTimeCalculator(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        startCal.setTime(startDate);
        endCal.setTime(endDate);
    }

    private void validateWeekend(int weekendDay) {
        if (weekendDay < 0 && weekendDay > Calendar.SATURDAY) {
            throw new IllegalArgumentException("Invalid weekend day selected");
        }
    }

    private void log(Object data) {
        System.out.println(data);
    }

    private Boolean isInWorkingDay() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        try {
            calendar1.setTime(timeParser.parse(dayStartTimeString));
            calendar2.setTime(timeParser.parse(dayEndTimeString));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

        List<Integer> fields = Arrays.asList(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

        for (int field : fields) {
            calendar1.set(field, startCal.get(field));
            calendar2.set(field, startCal.get(field));
        }

        // Get the work start time of the date
        Date date1 = calendar1.getTime();
        // Get the work end time of the date
        Date date2 = calendar2.getTime();
        Date date = startCal.getTime();

        if (date1.before(startDate)) {
            date1 = startDate;
        }

        // If the work end time is greater than the endDate
        if (date2.after(endDate)) {
            // Then set it to the endDate to stop calculating further and also fix invalid calculation
            date2 = endDate;
        }

        // Consider the start time as inclusive and end date as exclusive
        Boolean status = (date.equals(date1) || date.after(date1)) && date.before(date2);
        log("" + date1 + " > " + date + " < " + date2 + " " + status);
        return status;
    }

    Integer getMinutes() {
        // TODO Optimize this loop
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            int day = startCal.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                startCal.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }

            if (!isInWorkingDay()) {
                startCal.add(Calendar.MINUTE, 1);
                continue;
            }

            minutes++;
            startCal.add(Calendar.MINUTE, 1);
        }

        return minutes;
    }

    Double getDays() {
        getMinutes();
        return (double) (minutes / (60 * 8));
    }

    void setWeekends(int weekendDay1, int weekendDay2) {
        validateWeekend(weekendDay1);
        validateWeekend(weekendDay2);

        this.weekendDay1 = weekendDay1;
        this.weekendDay2 = weekendDay2;
    }
}