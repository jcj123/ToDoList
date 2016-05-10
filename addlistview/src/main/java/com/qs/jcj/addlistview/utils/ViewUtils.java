package com.qs.jcj.addlistview.utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.Toast;

import com.qs.jcj.addlistview.DayActivity;
import com.qs.jcj.addlistview.MainActivity;
import com.qs.jcj.addlistview.MonthActivity;

import java.util.Calendar;

/**
 * Created by jcj on 16/5/9.
 */
public class ViewUtils {
    private MainActivity activity;
    public ViewUtils(MainActivity activity) {
        this.activity = activity;
    }

    /**
     * 展示日期选择dialog
     */
    public  void showDatePickerDialog(boolean isDay) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (isDay) {
            new DatePickerDialog(activity, new DayPickerListener(), year, month, day).show();
        }else {
            new DatePickerDialog(activity, new MonthPickerListener(), year, month, day).show();
        }
    }
    class DayPickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            Toast.makeText(activity, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(activity, DayActivity.class);
            String date = year + "-0"+monthOfYear+"-0"+dayOfMonth;
            intent.putExtra("date",date);
            activity.startActivity(intent);
        }
    }
    class MonthPickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            Toast.makeText(activity, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(activity, MonthActivity.class);
            String date = year + "-0"+monthOfYear+"%";
            intent.putExtra("date",date);
            activity.startActivity(intent);
        }
    }
}
