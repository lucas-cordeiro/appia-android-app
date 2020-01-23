package br.com.lucascordeiro.appia.ui.util;

import android.util.Log;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayAxisValueFormatter extends ValueFormatter {
    private final String[] mWeeks = new String[]{
            "Dom","Seg", "Ter", "Quar", "Quin", "Sex", "Sab",
    };

    private final BarLineChartBase<?> chart;
    private List<Date> dates;

    public DayAxisValueFormatter(BarLineChartBase<?> chart, List<Date> dates) {
        this.chart = chart;
        this.dates = dates;
    }

    @Override
    public String getFormattedValue(float value) {
        Calendar cal = Calendar.getInstance();
        Date date = dates.get((int)value);
        cal.setTimeInMillis(date.getTime());
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        Log.d("TAG","Date: "+date+" WeekDay: "+weekDay);
        return mWeeks[weekDay-1];
    }
}