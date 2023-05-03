package com.mcs044.expensetracker.entity;

import java.time.Month;

public enum MonthEnum {
    January(Month.JANUARY),
    February(Month.FEBRUARY),
    March(Month.MARCH),
    April(Month.APRIL),
    May(Month.MAY),
    June(Month.JUNE),
    July(Month.JULY),
    August(Month.AUGUST),
    September(Month.SEPTEMBER),
    October(Month.OCTOBER),
    November(Month.NOVEMBER),
    December(Month.DECEMBER);

    private final Month month;

    MonthEnum(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }
}
