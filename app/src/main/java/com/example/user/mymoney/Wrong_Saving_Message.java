package com.example.user.mymoney;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class Wrong_Saving_Message extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Ошибка!!!").setMessage("Введены неправильные данные.\nПовторите ввод.").setIcon(R.drawable.error_img).setPositiveButton("OK", null).create();
    }
}
