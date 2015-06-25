package com.cc

import java.text.DateFormat
import java.text.SimpleDateFormat

class BusinessTimeCalculator {

    private Date startDate
    private Date endDate
    private Integer minutes = 0

    private Calendar startCal = Calendar.getInstance()
    private Calendar endCal = Calendar.getInstance()

    private String dayStartTimeString = "09:30", dayEndTimeString = "18:30"
    private DateFormat timeParser = new SimpleDateFormat("HH:mm")

    BusinessTimeCalculator(Date startDate, Date endDate) {
        this.startDate = startDate
        this.endDate = endDate

        startCal.setTime(startDate)
        endCal.setTime(endDate)
    }

    private Boolean isInWorkingDay() {
        Calendar calendar1 = Calendar.getInstance()
        calendar1.setTime(timeParser.parse(dayStartTimeString))

        Calendar calendar2 = Calendar.getInstance()
        calendar2.setTime(timeParser.parse(dayEndTimeString))

        [Calendar.YEAR, Calendar.MONTH, Calendar.DATE].each { field ->
            calendar1.set(field, startCal.get(field))
            calendar2.set(field, startCal.get(field))
        }

        Date date1 = calendar1.getTime()
        Date date2 = calendar2.getTime()
        Date date = startCal.getTime()

        return date.after(date1) && date.before(date2)
    }

    Double getDays() {
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            int day = startCal.get(Calendar.DAY_OF_WEEK)
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY || !isInWorkingDay()) {
                startCal.add(Calendar.MINUTE, 1)
                continue
            }

            minutes ++
            startCal.add(Calendar.MINUTE, 1)
        }

        return (minutes / (60 * 8))
    }
}