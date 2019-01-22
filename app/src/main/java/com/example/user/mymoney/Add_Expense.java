package com.example.user.mymoney;

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

public class Add_Expense extends AppCompatActivity implements View.OnClickListener{

    Button save_button;
    EditText etExpense, etDescription;
    Spinner spCategory;
    DBHelper_Expenses dbHelper_Expenses;
    TextView tvDate;

    String[] categories_of_expense = {"Еда", "Одежда", "Образование", "Бензин", "Здоровье", "Коммунальные счета", "Домашние животные", "Транспорт", "Выплата долгов", "Другое"};

    Calendar calendar = new GregorianCalendar();
    int current_year=calendar.get(Calendar.YEAR);
    int current_month=calendar.get(Calendar.MONTH)+1;
    int current_day=calendar.get(Calendar.DAY_OF_MONTH);
    int Dialog_Date=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expense);

        save_button = (Button) findViewById(R.id.button_for_saving_add_expense);
        save_button.setOnClickListener(this);
        etExpense = (EditText) findViewById(R.id.editText_for_input_expense);
        spCategory = (Spinner) findViewById(R.id.spinner_for_expenseCategory);
        etDescription = (EditText) findViewById(R.id.editText_for_input_description);
        tvDate = (TextView) findViewById(R.id.textView_for_date_of_add_expense);
        tvDate.append(current_day + "/" + current_month + "/" + current_year);
        dbHelper_Expenses = new DBHelper_Expenses(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_of_expense);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_for_expenseCategory);
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
            tvDate.setText(current_day + "/" + current_month + "/" + current_year);
        }
    };

    @Override
    public void onClick(View v)
    {
        try {
            double sum_of_expense = Double.parseDouble(etExpense.getText().toString());
            String strCategory = spCategory.getSelectedItem().toString();
            String strDescription = etDescription.getText().toString();
            String strDay = Integer.toString(current_day);
            String strMonth = Integer.toString(current_month);
            String strYear = Integer.toString(current_year);

            SQLiteDatabase database = dbHelper_Expenses.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper_Expenses.KEY_EXPENSE, sum_of_expense);
            contentValues.put(DBHelper_Expenses.KEY_CATEGORY, strCategory);
            contentValues.put(DBHelper_Expenses.KEY_DESCRIPTION, strDescription);
            contentValues.put(DBHelper_Incomes.KEY_DAY, strDay);
            contentValues.put(DBHelper_Incomes.KEY_MONTH, strMonth);
            contentValues.put(DBHelper_Incomes.KEY_YEAR, strYear);

            database.insert(DBHelper_Expenses.TABLE_EXPENSES, null, contentValues);

            dbHelper_Expenses.close();

            Successful_Saving_Message dialog = new Successful_Saving_Message();
            dialog.show(getFragmentManager(), "custom");
        }
        catch (Exception error)
        {
            Wrong_Saving_Message wrong_saving_message = new Wrong_Saving_Message();
            wrong_saving_message.show(getFragmentManager(), "custom");
        }
        }
}
