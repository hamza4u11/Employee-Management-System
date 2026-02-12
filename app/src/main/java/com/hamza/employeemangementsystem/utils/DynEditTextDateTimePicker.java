package com.hamza.employeemangementsystem.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import com.hamza.employeemangementsystem.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DynEditTextDateTimePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText _editText;
    private int _day;
    private int _month;
    private int _year;
    private int _hour;
    private int _minute;
    private int _second;
    private Context _context;
    private long minimumDate;
    private boolean onlyDate;
    private boolean onlyTime;


    public long getMinimumDate() {
        return minimumDate;
    }

    public void setMinimumDate(long minimumDate) {
        this.minimumDate = minimumDate;
    }

    public boolean isOnlyDate() {
        return onlyDate;
    }

    public void setOnlyTime(boolean onlyTime) {
        this.onlyTime = onlyTime;
    }

    public boolean isOnlyTime() {
        return onlyTime;
    }

    public void setOnlyDate(boolean onlyDate) {
        this.onlyDate = onlyDate;
    }

    public DynEditTextDateTimePicker(Context context, EditText editTextViewID)
    {
        Activity act = (Activity)context;
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
        this._context = context;
    }
    private String  getDateTimeValue(String value){
        SimpleDateFormat sdfOut=new SimpleDateFormat("MMM, dd, yyyy HH:mm");
        if(value.equals("")){
            return value;
        }
        try {
            if(value.length() < 6){
                //Time
                String [] timeParts =value.split(":");
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.set(Calendar.HOUR,Integer.parseInt(timeParts[0]));
                calendar.set(Calendar.MINUTE,Integer.parseInt(timeParts[1]));
                return sdfOut.format(calendar.getTime());
            }
            sdfOut.parse(value);
        }catch(Exception e) {
            //Time only
            e.printStackTrace();
        }
        return value;
    }
    public DynEditTextDateTimePicker(Context context, EditText editTextViewID,String value,boolean isTimeOnly){
        Activity act = (Activity)context;
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
        this._context = context;
        setOnlyTime(isTimeOnly);
        if(!value.equals("")){
            String dateTimeValue = getDateTimeValue(value);
            String dateFormat=isTimeOnly ? "MMM, dd, yyyy HH:mm":"";
            SimpleDateFormat sdfOut=new SimpleDateFormat("MMM, dd, yyyy HH:mm");

            try {
                Date date=sdfOut.parse(dateTimeValue);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                this._year = calendar.get(Calendar.YEAR);
                this._month = calendar.get(Calendar.MONTH);
                this._day = calendar.get(Calendar.DAY_OF_MONTH);
                this._hour = calendar.get(Calendar.HOUR_OF_DAY);
                this._minute = calendar.get(Calendar.MINUTE);
                updateDisplay();
            }catch (ParseException exceptionText){
                exceptionText.printStackTrace();
            }
        }

    }
    public DynEditTextDateTimePicker(Context context, EditText editTextViewID,String value)
    {
        Log.d("Checking Value" , " " + value);
        Activity act = (Activity)context;
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
        this._context = context;
        String dateTimeValue = getDateTimeValue(value);
        if(!value.equals("")){
            SimpleDateFormat sdfOut=new SimpleDateFormat("MMM, dd, yyyy HH:mm");

            Log.d("Checking Value inside If " , " " + value);

            try {
                Date date=sdfOut.parse(dateTimeValue);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                this._year = calendar.get(Calendar.YEAR);
                this._month = calendar.get(Calendar.MONTH);
                this._day = calendar.get(Calendar.DAY_OF_MONTH);
                this._hour = calendar.get(Calendar.HOUR_OF_DAY);
                this._minute = calendar.get(Calendar.MINUTE);
                updateDisplay();
            }catch (ParseException exceptionText){
                exceptionText.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _year = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        if (!isOnlyDate()) {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int hour = _hour != 0 ? _month : calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = _minute != 0 ? _day : calendar.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(_context, this, hour, minutes, true);
            dialog.show();


        }
        updateDisplay();
        }

    @Override
    public void onClick(View v) {
        Log.d("From OnClick ", "Date Time Picker OnClick");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year=_year!=0?_year:calendar.get(Calendar.YEAR);
        int month=_month!=0?_month:calendar.get(Calendar.MONTH);
        int dayOfMonth=_day!=0?_day:calendar.get(Calendar.DAY_OF_MONTH);
        if(isOnlyTime()){
            int hour=(_hour != 0) ? _hour: calendar.get(Calendar.HOUR_OF_DAY);
            int minutes=(_minute != 0) ? _minute: calendar.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(_context, this,hour,minutes,true);
            dialog.show();

        }else {
            DatePickerDialog dialog = new DatePickerDialog(_context, this,
                    year, month,
                    dayOfMonth);
            if (minimumDate > 0) {
                dialog.getDatePicker().setMinDate(minimumDate);
            }
            dialog.show();
        }

    }

    // updates the date in the birth date EditText
    private void updateDisplay() {
        String myFormat = "dd/MM/yy HH:mm"; //In which you need put here
        String myFormatTime="HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdfTimeOnly = new SimpleDateFormat(myFormat, Locale.US);
        try {
            //Date dateTimeOnly=sdfTimeOnly.parse(new StringBuilder().append(_hour).append(":").append(_minute).toString());
            Date date=sdf.parse(new StringBuilder()
                    .append(_day).append("/").append(_month+1).append("/").append(_year).append(" ")
                    .append(_hour).append(":").append(_minute).append("").toString()
            );
            SimpleDateFormat sdfOut=new SimpleDateFormat("MMM, dd, yyyy HH:mm");
            SimpleDateFormat sdfOutTimeOnly=new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfOutDateOnly=new SimpleDateFormat("yyyy-MM-dd");

            //_editText.setText(sdfOut.format(date));
            if(isOnlyTime()){
                _editText.setText(sdfOutTimeOnly.format(date));
//                _editText.setTag(R.id.TAG_DATE_ID,sdfOutTimeOnly.format(date));

            }else{
                _editText.setText(this.onlyDate?sdfOutDateOnly.format(date): sdfOut.format(date));
//                _editText.setTag(R.id.TAG_DATE_ID,date.toString());

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
/*
        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_year).append(" "));

 */
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        _hour=hourOfDay;
        _minute=minute;
        updateDisplay();
    }
}
