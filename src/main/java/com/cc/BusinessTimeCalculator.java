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

    private Date dayStartTime, dayEndTime;

    private DateFormat timeParser = new SimpleDateFormat("HH:mm");

    private Integer weekendDay1 = Calendar.SUNDAY;
    private Integer weekendDay2 = Calendar.SATURDAY;
    private int workingMinutes;

    BusinessTimeCalculator(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        startCal.setTime(startDate);
        endCal.setTime(endDate);

        setWorkingTime("09:30", "18:30");
    }

    private void validateWeekend(Integer weekendDay) {
        // If weekend day value is not Mon, Tue, Wed, Thurs, Fri, Sat or Sunday
        if (weekendDay != null && (weekendDay < Calendar.SUNDAY || weekendDay > Calendar.SATURDAY)) {
            throw new IllegalArgumentException("Invalid weekend day selected");
        }
    }

    private void log(Object data) {
        System.out.println(data);
    }

    private Boolean isInWorkingDay() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(dayStartTime);
        calendar2.setTime(dayEndTime);

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
        //log("" + date1 + " > " + date + " < " + date2 + " " + status);
        return status;
    }

    /**
     * @return Get number of working time minutes between two given dates.
     */
    public Integer getMinutes() {
        // TODO Optimize this loop
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            int day = startCal.get(Calendar.DAY_OF_WEEK);
            if ((weekendDay1 != null && day == weekendDay1) || (weekendDay2 != null && day == weekendDay2)) {
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

    /**
     * @return Get number of working days between two given dates.
     */
    public Double getDays() {
        getMinutes();
        return ((double) minutes / workingMinutes);
    }

    /**
     * Set start and end working time.
     * @param startTime Work start time in HH:mm format. (Default 09:30)
     * @param endTime Work end time in HH:mm format. (Default 16:30)
     */
    public void setWorkingTime(String startTime, String endTime) {
        try {
            this.dayStartTime = timeParser.parse(startTime);
            this.dayEndTime = timeParser.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

        this.workingMinutes = (int)((this.dayEndTime.getTime() - this.dayStartTime.getTime()) / (1000 * 60));
    }

    /**
     * Set weekend days to exclude
     * @param weekendDay1
     * @param weekendDay2
     */
    public void setWeekends(Integer weekendDay1, Integer weekendDay2) {
        validateWeekend(weekendDay1);
        validateWeekend(weekendDay2);

        this.weekendDay1 = weekendDay1;
        this.weekendDay2 = weekendDay2;
    }
}