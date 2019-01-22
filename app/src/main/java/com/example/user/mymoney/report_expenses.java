package com.example.user.mymoney;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.util.Log;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class report_expenses extends AppCompatActivity implements View.OnClickListener{

    TextView tvReport, tvDate;

    String selected_date="";

    Calendar calendar = new GregorianCalendar();
    int current_year = calendar.get(Calendar.YEAR);
    int current_month = calendar.get(Calendar.MONTH) + 1;
    int current_day = calendar.get(Calendar.DAY_OF_MONTH);
    int Dialog_Date = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_expenses);

        tvReport =(TextView) findViewById(R.id.textView_for_showing_report);
        tvReport.setMovementMethod(new ScrollingMovementMethod());

        tvDate = (TextView) findViewById(R.id.textView_for_choosing_date);
        current_day++;
        tvDate.append(current_day + "/" + current_month + "/" + current_year);

        String[] categories_of_report_date = {"За день", "За месяц", "За год"};
        ArrayAdapter<String> adapter_date = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_of_report_date);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner_for_report_date = (Spinner) findViewById(R.id.spinner_for_reportDate);
        spinner_for_report_date.setAdapter(adapter_date);
        spinner_for_report_date.setPrompt("Категория");

        OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                selected_date = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner_for_report_date.setOnItemSelectedListener(itemSelectedListener);
    }

    public void datePicking(View v)
    {
        showDialog(Dialog_Date);
    }

    protected Dialog onCreateDialog(int id){
        if(id==Dialog_Date){
            DatePickerDialog tdp = new DatePickerDialog(this, myCallBack, current_year, current_month, current_day);
            return tdp;
        }
        return super.onCreateDialog(id);
    }

    OnDateSetListener myCallBack = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            current_year = year;
            current_month=monthOfYear+1;
            current_day = dayOfMonth;

            if(current_day <10) {
                String str_current_day = Integer.toString(current_day);
                StringBuffer strbf_zero = new StringBuffer("0");
                strbf_zero.append(str_current_day);
                String str_zero = strbf_zero.toString();
                current_day = Integer.parseInt(str_zero);
            }

            if(current_month <10) {
                String str_current_month = Integer.toString(current_month);
                StringBuffer strbf_zero = new StringBuffer("0");
                strbf_zero.append(str_current_month);
                String str_zero = strbf_zero.toString();
                current_month = Integer.parseInt(str_zero);
            }

            if(selected_date.equals("За день"))
            {
                tvDate.setText(current_day + "/" + current_month + "/" + current_year);
            }
            else if(selected_date.equals("За месяц"))
            {
                switch(current_month) {
                    case 1:
                        tvDate.setText("Январь " + current_year);
                        break;
                    case 2:
                        tvDate.setText("Февраль " + current_year);
                        break;
                    case 3:
                        tvDate.setText("Март " + current_year);
                        break;
                    case 4:
                        tvDate.setText("Апрель "  + current_year);
                        break;
                    case 5:
                        tvDate.setText("Май "  + current_year);
                        break;
                    case 6:
                        tvDate.setText("Июнь "  + current_year);
                        break;
                    case 7:
                        tvDate.setText("Июль "  + current_year);
                        break;
                    case 8:
                        tvDate.setText("Август "  + current_year);
                        break;
                    case 9:
                        tvDate.setText("Сентябрь "  + current_year);
                        break;
                    case 10:
                        tvDate.setText("Октябрь "  + current_year);
                        break;
                    case 11:
                        tvDate.setText("Ноябрь "  + current_year);
                        break;
                    case 12:
                        tvDate.setText("Декабрь "  + current_year);
                        break;
                }
            }
            else if(selected_date.equals("За год"))
            {
                String str_current_year = Integer.toString(current_year);
                tvDate.setText(str_current_year);
            }

        }
    };

    @Override
    public void onClick(View v)
    {
        String str_current_day = Integer.toString(current_day);
        String str_current_month = Integer.toString(current_month);
        String str_current_year = Integer.toString(current_year);

        DBHelper_Expenses dbHelper_expenses = new DBHelper_Expenses(this);
        SQLiteDatabase database = dbHelper_expenses.getReadableDatabase();
        Cursor cursor = database.query(DBHelper_Expenses.TABLE_EXPENSES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_ID);
            int incomeIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_EXPENSE);
            int categoryIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_CATEGORY);
            int descriptionIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_DESCRIPTION);
            int dayIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_DAY);
            int monthIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_MONTH);
            int yearIndex = cursor.getColumnIndex(DBHelper_Expenses.KEY_YEAR);

            do {
                if(selected_date.equals("За день")) {
                    if (str_current_day.equals(cursor.getString(dayIndex)) && str_current_month.equals(cursor.getString(monthIndex)) && str_current_year.equals(cursor.getString(yearIndex))) {
                        tvReport.setText("" + cursor.getString(categoryIndex) + " " + cursor.getString(incomeIndex) +
                                " " + cursor.getString(dayIndex) + "/" + cursor.getString(monthIndex) + "/" + cursor.getString(yearIndex));
                        tvReport.append("\n");
                    }
                }

                else if(selected_date.equals("За месяц")) {
                    if(str_current_month.equals(cursor.getString(monthIndex)))
                    {
                        tvReport.setText("" + cursor.getString(categoryIndex) + " " + cursor.getString(incomeIndex) +
                                " " + cursor.getString(dayIndex) + "/" + cursor.getString(monthIndex) + "/" + cursor.getString(yearIndex));
                        tvReport.append("\n");
                    }
                }

                else if(selected_date.equals("За год")) {
                    if(str_current_year.equals(cursor.getString(yearIndex)))
                    {
                        tvReport.setText("" + cursor.getString(categoryIndex) + " " + cursor.getString(incomeIndex) +
                                " " + cursor.getString(dayIndex) + "/" + cursor.getString(monthIndex) + "/" + cursor.getString(yearIndex));
                        tvReport.append("\n");
                    }
                }

            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();
    }
}
