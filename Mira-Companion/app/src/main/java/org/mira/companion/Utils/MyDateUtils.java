package org.mira.companion.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Keep;
import android.text.format.DateUtils;
import android.text.format.Time;


import org.mira.companion.R;

import java.util.Locale;

/**
 * This class contains various date-related utilities for creating text for things like
 * elapsed time and date ranges, strings for days of the week and months, and AM/PM text     etc.
 */


@Keep
public class MyDateUtils
{

@Keep
public static CharSequence getRelativeTimeSpanString(long time, long now, long minResolution,
        int flags, Context c) {
    Resources r = c.getResources();
    //boolean abbrevRelative = (flags & (FORMAT_ABBREV_RELATIVE | FORMAT_ABBREV_ALL)) != 0;

    boolean past = (now >= time);
    long duration = Math.abs(now - time);

    int resId;
    long count;
    if (duration < DateUtils.MINUTE_IN_MILLIS && minResolution < DateUtils.MINUTE_IN_MILLIS) {
        count = duration / DateUtils.SECOND_IN_MILLIS;
        if (past) {
            resId = R.plurals.num_seconds_ago;
        } else {
            resId = R.plurals.in_num_seconds;
        }
    } else if (duration < DateUtils.HOUR_IN_MILLIS && minResolution < DateUtils.HOUR_IN_MILLIS) {
        count = duration / DateUtils.MINUTE_IN_MILLIS;
        if (past) {
            resId = R.plurals.num_minutes_ago;
        } else {
            resId = R.plurals.in_num_minutes;
        }
    } else if (duration < DateUtils.DAY_IN_MILLIS && minResolution < DateUtils.DAY_IN_MILLIS) {
        count = duration / DateUtils.HOUR_IN_MILLIS;
        if (past) {
            resId = R.plurals.num_hours_ago;
        } else {
            resId = R.plurals.in_num_hours;
        }
    } else if (duration < DateUtils.WEEK_IN_MILLIS && minResolution < DateUtils.WEEK_IN_MILLIS) {
        return getRelativeDayString(r, time, now);
    } else {
        // We know that we won't be showing the time, so it is safe to pass
        // in a null context.
        return DateUtils.formatDateRange(null, time, time, flags);
    }

    String format = r.getQuantityString(resId, (int) count);
    return String.format(format, count);
}



/**
 * Returns a string describing a day relative to the current day. For example if the day is
 * today this function returns "Today", if the day was a week ago it returns "7 days ago", and
 * if the day is in 2 weeks it returns "in 14 days".
 *
 * @param r the resources
 * @param day the relative day to describe in UTC milliseconds
 * @param today the current time in UTC milliseconds
 */

@Keep
private static final String getRelativeDayString(Resources r, long day, long today) {
    Locale locale = r.getConfiguration().locale;
    if (locale == null) {
        locale = Locale.getDefault();
    }

    // TODO: use TimeZone.getOffset instead.
    Time startTime = new Time();
    startTime.set(day);
    int startDay = Time.getJulianDay(day, startTime.gmtoff);

    Time currentTime = new Time();
    currentTime.set(today);
    int currentDay = Time.getJulianDay(today, currentTime.gmtoff);

    int days = Math.abs(currentDay - startDay);
    boolean past = (today > day);

    // TODO: some locales name other days too, such as de_DE's "Vorgestern" (today - 2).
    if (days == 1) {
        if (past) {
            return r.getString(R.string.yesterday);
        } else {
            return r.getString(R.string.tomorrow);
        }
    } else if (days == 0) {
        return r.getString(R.string.today);
    }

    int resId;
    if (past) {
        resId = R.plurals.num_days_ago;
    } else {
        resId = R.plurals.in_num_days;
    }

    String format = r.getQuantityString(resId, days);
    return String.format(format, days);
}
}