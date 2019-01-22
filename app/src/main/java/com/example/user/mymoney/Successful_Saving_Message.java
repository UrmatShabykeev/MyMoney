package com.example.user.mymoney;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;


public class Successful_Saving_Message extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Сохранено!").setIcon(R.drawable.thumb_up).setPositiveButton("OK", null).create();
    }
}
