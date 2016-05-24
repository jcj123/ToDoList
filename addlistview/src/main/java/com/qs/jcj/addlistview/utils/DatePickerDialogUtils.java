package com.qs.jcj.addlistview.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.Toast;

import com.qs.jcj.addlistview.MainActivity;

import java.util.Calendar;

/**
 * Created by jcj on 16/5/9.
 * 日期选取工具
 */
public class DatePickerDialogUtils {
    private Activity activity;
    private final SharedPreferences sp;

    public DatePickerDialogUtils(Activity activity) {
        this.activity = activity;
        sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    /**
     * 展示日期选择dialog（按天查询，按月查询）
     */
    public void showDatePickerDialog(boolean isDay) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (isDay) {
            new DatePickerDialog(activity, new DayPickerListener(), year, month, day).show();
        } else {
            new DatePickerDialog(activity, new MonthPickerListener(), year, month, day).show();
        }
    }

    /**
     * 添加新的待办事项时，设置待办事项的时间
     */
    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(activity, new AlertListener(), year, month, day).show();
    }

    class AlertListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            Toast.makeText(activity, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            String date = getFormatDate(year,monthOfYear,dayOfMonth);
            sp.edit().putString("showdate",date).commit();
        }
    }

    class DayPickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            Toast.makeText(activity, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            String date = getFormatDate(year, monthOfYear, dayOfMonth);
            sp.edit().putString("date",date).commit();
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.fragmentManage();
        }
    }

    /**
     * 对日期进行格式化处理，以适应数据库
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    @NonNull
    private String getFormatDate(int year, int monthOfYear, int dayOfMonth) {
        String date = null;
        if (monthOfYear<10) {
            date = year + "-0" + monthOfYear;
        }else {
            date = year + "-" + monthOfYear;
        }
        if (dayOfMonth < 10) {
            date = date + "-0" + dayOfMonth;
        }else {
            date = date + "-" + dayOfMonth;
        }
        return date;
    }

    class MonthPickerListener implements DatePickerDialog.OnDateSetListener {

        private String date;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            Toast.makeText(activity, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            if (monthOfYear<10) {
                date = year + "-0" + monthOfYear + "%";
            }else {
                date = year +"-"+ monthOfYear+"%";
            }
            sp.edit().putString("month",date).commit();
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.fragmentManage();
        }
    }
}
