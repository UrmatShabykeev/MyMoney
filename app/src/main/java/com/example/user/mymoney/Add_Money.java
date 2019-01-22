package com.example.user.mymoney;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v4.app.DialogFragment;

public class Add_Money extends AppCompatActivity implements View.OnClickListener{

    Button save_button;
    EditText etIncome, etDescription;
    Spinner spCategory;
    DBHelper_Incomes dbHelper_Incomes;
    TextView tvDate;

    String[] categories_of_income = {"Зарплата", "Заем", "Продажа", "Подарок", "Другое"};

    Calendar calendar = new GregorianCalendar();
    int current_year=calendar.get(Calendar.YEAR);
    int current_month=calendar.get(Calendar.MONTH)+1;
    int current_day=calendar.get(Calendar.DAY_OF_MONTH);
    int Dialog_Date=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__money);

        save_button = (Button) findViewById(R.id.button_for_saving_add_income);
        save_button.setOnClickListener(this);
        etIncome = (EditText) findViewById(R.id.editText_for_input_income);
        spCategory = (Spinner) findViewById(R.id.spinner_for_incomeCategory);
        etDescription = (EditText) findViewById(R.id.editText_for_input_description);
        tvDate = (TextView) findViewById(R.id.textView_for_date_of_add_income);
        current_day++;
        tvDate.append(current_day + "/" + current_month + "/" + current_year);
        dbHelper_Incomes = new DBHelper_Incomes(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_of_income);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_for_incomeCategory);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Категория");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

            /*if(current_day <10) {
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
            }*/

            tvDate.setText(current_day + "/" + current_month + "/" + current_year);
        }
    };

    @Override
    public void onClick(View v)
    {
        try {
            double sum_of_income = Double.parseDouble(etIncome.getText().toString());
            String strCategory = spCategory.getSelectedItem().toString();
            String strDescription = etDescription.getText().toString();
            String strDay = Integer.toString(current_day);
            String strMonth = Integer.toString(current_month);
            String strYear = Integer.toString(current_year);

            SQLiteDatabase database = dbHelper_Incomes.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper_Incomes.KEY_INCOME, sum_of_income);
            contentValues.put(DBHelper_Incomes.KEY_CATEGORY, strCategory);
            contentValues.put(DBHelper_Incomes.KEY_DESCRIPTION, strDescription);
            contentValues.put(DBHelper_Incomes.KEY_DAY, strDay);
            contentValues.put(DBHelper_Incomes.KEY_MONTH, strMonth);
            contentValues.put(DBHelper_Incomes.KEY_YEAR, strYear);

            database.insert(DBHelper_Incomes.TABLE_INCOMES, null, contentValues);

            dbHelper_Incomes.close();

            Successful_Saving_Message successful_saving_message = new Successful_Saving_Message();
            successful_saving_message.show(getFragmentManager(), "custom");
        }
        catch(Exception error) {
            Wrong_Saving_Message wrong_saving_message = new Wrong_Saving_Message();
            wrong_saving_message.show(getFragmentManager(), "custom");
        }
    }
}
