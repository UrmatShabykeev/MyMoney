package com.example.user.mymoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
    }

    public void AddMoney_Touched(View v)
    {
        Intent intent = new Intent(Main_Menu.this, Add_Money.class);
        startActivity(intent);
    }

    public void AddExpense_Touched(View v)
    {
        Intent intent = new Intent(Main_Menu.this, Add_Expense.class);
        startActivity(intent);
    }

    public void Report_Touched(View v)
    {
        Intent intent = new Intent(Main_Menu.this, Report_window.class);
        startActivity(intent);
    }

    public void Report_Touched_Expenses(View v)
    {
        Intent intent = new Intent(Main_Menu.this, report_expenses.class);
        startActivity(intent);
    }

    public void ExitButtonPressed(View v)
    {
        moveTaskToBack(true);
    }
}
